package com.google.mediapipe.examples.objectdetection;

/**
 * Manages musical layers (pad, bass, percussion, lead) with fade in/out.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0007H\u0002J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0007H\u0002J\u000e\u0010\r\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0007J\u0006\u0010\u000e\u001a\u00020\u0004J\u000e\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/LayerManager;", "", "()V", "currentLayers", "Lcom/google/mediapipe/examples/objectdetection/MotionMusicMapper$LayerConfig;", "fadeState", "", "", "", "fadeIn", "", "layer", "fadeOut", "getFade", "getLayers", "updateLayers", "newLayers", "app_debug"})
public final class LayerManager {
    @org.jetbrains.annotations.NotNull()
    private com.google.mediapipe.examples.objectdetection.MotionMusicMapper.LayerConfig currentLayers;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Float> fadeState = null;
    
    public LayerManager() {
        super();
    }
    
    /**
     * Update layers with smooth transitions.
     */
    public final void updateLayers(@org.jetbrains.annotations.NotNull()
    com.google.mediapipe.examples.objectdetection.MotionMusicMapper.LayerConfig newLayers) {
    }
    
    private final void fadeIn(java.lang.String layer) {
    }
    
    private final void fadeOut(java.lang.String layer) {
    }
    
    /**
     * Get current layer configuration.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.google.mediapipe.examples.objectdetection.MotionMusicMapper.LayerConfig getLayers() {
        return null;
    }
    
    /**
     * Get fade value for a layer (0=no layer, 1=full volume).
     */
    public final float getFade(@org.jetbrains.annotations.NotNull()
    java.lang.String layer) {
        return 0.0F;
    }
}