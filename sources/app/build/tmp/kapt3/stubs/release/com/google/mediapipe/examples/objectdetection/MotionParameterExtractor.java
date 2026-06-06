package com.google.mediapipe.examples.objectdetection;

/**
 * Extracts and smooths motion parameters from per-frame camera data.
 *
 * The extractor owns its own smoothing state, so it is safe to call
 * [extractParameters] exactly once per 30-fps camera frame.
 *
 * Smoothing factors for velocity and acceleration are baked in here.
 * Position-to-pitch and position-to-timbre smoothing instead live in
 * [MusicParameterMapper], driven by MappingConfig values.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u001dB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004H\u0002J&\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\rJ\u0006\u0010\u0019\u001a\u00020\u001aJ\u0018\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/MotionParameterExtractor;", "", "()V", "ACC_ALPHA", "", "TAG", "", "VEL_ALPHA", "emaAcceleration", "emaVelocity", "lastCenter", "Landroid/graphics/PointF;", "lastTimestampNanos", "", "lastVelocityPxS", "accelerationFromVelocity", "currentVel", "dt", "extractParameters", "Lcom/google/mediapipe/examples/objectdetection/MotionParameterExtractor$MotionFrame;", "centerPx", "screenWidthPx", "", "screenHeightPx", "timestampMs", "reset", "", "velocityFromCentres", "current", "MotionFrame", "app_release"})
public final class MotionParameterExtractor {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "MotionParameterExtractor";
    
    /**
     * EMA factor for velocity smoothing (larger = snappier).
     */
    private static final float VEL_ALPHA = 0.35F;
    
    /**
     * EMA factor for acceleration smoothing.
     */
    private static final float ACC_ALPHA = 0.25F;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile android.graphics.PointF lastCenter;
    @kotlin.jvm.Volatile()
    private static volatile long lastTimestampNanos = 0L;
    @kotlin.jvm.Volatile()
    private static volatile float lastVelocityPxS = 0.0F;
    @kotlin.jvm.Volatile()
    private static volatile float emaVelocity = 0.0F;
    @kotlin.jvm.Volatile()
    private static volatile float emaAcceleration = 0.0F;
    @org.jetbrains.annotations.NotNull()
    public static final com.google.mediapipe.examples.objectdetection.MotionParameterExtractor INSTANCE = null;
    
    private MotionParameterExtractor() {
        super();
    }
    
    /**
     * Reset all smoothing — call when a new tracking session starts.
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void reset() {
    }
    
    /**
     * Extract one frame's worth of motion data.
     *
     * @param centerPx     Tracked object centre in screen px coordinates.
     * @param screenWidthPx  View width in px.
     * @param screenHeightPx View height in px.
     * @param timestampMs  Frame wall-clock time in milliseconds.
     */
    @kotlin.jvm.Synchronized()
    @org.jetbrains.annotations.NotNull()
    public final synchronized com.google.mediapipe.examples.objectdetection.MotionParameterExtractor.MotionFrame extractParameters(@org.jetbrains.annotations.NotNull()
    android.graphics.PointF centerPx, int screenWidthPx, int screenHeightPx, long timestampMs) {
        return null;
    }
    
    private final float velocityFromCentres(android.graphics.PointF current, float dt) {
        return 0.0F;
    }
    
    private final float accelerationFromVelocity(float currentVel, float dt) {
        return 0.0F;
    }
    
    /**
     * Result of a single [extractParameters] call.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003JO\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001J\t\u0010 \u001a\u00020!H\u00d6\u0001R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f\u00a8\u0006\""}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/MotionParameterExtractor$MotionFrame;", "", "normalizedX", "", "normalizedY", "rawVelocity", "smoothedVelocity", "rawAcceleration", "smoothedAcceleration", "dtSeconds", "(FFFFFFF)V", "getDtSeconds", "()F", "getNormalizedX", "getNormalizedY", "getRawAcceleration", "getRawVelocity", "getSmoothedAcceleration", "getSmoothedVelocity", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_release"})
    public static final class MotionFrame {
        private final float normalizedX = 0.0F;
        private final float normalizedY = 0.0F;
        private final float rawVelocity = 0.0F;
        private final float smoothedVelocity = 0.0F;
        private final float rawAcceleration = 0.0F;
        private final float smoothedAcceleration = 0.0F;
        private final float dtSeconds = 0.0F;
        
        public final float component1() {
            return 0.0F;
        }
        
        public final float component2() {
            return 0.0F;
        }
        
        public final float component3() {
            return 0.0F;
        }
        
        public final float component4() {
            return 0.0F;
        }
        
        public final float component5() {
            return 0.0F;
        }
        
        public final float component6() {
            return 0.0F;
        }
        
        public final float component7() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.google.mediapipe.examples.objectdetection.MotionParameterExtractor.MotionFrame copy(float normalizedX, float normalizedY, float rawVelocity, float smoothedVelocity, float rawAcceleration, float smoothedAcceleration, float dtSeconds) {
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
        
        public MotionFrame(float normalizedX, float normalizedY, float rawVelocity, float smoothedVelocity, float rawAcceleration, float smoothedAcceleration, float dtSeconds) {
            super();
        }
        
        public final float getNormalizedX() {
            return 0.0F;
        }
        
        public final float getNormalizedY() {
            return 0.0F;
        }
        
        public final float getRawVelocity() {
            return 0.0F;
        }
        
        public final float getSmoothedVelocity() {
            return 0.0F;
        }
        
        public final float getRawAcceleration() {
            return 0.0F;
        }
        
        public final float getSmoothedAcceleration() {
            return 0.0F;
        }
        
        public final float getDtSeconds() {
            return 0.0F;
        }
    }
}