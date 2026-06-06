package com.google.mediapipe.examples.objectdetection;

/**
 * Singleton orchestrator for the motion-to-music pipeline.
 *
 * Data flow per frame:
 *  onPositionUpdate() → MotionParameterExtractor → MusicParameterMapper → MusicPlayer
 *
 * Lifecycle:
 *  start(context)     — load config + register note samples
 *  onPositionUpdate() — called per 30-fps camera frame
 *  onTrackingStateChange() — react to tracking active / lost events
 *  stop()             — release SoundPool + reset state
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0004J\u0016\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u000bJ\u0010\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u000e\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aJ\u0016\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u0004J\u0010\u0010\u001e\u001a\u00020\u000f2\b\u0010\u001f\u001a\u0004\u0018\u00010\rJ\u000e\u0010 \u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010!\u001a\u00020\u000fJ\u0016\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\b2\u0006\u0010$\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/MotionToMusicEngine;", "", "()V", "TAG", "", "fadeMultiplier", "", "lastScreenHeight", "", "lastScreenWidth", "running", "", "trackingSessionRef", "Lcom/google/mediapipe/examples/objectdetection/TrackingSession;", "onPositionUpdate", "", "posXPx", "posYPx", "tsMs", "", "_className", "onTrackingStateChange", "active", "isLost", "preloadNoteSamples", "context", "Landroid/content/Context;", "reloadConfig", "setInstrument", "instrument", "setTrackingSession", "session", "start", "stop", "updateScreenDimensions", "widthPx", "heightPx", "app_debug"})
public final class MotionToMusicEngine {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "MotionToMusicEngine";
    @kotlin.jvm.Volatile()
    private static volatile boolean running = false;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.google.mediapipe.examples.objectdetection.TrackingSession trackingSessionRef;
    @kotlin.jvm.Volatile()
    private static volatile int lastScreenWidth = 480;
    @kotlin.jvm.Volatile()
    private static volatile int lastScreenHeight = 640;
    
    /**
     * Fade multiplier driven by tracking-lost state (1 = full volume, 0 = silent).
     */
    @kotlin.jvm.Volatile()
    private static volatile float fadeMultiplier = 1.0F;
    @org.jetbrains.annotations.NotNull()
    public static final com.google.mediapipe.examples.objectdetection.MotionToMusicEngine INSTANCE = null;
    
    private MotionToMusicEngine() {
        super();
    }
    
    /**
     * Start the engine: initialise SoundPool, load config, register OGG samples.
     * Called from CameraFragment.onViewCreated / onResume.
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void start(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    /**
     * Called once per camera frame (~30 fps) from CameraFragment.onResults().
     *
     * @param posXPx   object centre X in px (screen coordinates)
     * @param posYPx   object centre Y in px (screen coordinates)
     * @param tsMs     frame timestamp in milliseconds
     * @param _className object class label (currently unused, reserved for future per-class instruments)
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void onPositionUpdate(float posXPx, float posYPx, long tsMs, @org.jetbrains.annotations.NotNull()
    java.lang.String _className) {
    }
    
    /**
     * Update active/lost flags and react to lifecycle events.
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void onTrackingStateChange(boolean active, boolean isLost) {
    }
    
    /**
     * Attach the current [TrackingSession] for isActive / isLost lookups.
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void setTrackingSession(@org.jetbrains.annotations.Nullable()
    com.google.mediapipe.examples.objectdetection.TrackingSession session) {
    }
    
    /**
     * Update cached screen dimensions — call after layout stabilises.
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void updateScreenDimensions(int widthPx, int heightPx) {
    }
    
    /**
     * Hot-reload `assets/music-config.json` and refresh note registrations.
     * No engine restart needed.
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void reloadConfig(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @kotlin.jvm.Synchronized()
    public final synchronized void setInstrument(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String instrument) {
    }
    
    /**
     * Stop the engine: release SoundPool, reset all motion smoothing state.
     * Called from CameraFragment.onPause / onStop.
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void stop() {
    }
    
    /**
     * Build a noteIndex → R.raw resId map from the current config's note list,
     * discover resources by scanning the `R.raw` class via reflection.
     */
    private final void preloadNoteSamples(android.content.Context context) {
    }
}