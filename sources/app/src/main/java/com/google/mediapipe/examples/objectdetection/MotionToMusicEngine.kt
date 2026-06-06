package com.google.mediapipe.examples.objectdetection

import android.content.Context
import android.graphics.PointF
import android.util.Log

/**
 * Singleton orchestrator for the motion-to-music pipeline.
 *
 * Data flow per frame:
 *   onPositionUpdate() → MotionParameterExtractor → MusicParameterMapper → MusicPlayer
 *
 * Lifecycle:
 *   start(context)     — load config + register note samples
 *   onPositionUpdate() — called per 30-fps camera frame
 *   onTrackingStateChange() — react to tracking active / lost events
 *   stop()             — release SoundPool + reset state
 */
object MotionToMusicEngine {

    private const val TAG = "MotionToMusicEngine"

    @Volatile private var running: Boolean = false

    @Volatile private var trackingSessionRef: TrackingSession? = null
    @Volatile private var lastScreenWidth = 480
    @Volatile private var lastScreenHeight = 640

    /** Fade multiplier driven by tracking-lost state (1 = full volume, 0 = silent). */
    @Volatile private var fadeMultiplier: Float = 1f

    /**
     * Start the engine: initialise SoundPool, load config, register OGG samples.
     * Called from CameraFragment.onViewCreated / onResume.
     */
    @Synchronized
    fun start(context: Context) {
        if (running) return
        MusicPlayer.init(context)
        MappingConfigLoader.load(context)
        preloadNoteSamples(context)
        fadeMultiplier = 1f
        running = true
        Log.d(TAG, "Engine started")
    }

    /**
     * Called once per camera frame (~30 fps) from CameraFragment.onResults().
     *
     * @param posXPx   object centre X in px (screen coordinates)
     * @param posYPx   object centre Y in px (screen coordinates)
     * @param tsMs     frame timestamp in milliseconds
     * @param _className object class label (currently unused, reserved for future per-class instruments)
     */
    @Synchronized
    fun onPositionUpdate(
        posXPx: Float,
        posYPx: Float,
        tsMs: Long,
        _className: String,
    ) {
        if (!running) return

        val session = trackingSessionRef
        val isActive = session?.isActive ?: false
        val isLost   = session?.isLost   ?: false

        if (!isActive) {
            MusicPlayer.setVolumeMultiplier(0f)
            return
        }

        // ── Fade-out when tracking is lost ────────────────────────────────────
        if (isLost) {
            fadeMultiplier = (fadeMultiplier - 0.016f).coerceAtLeast(0f)
            MusicPlayer.setVolumeMultiplier(fadeMultiplier)
            return
        } else {
            fadeMultiplier = 1f
        }

        // ── Extract motion ───────────────────────────────────────────────────
        val center = PointF(posXPx, posYPx)
        val frame = MotionParameterExtractor.extractParameters(
            centerPx    = center,
            screenWidthPx  = lastScreenWidth,
            screenHeightPx = lastScreenHeight,
            timestampMs   = tsMs,
        )

        // ── Build scale note list for this frame ─────────────────────────────
        val cfg = MappingConfigLoader.config
        val scaleNotes = harmonicScaleToNoteNames(
            cfgScale        = cfg.scale,
            cfgRootNote     = cfg.rootNote,
            cfg.mapping.xAxis.notes ?: emptyList(),
            customSemitones = cfg.customScale,
        )

        // ── Map to musical parameters ─────────────────────────────────────────
        val params = MusicParameterMapper.map(
            normX               = frame.normalizedX,
            normY               = frame.normalizedY,
            smoothedVelocityPxS = frame.smoothedVelocity,
            config               = cfg,
            scale               = scaleNotes,
            isActiveFrame       = !isLost,
        )

        Log.d(TAG, "Frame: normX=${frame.normalizedX}, normY=${frame.normalizedY}, vel=${frame.smoothedVelocity}")
        Log.d(TAG, "Mapped: noteIdx=${params.noteIndex}, vol=${params.volume}, filterHz=${params.filterHz}")

        // ── Play note ─────────────────────────────────────────────────────────
        if (params.volume > 0f) {
            MusicPlayer.play(params.noteIndex, params.volume)
        }
    }

    /** Update active/lost flags and react to lifecycle events. */
    @Synchronized
    fun onTrackingStateChange(active: Boolean, isLost: Boolean) {
        if (!active) {
            fadeMultiplier = 0f
            MusicPlayer.setVolumeMultiplier(0f)
            MotionParameterExtractor.reset()
            MusicParameterMapper.resetSmoothedPosition()
        } else {
            fadeMultiplier = 1f
        }
    }

    /** Attach the current [TrackingSession] for isActive / isLost lookups. */
    @Synchronized
    fun setTrackingSession(session: TrackingSession?) {
        trackingSessionRef = session
    }

    /** Update cached screen dimensions — call after layout stabilises. */
    @Synchronized
    fun updateScreenDimensions(widthPx: Int, heightPx: Int) {
        lastScreenWidth  = widthPx
        lastScreenHeight = heightPx
    }

    /**
     * Hot-reload `assets/music-config.json` and refresh note registrations.
     * No engine restart needed.
     */
    @Synchronized
fun reloadConfig(context: Context) {
         MappingConfigLoader.reload(context)
         preloadNoteSamples(context)
         fadeMultiplier = 1f
     }

@Synchronized
    fun setInstrument(context: Context, instrument: String) {
        MappingConfigLoader.setInstrument(instrument)
        if (instrument == "disabled") {
            MusicPlayer.release()
            Log.d(TAG, "Instrument disabled - released SoundPool")
        } else {
            preloadNoteSamples(context)
        }
        fadeMultiplier = 1f
    }

    /**
     * Stop the engine: release SoundPool, reset all motion smoothing state.
     * Called from CameraFragment.onPause / onStop.
     */
    @Synchronized
    fun stop() {
        if (!running) return
        MusicPlayer.release()
        MotionParameterExtractor.reset()
        MusicParameterMapper.resetSmoothedPosition()
        fadeMultiplier = 0f
        trackingSessionRef = null
        running = false
        Log.d(TAG, "Engine stopped, SoundPool released")
    }

    // ── private ─────────────────────────────────────────────────────────────

    /**
     * Build a noteIndex → R.raw resId map from the current config's note list,
     * discover resources by scanning the `R.raw` class via reflection.
     */
    private fun preloadNoteSamples(context: Context) {
        val cfg = MappingConfigLoader.config

        // Skip loading if instrument is disabled
        if (cfg.instrument == "disabled") {
            Log.d(TAG, "Instrument disabled - skipping sample loading")
            return
        }

        val cfgNoteNames = harmonicScaleToNoteNames(
            cfgScale        = cfg.scale,
            cfgRootNote     = cfg.rootNote,
            cfg.mapping.xAxis.notes ?: emptyList(),
            customSemitones = cfg.customScale,
        )
        Log.d(TAG, "Looking for note samples for instrument '${cfg.instrument}': $cfgNoteNames")
        val resMap = loadNoteResourceMap(context, cfgNoteNames, cfg.instrument)
        if (resMap.isEmpty()) {
            Log.w(TAG, "No res/raw/ samples found for notes $cfgNoteNames with instrument '${cfg.instrument}' — engine muted")
            return
        }
        Log.d(TAG, "Found ${resMap.size} note samples: ${resMap.entries.joinToString { "idx=${it.key}:resId=${it.value}" }}")
        MusicPlayer.registerNotes(context, resMap)
        Log.d(TAG, "Registered ${resMap.size} note samples for instrument '${cfg.instrument}': $cfgNoteNames")
    }
}
