## 1. OpenSpec Artifacts

- [x] 1.1 Create `openspec/changes/object-motion-to-music/.openspec.yaml`
- [x] 1.2 Create `openspec/changes/object-motion-to-music/proposal.md`
- [x] 1.3 Create `openspec/changes/object-motion-to-music/design.md`

## 2. Configuration Layer

- [x] 2.1 Create `MappingConfig.kt` — data classes (`MappingConfig`, `AxisMapping`, `VelocityMapping`) + `MappingConfigLoader` reading `assets/music-config.json`
- [x] 2.2 Create `HarmonicScale.kt` — scale definitions (chromatic, major, minor, pentatonic_major, pentatonic_minor, blues) + helpers to convert root note "C4" → MIDI number and expand scale to semitone offsets
- [x] 2.3 Create `assets/music-config.json` — default config with pentatonic_major C4

## 3. Audio Player

- [x] 3.1 Create `MusicPlayer.kt` — `SoundPool` wrapper: `init(applicationContext)`, `loadSample(name, resId)`, `play(noteIndex, volume)`, `release()`, `setVolumeMultiplier(0..1)` for fade-out

## 4. Motion Extraction

- [x] 4.1 Create `MotionParameterExtractor.kt` — `normalizePosition(px, screenDim)`, `computeVelocity(center, lastCenter, dt)` with EMA smoothing, `computeAcceleration(velocity, lastVelocity, dt)` with EMA

## 5. Music Mapping

- [x] 5.1 Create `MusicParameterMapper.kt` — `mapPitch(normX, config) → NoteIndex + fractionalOffset`, `mapTimbre(normY, config) → Hz`, `mapVolume(velocity, config) → Float`

## 6. Engine Orchestrator

- [x] 6.1 Create `MotionToMusicEngine.kt` — object (lazy-initialised), `start(context)`, `stop()`, `onPositionUpdate(posX, posY, tsMs, className)`, `onTrackingStateChange(active, isLost)`, `reloadConfig()`

## 7. Instrument Samples

- [x] 7.1 Generate OGG test samples in `app/src/main/res/raw/` — c4.ogg, d4.ogg, e4.ogg, g4.ogg, a4.ogg (440 Hz reference, 300 ms, triangle wave, OGG Vorbis)

## 8. Camera Integration

- [x] 8.1 Modify `CameraFragment.kt` — initialise `MotionToMusicEngine` in `onViewCreated()`, call `onPositionUpdate()` in `onResults()` before `overlay.invalidate()`, call `start()` in `onResume()`, call `stop()` in `onPause()`, call engine on stop-button press

## 9. Verification

- [x] 9.1 `./gradlew assembleDebug` — verify compilation
- [x] 9.2 Static review: check `SoundPool` attributes, no audio deps in build.gradle, lifecycle safety
- [x] 9.3 Confirm OGG files in `res/raw/` are listed in `R.raw.*` namespace
