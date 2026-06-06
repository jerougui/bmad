package com.google.mediapipe.examples.objectdetection;

/**
 * Maps raw motion parameters to musical output parameters
 * as defined by the loaded [MappingConfig].
 *
 * All output values are clamped so callers never need to double-check.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002\"#B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0002J<\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u00142\u0006\u0010\u0015\u001a\u00020\u0016J4\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000e\u001a\u00020\u00062\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u00142\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u00142\u0006\u0010\u001b\u001a\u00020\u0016H\u0002J \u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0006\u0010 \u001a\u00020!R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/MusicParameterMapper;", "", "()V", "TAG", "", "smoothedNormX", "", "smoothedNormY", "lerp", "t", "a", "b", "map", "Lcom/google/mediapipe/examples/objectdetection/MusicParameterMapper$MusicParameters;", "normX", "normY", "smoothedVelocityPxS", "config", "Lcom/google/mediapipe/examples/objectdetection/MappingConfig;", "scale", "", "isActiveFrame", "", "mapPitch", "Lcom/google/mediapipe/examples/objectdetection/MusicParameterMapper$PitchResult;", "scaleNotes", "xAxisNotes", "glissando", "mapVolume", "velocityPxS", "velCfg", "Lcom/google/mediapipe/examples/objectdetection/VelocityMapping;", "resetSmoothedPosition", "", "MusicParameters", "PitchResult", "app_debug"})
public final class MusicParameterMapper {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "MusicParameterMapper";
    private static float smoothedNormX = 0.5F;
    private static float smoothedNormY = 0.5F;
    @org.jetbrains.annotations.NotNull()
    public static final com.google.mediapipe.examples.objectdetection.MusicParameterMapper INSTANCE = null;
    
    private MusicParameterMapper() {
        super();
    }
    
    /**
     * Reset axis smoothing. Call when tracking restarts.
     */
    public final void resetSmoothedPosition() {
    }
    
    /**
     * Map a frame's motion to musical parameters.
     *
     * @param normX         X normalised to [0, 1]
     * @param normY         Y normalised to [0, 1]
     * @param smoothedVelocityPxS  velocity in px/s after EMA
     * @param config        active mapping config (non-null)
     * @param isActiveFrame true when tracking is currently found (not lost)
     * @param scale         harmonic scale note list (must be non-empty)
     */
    @org.jetbrains.annotations.NotNull()
    public final com.google.mediapipe.examples.objectdetection.MusicParameterMapper.MusicParameters map(float normX, float normY, float smoothedVelocityPxS, @org.jetbrains.annotations.NotNull()
    com.google.mediapipe.examples.objectdetection.MappingConfig config, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> scale, boolean isActiveFrame) {
        return null;
    }
    
    private final com.google.mediapipe.examples.objectdetection.MusicParameterMapper.PitchResult mapPitch(float normX, java.util.List<java.lang.String> scaleNotes, java.util.List<java.lang.String> xAxisNotes, boolean glissando) {
        return null;
    }
    
    private final float mapVolume(float velocityPxS, boolean isActiveFrame, com.google.mediapipe.examples.objectdetection.VelocityMapping velCfg) {
        return 0.0F;
    }
    
    private final float lerp(float t, float a, float b) {
        return 0.0F;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n\u00a8\u0006\u001a"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/MusicParameterMapper$MusicParameters;", "", "noteIndex", "", "noteFractionalOffset", "", "volume", "filterHz", "(IFFF)V", "getFilterHz", "()F", "getNoteFractionalOffset", "getNoteIndex", "()I", "getVolume", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class MusicParameters {
        private final int noteIndex = 0;
        private final float noteFractionalOffset = 0.0F;
        private final float volume = 0.0F;
        private final float filterHz = 0.0F;
        
        public final int component1() {
            return 0;
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
        
        @org.jetbrains.annotations.NotNull()
        public final com.google.mediapipe.examples.objectdetection.MusicParameterMapper.MusicParameters copy(int noteIndex, float noteFractionalOffset, float volume, float filterHz) {
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
        
        public MusicParameters(int noteIndex, float noteFractionalOffset, float volume, float filterHz) {
            super();
        }
        
        public final int getNoteIndex() {
            return 0;
        }
        
        public final float getNoteFractionalOffset() {
            return 0.0F;
        }
        
        public final float getVolume() {
            return 0.0F;
        }
        
        public final float getFilterHz() {
            return 0.0F;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/MusicParameterMapper$PitchResult;", "", "index", "", "fractionalOffset", "", "(IF)V", "getFractionalOffset", "()F", "getIndex", "()I", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    static final class PitchResult {
        private final int index = 0;
        private final float fractionalOffset = 0.0F;
        
        public final int component1() {
            return 0;
        }
        
        public final float component2() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.google.mediapipe.examples.objectdetection.MusicParameterMapper.PitchResult copy(int index, float fractionalOffset) {
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
        
        public PitchResult(int index, float fractionalOffset) {
            super();
        }
        
        public final int getIndex() {
            return 0;
        }
        
        public final float getFractionalOffset() {
            return 0.0F;
        }
    }
}