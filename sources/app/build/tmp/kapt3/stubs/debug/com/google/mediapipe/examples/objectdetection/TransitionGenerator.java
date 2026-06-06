package com.google.mediapipe.examples.objectdetection;

/**
 * Generates musical transition effects (riser, drop, impact).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0017\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006J$\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\tJ \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\t2\b\b\u0002\u0010\u000f\u001a\u00020\t\u00a8\u0006\u0010"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/TransitionGenerator;", "", "()V", "generateImpact", "", "durationMs", "", "generateRiser", "startFreq", "", "endFreq", "shouldTriggerRiser", "", "acceleration", "avgSpeed", "threshold", "app_debug"})
public final class TransitionGenerator {
    @org.jetbrains.annotations.NotNull()
    public static final com.google.mediapipe.examples.objectdetection.TransitionGenerator INSTANCE = null;
    
    private TransitionGenerator() {
        super();
    }
    
    /**
     * Generate riser samples - upward frequency sweep.
     */
    @org.jetbrains.annotations.NotNull()
    public final short[] generateRiser(int durationMs, float startFreq, float endFreq) {
        return null;
    }
    
    /**
     * Generate impact noise burst.
     */
    @org.jetbrains.annotations.NotNull()
    public final short[] generateImpact(int durationMs) {
        return null;
    }
    
    /**
     * Check if riser should be triggered.
     */
    public final boolean shouldTriggerRiser(float acceleration, float avgSpeed, float threshold) {
        return false;
    }
}