## Context

Current system uses pre-recorded OGG samples loaded into SoundPool. Want to transition to procedural synthesis for dynamic, real-time responsive music. The MotionToMusicEngine processes ~30fps position updates and needs to generate audio based on motion intensity in real-time.

## Goals / Non-Goals

**Goals:**
- Generate waveforms programmatically (sine, triangle, square, noise)
- Map motion parameters to synthesis parameters (cutoff, resonance, ADSR)
- Implement transition effects (riser, drop, impact) based on motion changes
- Maintain low latency audio playback

**Non-Goals:**
- Complex audio effects (reverb, delay) - use simple filter sweeps
- Sample-accurate timing - good enough for 30fps motion
- MIDI file generation - real-time synthesis only

## Decisions

**Procedural Synthesis Engine**
- Use Android AudioTrack with buffer streaming for low-latency output
- Generate waveforms mathematically each frame rather than loading samples
- Simple waveforms: sine (pads), square (bass), triangle (arps), noise (impacts)

**Intensity Mapping**
- Speed < 0.2: Pad layer (sine wave, low cutoff, high reverb)
- Speed 0.2-0.6: Add bass layer (square wave, moderate cutoff)
- Speed > 0.6: Add percussion (noise bursts) and lead synth

**Transition System**
- Riser: Frequency sweep upward over 1-2 seconds on acceleration spike
- Drop: Momentary silence on direction change > 45°
- Impact: Noise burst on abrupt stop

**Architecture**
- SynthEngine class handles waveform generation
- MotionMusicMapper translates motion→synthesis parameters
- AudioTrack runs on dedicated thread for real-time output

## Risks / Trade-offs

[CPU Usage] → Mitigation: Generate only active layers, simple math-based synthesis
[Latency] → Mitigation: Small buffer sizes (512 samples), AudioTrack in streaming mode
[Audio Quality] → Mitigation: Use band-limited waveforms, avoid aliasing