## Context

The app already tracks a single object across frames (`TrackingSession`), computes its velocity in `OverlayView.onDraw()`, and runs at a steady ~30 fps via `CameraFragment.onResults()`. None of this motion data is currently sonified. The goal is to add real-time musical feedback driven entirely by this existing data — position becoming pitch, velocity becoming volume, and Y-axis displacement becoming timbre — all governed by a single JSON config file.

## Goals / Non-Goals

**Goals:**
- Map X position → note within a configurable harmonic scale (pentatonic, major, chromatic, …)
- Map Y position → filter cutoff / timbre (200–2000 Hz range)
- Map velocity magnitude → volume with configurable attack/release and a quiet threshold
- Smooth all raw values with EMA filters to avoid audible stepping/jitter
- Fade out smoothly when the tracked object is lost; cut to silence when tracking stops
- Keep configuration in one JSON file; support hot-reload without restart

**Non-Goals:**
- Multi-object sonification
- Audio synthesis (only sample playback)
- Audio recording / export
- Real-time spectrum analysis
- Settings UI for the config file (edited externally)

## Decisions

### 1. audio API: SoundPool

**Decision:** Use Android `SoundPool` (maxStreams = 8, `AudioManager.STREAM_MUSIC`).

**Rationale:** `SoundPool` is purpose-built for short, low-latency samples, well under 50 ms on modern Android. No additional library dependency. Thread-safe for concurrent calls from the rendering thread.

**Alternatives considered:** `AudioTrack` for continuous synthesis — rejected (over-engineered, no natural sample-map support); `MediaPlayer` — rejected (too high latency for note-by-note playback).

### 2. Sample storage

**Decision:** Pre-recorded OGG files in `res/raw/` (e.g. `c4.ogg`, `d4.ogg`, …).

**Rationale:** OGG is natively supported by Android `SoundPool.load(context, R.raw.xxx, 1)`. No codec dependency. Files are small and load fast. Future scales can add more samples without code changes.

### 3. Point of injection: CameraFragment.onResults()

**Decision:** Call `MotionToMusicEngine.onPositionUpdate()` inside the `runOnUiThread` block, **before** `overlay.invalidate()` (line 444), while `trackingSession` and detections are still in scope.

**Rationale:** Placing the call before `invalidate()` ensures the engine sees the complete frame state for this cycle. The position, object class, and lost state are all current at that point. The call is synchronous but non-blocking (SoundPool handles its own thread).

### 4. Velocity source

**Decision:** Read velocity from `TrackingSession.lastKnownPosition` + frame timestamp inside `MotionParameterExtractor`, computing `(centre − last_centre) / dt` per frame — replicating the existing `OverlayView` formula.

**Rationale:** The `OverlayView` velocity is transient draw logic; migrating to `MotionParameterExtractor` keeps the music engine independent of the view layer and makes the velocity computation reusable.

**Alternatives considered:** Reading `vx/vy` via an exposed overlay property — rejected (tight coupling, velocity text display logic mixed with audio).

### 5. Config hot-reload

**Decision:** `MappingConfig.reload(context)` reads `assets/music-config.json` on a background thread and replaces the in-memory `MappingConfig` holder atomically.

**Rationale:** `apply {}` on a MutableState/volatile holder prevents partial reads. No engine restart needed; next `onPositionUpdate()` call picks up the new config immediately.

### 6. Harmonic scale abstraction

**Decision:** `HarmonicScale` maps a scale name ("pentatonic_major", "chromatic", "major", etc.) to an ordered list of semitone offsets from the root note. Root note is specified in scientific pitch notation (e.g. "C4") and converted to a MIDI number by the mapper.

**Rationale:** Separating scale structure from config loading allows scales to be defined declaratively in code while remaining open to `customScale` overrides from JSON.

## Data Flow

```
CameraFragment.onResults() [30 fps]
  │
  ├─ TrackingSession updated (position, isLost, className)
  │
  ├─ [NEW] MotionToMusicEngine.onPositionUpdate(posX_px, posY_px, tsMs, className)
  │      │
  │      ├─ MotionParameterExtractor
  │      │     ├─ normalizedX = posX_px / displayWidth  → [0, 1]
  │      │     ├─ normalizedY = posY_px / displayHeight → [0, 1]
  │      │     ├─ velocity    = sqrt(vx² + vy²)         → EMA-smoothed
  │      │     └─ acceleration = d(velocity)/dt          → EMA-smoothed
  │      │
  │      ├─ MusicParameterMapper  [reads MappingConfig]
  │      │     ├─ pitchIndex   = floor(normX * notes.count).coerce(0..max)
  │      │     │       └─ (glissando) lerp between adjacent notes for sub-frame microtonal
  │      │     ├─ filterHz     = lerp(normY, filterLo, filterHi)
  │      │     └─ volume       = clamp((velocity - threshold) / (maxV - threshold), 0..1)
  │      │
  │      └─ MusicPlayer
  │            ├─ if velocity > velocityThreshold → play(noteSampleIndex, volume)
  │            ├─ apply attack (0.05 s) / release (0.2 s) envelope to volume
  │            └─ if isLost → ramp volume to 0 over 500 ms
  │
  └─ overlay.invalidate()  [unchanged]
```

## Format JSON de configuration

```json
{
  "scale": "pentatonic_major",
  "rootNote": "C4",
  "customScale": null,
  "mapping": {
    "xAxis": {
      "parameter": "pitch",
      "notes": ["C4","D4","E4","G4","A4"],
      "smoothing": 0.1,
      "glissando": true
    },
    "yAxis": {
      "parameter": "timbre",
      "filterRange": [200, 2000],
      "smoothing": 0.05
    },
    "velocity": {
      "parameter": "volume",
      "min": 0.3,
      "max": 1.0,
      "attack": 0.05,
      "release": 0.2,
      "threshold": 10.0
    }
  },
  "objectClassInstruments": {
    "person": "strings",
    "car": "bass",
    "default": "sine"
  }
}
```

## Risks / Trade-offs

| Risk | Mitigation |
|------|------------|
| High-velocity object causes rapid note-fire (>60 note/s) | `SoundPool` handles concurrent streams; volume gating + sample duration (~200 ms) provides natural overlap ceiling |
| Velocity spikes on re-acquisition after loss | EMA smoothing factor; hysteresis on re-entry |
| Fade-out noise on fast stop | Cut to silence directly on `isLost → true` frame; skip fade when app backgrounds |
| Config file absent or malformed | Fall back to built-in pentatonic C4; log warning and continue |
| Device-specific SoundPool latency | Specify `AudioAttributes` explicitly; no reliance on defaults |
| Thread contention on `lastKnownPosition` | `TrackingSession` reads are on the main thread (CameraFragment); no concurrent writes |
