package com.google.mediapipe.examples.objectdetection;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\r\u0010\u0012\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\rJ\u001e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\tJ\"\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\bJ\u0006\u0010\u001b\u001a\u00020\u000fJ\r\u0010\u001c\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0002\u0010\u0013J\u000e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/MusicPlayer;", "", "()V", "_initialized", "", "isInitialized", "()Z", "noteIds", "", "", "soundPool", "Landroid/media/SoundPool;", "volumeMultiplier", "", "init", "", "context", "Landroid/content/Context;", "pauseAll", "()Lkotlin/Unit;", "play", "index", "volume", "registerNote", "resId", "registerNotes", "noteResIdMap", "release", "resumeAll", "setVolumeMultiplier", "mult", "app_release"})
public final class MusicPlayer {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile android.media.SoundPool soundPool;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.NotNull()
    private static volatile java.util.Map<java.lang.Integer, java.lang.Integer> noteIds;
    @kotlin.jvm.Volatile()
    private static volatile float volumeMultiplier = 1.0F;
    @kotlin.jvm.Volatile()
    private static volatile boolean _initialized = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.google.mediapipe.examples.objectdetection.MusicPlayer INSTANCE = null;
    
    private MusicPlayer() {
        super();
    }
    
    @kotlin.jvm.Synchronized()
    public final synchronized void init(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    /**
     * Register note samples: each pair is [logical noteIndex → R.raw resource id].
     * @param context    Application context (needed by SoundPool.load)
     * @param noteResIdMap index → resId for each note to pre-load
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void registerNotes(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.Integer, java.lang.Integer> noteResIdMap) {
    }
    
    @kotlin.jvm.Synchronized()
    public final synchronized void registerNote(int index, @org.jetbrains.annotations.NotNull()
    android.content.Context context, int resId) {
    }
    
    @kotlin.jvm.Synchronized()
    public final synchronized void play(int index, float volume) {
    }
    
    @kotlin.jvm.Synchronized()
    public final synchronized void setVolumeMultiplier(float mult) {
    }
    
    @kotlin.jvm.Synchronized()
    @org.jetbrains.annotations.Nullable()
    public final synchronized kotlin.Unit pauseAll() {
        return null;
    }
    
    @kotlin.jvm.Synchronized()
    @org.jetbrains.annotations.Nullable()
    public final synchronized kotlin.Unit resumeAll() {
        return null;
    }
    
    @kotlin.jvm.Synchronized()
    public final synchronized void release() {
    }
    
    @kotlin.jvm.Synchronized()
    public final synchronized boolean isInitialized() {
        return false;
    }
}