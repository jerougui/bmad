## Why

An object tracked by the camera has an intrinsic expressive dimension: its position, velocity, and trajectory. This information is currently lost — it is computed every frame for the UI overlay but never translated into sound. By sonifying the tracked object's motion in real time, the app gains a new immersion channel: the user hears where the object is, how fast it moves, and whether it is being lost — all without looking at the screen.

## What Changes

- A JSON-driven mapping layer translates raw camera-space coordinates (X, Y, velocity px/s) into musical parameters (pitch note, timbre filter, volume).
- The mapping is fully configurable via a single `assets/music-config.json` file — musical scale, root note, smoothing factors, velocity threshold, and per-axis behaviour.
- A lightweight audio engine wraps `SoundPool` to play pre-recorded OGG note samples on each incoming frame, with EMA-smoothed velocity gating and a progressive fade-out when tracking is lost.
- No new UI is required. The engine hooks into the existing `CameraFragment.onResults()` 30fps door and reads from the existing `TrackingSession` state.

## Capabilities

### New Capabilities

- `object-motion-sonification`: Translates a tracked object's real-time position and velocity into musical pitch, timbre, and volume, using configurable harmonic scales and OGG sample playback.
- `motion-music-config`: A single JSON file defines the full mapping between motion axes and musical parameters, including smoothing, glissando, and velocity threshold.

### Modified Capabilities

- `CameraFragment.onResults()`: gains an optional call to `MotionToMusicEngine.onPositionUpdate()` after tracking state is updated and before `overlay.invalidate()`, without altering existing tracking, detection, or rendering logic.

## Impact

- **New Files**: `MappingConfig.kt`, `HarmonicScale.kt`, `MusicPlayer.kt`, `MotionParameterExtractor.kt`, `MusicParameterMapper.kt`, `MotionToMusicEngine.kt`, `assets/music-config.json`, OGG samples in `res/raw/`
- **Modified Files**: `CameraFragment.kt` (one call site, ~3 lines added in `onResults()`)
- **Audio thread safety**: `SoundPool` is natively thread-safe; all playback calls are non-blocking.
- **Lifecycle safety**: Engine is started/stopped in `CameraFragment` lifecycle to release `SoundPool` and avoid leaks.
- **No new dependencies**: Uses Android SDK only (`SoundPool`, `AudioManager`, `AssetManager`).
