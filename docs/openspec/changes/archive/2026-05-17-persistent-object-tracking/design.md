## Context

The current object detection Android app allows users to select and track objects, but tracking stops when the object leaves the camera view. The user must manually re-select the object to resume tracking. This creates friction in use cases like monitoring a person walking behind obstacles or following a vehicle through a scene.

The existing architecture includes:
- Object detection model providing bounding boxes and class labels per frame
- `ObjectSelection` capability for tapping to select an object (turns bounding box green)
- `TrackingControl` with a stop button to end tracking
- `TrackingDisplay` showing position/velocity overlay

This change extends the tracking system to persist through temporary occlusions and automatically recover the target.

## Goals / Non-Goals

**Goals:**
- Maintain tracking state after selected object disappears from view
- Automatically re-acquire the object when it reappears using class and appearance similarity
- Display only the tracked object's bounding box during active tracking (filter other detections)
- Keep tracking active until user manually stops it
- Preserve existing selection UI (green bounding box) throughout

**Non-Goals:**
- Modifying the object detection model itself
- Implementing full-scale object re-identification (use lightweight features only)
- Multi-object simultaneous tracking (single tracked object at a time)
- Changing the stop button behavior (still manual)

## Decisions

### 1. Tracking State Management

**Decision:** Introduce a `TrackingSession` class to encapsulate persistent tracking state.

**Rationale:** Current tracking state is ephemeral per-frame. A dedicated class cleanly separates concerns and makes state persistence explicit. The `TrackingSession` will store:
- `targetFeatures`: Extracted appearance features (color histogram, aspect ratio) at selection time
- `targetClass`: Object class label
- `targetInitialBounds`: Initial bounding box for reference
- `isActive`: boolean flag
- `isLost`: boolean flag when object temporarily missing
- `framesSinceLastSeen`: counter for lost tracking duration
- `lastKnownPosition`: for velocity smoothing during recovery

**Alternatives Considered:**
- Storing state in existing `TrackingData` class: Too coupled with display logic
- Using a singleton manager: Less testable, more global state

### 2. Object Re-identification Features

**Decision:** Use color histogram + class label + bounding box aspect ratio for similarity matching.

**Rationale:** These features are:
- Computationally inexpensive: Histogram from YUV plane, O(1) operations
- reasonably discriminative: Color distribution + size + class filters most candidates
- Already available: Detection provides class and bounds; we can compute histogram from cropped region at selection time

**Matching Algorithm:**
- Candidate must match target class exactly
- Compute histogram correlation ( Bhattacharyya coefficient ) between target and candidate crops
- Compare aspect ratio difference (allows some tolerance for perspective changes)
- Weighted score: 0.5 for class match, 0.3 for histogram similarity, 0.2 for aspect ratio
- Threshold: 0.65 to consider a match

**Alternatives Considered:**
- Deep feature embeddings (e.g., Re-ID model): Too heavy for mobile real-time
- Template matching: Sensitive to scale/rotation changes

### 3. Display Filtering During Tracking

**Decision:** Modify the overlay renderer to skip drawing non-tracked objects when `TrackingSession.isActive` is true.

**Rationale:** Minimal change to rendering pipeline; existing detection loop remains, but filtering occurs at draw time.

**Implementation:** 
- Pass `trackingSession` to renderer
- For each detection, check: if tracking active AND detection does not match tracked target → skip rendering
- Tracked object always rendered with green box and tracking data

**Alternatives Considered:**
- Filtering at detection processing stage: Would break detection logging/analytics pipeline
- Separate rendering mode: More invasive refactor

### 4. Lost Object Search Strategy

**Decision:** Search only during tracking active state; limit search to same class objects; stop searching after N frames if no match found? → **No limit**: keep searching indefinitely until match or manual stop.

**Rationale:** User expects tracking to persist as long as they want; occlusions can be long. No artificial timeout.

**Implementation:**
- Each frame while `isLost` is true: iterate new detections, run similarity matching
- On match: set `isLost = false`, update target features with new detection's features (adaptation)
- Update display immediately

### 5. Feature Extraction Implementation

**Decision:** Compute 3D color histogram in YUV space (16 bins per channel) from the cropped object region at selection time. Store as normalized array.

**Rationale:** YUV separates luminance from chrominance, making color more robust to lighting changes. 16×16×16 = 4096 bins is small memory footprint. Comparison via Bhattacharyya coefficient is fast.

## Risks / Trade-offs

**Risk:** Performance overhead from histogram computation and comparison each frame while object is lost.

**Mitigation:** 
- Only compute histogram comparison when in lost state (typically rare)
- Use integer arithmetic with pre-computed lookup tables
- Profile on target device; optimize bin count if needed

**Risk:** False positive tracking (locking onto wrong object with similar color/class).

**Mitigation:**
- Use multi-feature score requiring class match first
- Set conservative threshold (0.65)
- Never update target features after initial selection (prevents drift)
- Manual stop button always available to reset

**Risk:** Object appearance changes significantly (e.g., lighting, angle) causing permanent loss.

**Mitigation:**
- Accept as limitation – user can re-select manually
- Could extend later with incremental feature updates (out of scope)

**Risk:** Tracking state lost on configuration change (screen rotation).

**Mitigation:**
- Save `TrackingSession` state in Activity `onSaveInstanceState`
- Restore on recreation

**Trade-off:** Only tracking one object at a time simplifies design but limits multi-target use.

**Decision:** Consistent with existing `TrackingControl` which manages single selection.

## Migration Plan

1. **Phase 1 – State & Data Structures:** Implement `TrackingSession` class with persistence and serialization
2. **Phase 2 – Feature Extraction:** Add histogram computation at selection time, integrate with `ObjectSelection` to initialize session
3. **Phase 3 – Re-identification:** Implement matching algorithm; add lost-state search loop in detection processor
4. **Phase 4 – Display Filtering:** Modify renderer to filter non-target objects; add UI indicator for "lost" state (optional)
5. **Phase 5 – State Recovery:** Implement save/restore across configuration changes
6. **Phase 6 – Testing & Tuning:** Test on device, adjust histogram bins/threshold if needed

**Rollback:** Disable persistent tracking via feature flag; revert to per-frame tracking if issues arise.

## Open Questions

- Should we display a "Searching..." indicator when object is lost? (UX decision)
- How many histogram bins optimize accuracy vs performance? Need empirical testing on target hardware.
- Should we allow user to manually trigger re-search if auto-recovery fails? Or just rely on stop+re-select?
- Does the existing `TrackingDisplay` need to continue showing velocity when object is lost, or pause updates?
