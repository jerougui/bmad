package com.google.mediapipe.examples.objectdetection;

/**
 * Predefined harmonic scales, expressed as semitone offset lists
 * relative to the scale's root note.
 *
 * Root = 0 semitones is always the tonic of the scale.
 * Offsets are sorted ascending.
 *
 * Example: PENTATONIC_MAJOR (C) = [0, 2, 4, 7, 9] → C D E G A
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\b\u0086\u0081\u0002\u0018\u0000 \u00142\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0014B\u001d\b\u0002\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013\u00a8\u0006\u0015"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/HarmonicScale;", "", "semitoneOffsets", "", "", "label", "", "(Ljava/lang/String;ILjava/util/List;Ljava/lang/String;)V", "getLabel", "()Ljava/lang/String;", "getSemitoneOffsets", "()Ljava/util/List;", "CHROMATIC", "MAJOR", "NATURAL_MINOR", "PENTATONIC_MAJOR", "PENTATONIC_MINOR", "BLUES", "DORIAN", "MIXOLYDIAN", "Companion", "app_debug"})
public enum HarmonicScale {
    /*public static final*/ CHROMATIC /* = new CHROMATIC(null, null) */,
    /*public static final*/ MAJOR /* = new MAJOR(null, null) */,
    /*public static final*/ NATURAL_MINOR /* = new NATURAL_MINOR(null, null) */,
    /*public static final*/ PENTATONIC_MAJOR /* = new PENTATONIC_MAJOR(null, null) */,
    /*public static final*/ PENTATONIC_MINOR /* = new PENTATONIC_MINOR(null, null) */,
    /*public static final*/ BLUES /* = new BLUES(null, null) */,
    /*public static final*/ DORIAN /* = new DORIAN(null, null) */,
    /*public static final*/ MIXOLYDIAN /* = new MIXOLYDIAN(null, null) */;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> semitoneOffsets = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String label = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.google.mediapipe.examples.objectdetection.HarmonicScale.Companion Companion = null;
    
    HarmonicScale(java.util.List<java.lang.Integer> semitoneOffsets, java.lang.String label) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getSemitoneOffsets() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.google.mediapipe.examples.objectdetection.HarmonicScale> getEntries() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/HarmonicScale$Companion;", "", "()V", "fromLabel", "Lcom/google/mediapipe/examples/objectdetection/HarmonicScale;", "label", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Look up a scale by its JSON string label. Falls back to pentatonic_major.
         */
        @org.jetbrains.annotations.NotNull()
        public final com.google.mediapipe.examples.objectdetection.HarmonicScale fromLabel(@org.jetbrains.annotations.NotNull()
        java.lang.String label) {
            return null;
        }
    }
}