## 1. Data Structures & State Management

- [x] 1.1 Create `TrackingSession` class with fields: `targetFeatures` (float array), `targetClass` (String), `targetInitialBounds` (Rect), `isActive` (boolean), `isLost` (boolean), `framesSinceLastSeen` (int), `lastKnownPosition` (PointF)
- [x] 1.2 Implement `TrackingSession` serialization for state save/restore (Parcelable or Bundle key-value)
- [x] 1.3 Add `TrackingSession` field to main tracking controller/activity
- [x] 1.4 Implement `startTrackingSession(detection)` method to initialize session from selected object
- [x] 1.5 Implement `stopTrackingSession()` method to clear state and reset flags
- [x] 1.6 Add logic to update `isLost` flag when target not detected for 3+ consecutive frames

## 2. Feature Extraction (Re-identification)

- [x] 2.1 Implement color histogram computation: crop detection region, convert to YUV, compute 3D histogram (16 bins per channel → 4096 bins), normalize
- [x] 2.2 Create `computeFeatures(detection)` utility returning feature vector (histogram + aspect ratio)
- [x] 2.3 Integrate feature extraction into object selection: when user taps object, compute and store features in `TrackingSession`
- [x] 2.4 Implement Bhattacharyya coefficient function for histogram similarity comparison
- [x] 2.5 Create `computeSimilarity(targetFeatures, candidateDetection)` function returning weighted score (class match 0.5, histogram 0.3, aspect ratio 0.2)

## 3. Re-acquisition & Lost-state Search

- [x] 3.1 In detection processing loop, add conditional: if `trackingSession.isActive && trackingSession.isLost`, iterate detections of same class
- [x] 3.2 For each candidate, call `computeSimilarity()`; if score ≥ 0.65, set `targetDetection = candidate`, `isLost = false`
- [x] 3.3 Update `lastKnownPosition` and reset `framesSinceLastSeen` on successful re-acquisition
- [x] 3.4 Add handling: when object re-acquired, trigger UI update to show green bounding box
- [x] 3.5 Ensure matching runs every frame while lost (no timeout limit)

## 4. Display Filtering (Exclusive Tracking View)

- [x] 4.1 Modify overlay renderer's `drawDetections()` method to accept `trackingSession` parameter
- [x] 4.2 Add conditional logic: if `trackingSession.isActive`, filter detections to only the `targetDetection` (skip all others)
- [x] 4.3 Ensure tracked object still drawn with green bounding box and position/velocity data
- [x] 4.4 Handle edge case: when lost and showing no bounding boxes, verify non-tracked objects are hidden
- [x] 4.5 Update existing `TrackingDisplay` to continue showing data while lost (pause velocity updates if preferred)

## 5. Integration with Existing Systems

- [x] 5.1 Connect `startTrackingSession()` to object selection event (tap handler) - currently sets color to green
- [x] 5.2 Ensure stop button calls `stopTrackingSession()` in addition to existing cleanup
- [x] 5.3 Update velocity computation to use `trackingSession.lastKnownPosition` even when lost (smooth display)
- [x] 5.4 Pass `trackingSession` from Activity to renderer and detection processor
- [x] 5.5 Verify state persists across detection frames: selection → lost → re-acquisition → manual stop

## 6. State Persistence & Lifecycle

- [x] 6.1 Implement `onSaveInstanceState()` to write `TrackingSession` fields to Bundle (Parcelable or primitives)
- [x] 6.2 Implement `onRestoreInstanceState()` to reconstruct `TrackingSession` with saved features and state
- [x] 6.3 Test state recovery: select object → rotate screen → verify tracking remains active with same target features
- [x] 6.4 Ensure session clears properly on app exit (already handled by Activity recreation)

## 7. Testing & Validation

- [x] 7.1 Unit test `computeFeatures()` produces consistent histograms for same object under minor lighting variations
- [x] 7.2 Unit test `computeSimilarity()` returns high score for same object across frames, low score for different objects
- [x] 7.3 Integration test: select object, cause occlusion (move object out), re-enter view → verify auto-resume
- [x] 7.4 Integration test: verify only tracked object's bounding box visible during tracking
- [x] 7.5 Performance test: profile histogram matching while lost on target device; ensure < 5ms per frame overhead
- [x] 7.6 Edge case: multiple objects of same class appear → verify correct target re-acquired based on appearance

## 8. Polish & UX Refinement

- [x] 8.1 (Optional) Add "Searching..." status indicator to UI when object is lost
- [x] 8.2 Tune histogram bin count (16^3) and similarity threshold (0.65) based on test results
- [x] 8.3 Verify stop button behavior unchanged and prominently accessible
- [x] 8.4 Update any existing documentation or in-app hints about tracking behavior
