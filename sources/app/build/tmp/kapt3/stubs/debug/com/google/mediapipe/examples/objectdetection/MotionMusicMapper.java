package com.google.mediapipe.examples.objectdetection;

/**
 * Maps motion parameters to musical synthesis parameters.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002\u0013\u0014B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u0004J\u0016\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/MotionMusicMapper;", "", "()V", "DIRECTION_CHANGE_THRESHOLD", "", "SPEED_HIGH", "SPEED_LOW", "SPEED_MEDIUM", "detectAccelerationSpike", "", "currentSpeed", "avgSpeed", "threshold", "detectDirectionChange", "oldAngle", "newAngle", "getParamsForSpeed", "Lcom/google/mediapipe/examples/objectdetection/MotionMusicMapper$SynthParams;", "speed", "LayerConfig", "SynthParams", "app_debug"})
public final class MotionMusicMapper {
    public static final float SPEED_LOW = 0.2F;
    public static final float SPEED_MEDIUM = 0.6F;
    public static final float SPEED_HIGH = 0.6F;
    public static final float DIRECTION_CHANGE_THRESHOLD = 45.0F;
    @org.jetbrains.annotations.NotNull()
    public static final com.google.mediapipe.examples.objectdetection.MotionMusicMapper INSTANCE = null;
    
    private MotionMusicMapper() {
        super();
    }
    
    /**
     * Get synthesis parameters based on speed.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.google.mediapipe.examples.objectdetection.MotionMusicMapper.SynthParams getParamsForSpeed(float speed) {
        return null;
    }
    
    /**
     * Detect direction change in degrees.
     */
    public final boolean detectDirectionChange(float oldAngle, float newAngle) {
        return false;
    }
    
    /**
     * Detect acceleration spike (2x threshold).
     */
    public final boolean detectAccelerationSpike(float currentSpeed, float avgSpeed, float threshold) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00032\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t\u00a8\u0006\u0018"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/MotionMusicMapper$LayerConfig;", "", "pad", "", "bass", "percussion", "lead", "(ZZZZ)V", "getBass", "()Z", "getLead", "getPad", "getPercussion", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class LayerConfig {
        private final boolean pad = false;
        private final boolean bass = false;
        private final boolean percussion = false;
        private final boolean lead = false;
        
        public final boolean component1() {
            return false;
        }
        
        public final boolean component2() {
            return false;
        }
        
        public final boolean component3() {
            return false;
        }
        
        public final boolean component4() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.google.mediapipe.examples.objectdetection.MotionMusicMapper.LayerConfig copy(boolean pad, boolean bass, boolean percussion, boolean lead) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
        
        public LayerConfig(boolean pad, boolean bass, boolean percussion, boolean lead) {
            super();
        }
        
        public final boolean getPad() {
            return false;
        }
        
        public final boolean getBass() {
            return false;
        }
        
        public final boolean getPercussion() {
            return false;
        }
        
        public final boolean getLead() {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0005H\u00c6\u0003J\'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t\u00a8\u0006\u0018"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/MotionMusicMapper$SynthParams;", "", "layer", "Lcom/google/mediapipe/examples/objectdetection/MotionMusicMapper$LayerConfig;", "cutoff", "", "resonance", "(Lcom/google/mediapipe/examples/objectdetection/MotionMusicMapper$LayerConfig;FF)V", "getCutoff", "()F", "getLayer", "()Lcom/google/mediapipe/examples/objectdetection/MotionMusicMapper$LayerConfig;", "getResonance", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class SynthParams {
        @org.jetbrains.annotations.NotNull()
        private final com.google.mediapipe.examples.objectdetection.MotionMusicMapper.LayerConfig layer = null;
        private final float cutoff = 0.0F;
        private final float resonance = 0.0F;
        
        @org.jetbrains.annotations.NotNull()
        public final com.google.mediapipe.examples.objectdetection.MotionMusicMapper.LayerConfig component1() {
            return null;
        }
        
        public final float component2() {
            return 0.0F;
        }
        
        public final float component3() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.google.mediapipe.examples.objectdetection.MotionMusicMapper.SynthParams copy(@org.jetbrains.annotations.NotNull()
        com.google.mediapipe.examples.objectdetection.MotionMusicMapper.LayerConfig layer, float cutoff, float resonance) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
        
        public SynthParams(@org.jetbrains.annotations.NotNull()
        com.google.mediapipe.examples.objectdetection.MotionMusicMapper.LayerConfig layer, float cutoff, float resonance) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.google.mediapipe.examples.objectdetection.MotionMusicMapper.LayerConfig getLayer() {
            return null;
        }
        
        public final float getCutoff() {
            return 0.0F;
        }
        
        public final float getResonance() {
            return 0.0F;
        }
    }
}