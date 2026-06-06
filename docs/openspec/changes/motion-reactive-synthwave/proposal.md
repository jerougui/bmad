## Why

The current motion-to-music system uses static instrument samples with fixed mappings. Users want dynamic, cinematic synthwave-style music that evolves in real-time with motion intensity, direction changes, and acceleration patterns - creating an immersive audio experience where music responds to movement dramatics.

## What Changes

- Add procedural synthesis engine (sine/triangle/square waves) for dynamic sound generation
- Add intensity-based music mapping: slow → atmospheric pads, fast → driving bass and percussion
- Add acceleration-triggered musical transitions (risers, drops, impacts)
- Add direction change detection for synth modulation
- Add motion intensity → filter cutoff and reverb mapping
- Add multi-layer synth architecture (pads, arps, bass, leads)

## Capabilities

### New Capabilities
- `procedural-synth-engine`: Core synthesis engine for generating waveforms programmatically
- `motion-music-mapping`: Map motion parameters (speed, acceleration, direction) to musical parameters
- `musical-transitions`: Transition system for riser/drop effects based on motion changes

### Modified Capabilities
- `motion-music-config`: Add intensity thresholds, filter mappings, and transition triggers to JSON config

## Impact

- **New Files**: SynthEngine.kt, MotionMusicMapper.kt, TransitionGenerator.kt
- **Modified Files**: MotionToMusicEngine.kt, MappingConfig.kt, music-config.json
- **No new dependencies**: Uses Android's built-in audio synthesis APIs
- **Runtime impact**: Real-time synthesis may increase CPU usage during active tracking