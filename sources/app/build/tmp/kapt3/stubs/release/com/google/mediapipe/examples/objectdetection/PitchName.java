package com.google.mediapipe.examples.objectdetection;

/**
 * Map a scientific pitch notation note name ("C4", "Bb3", "F#5") to a MIDI note number.
 * C4 = 60 (C4 = middle C).
 *
 * A4 = 440 Hz by convention.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\tR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/PitchName;", "", "()V", "noteToSemitone", "", "", "", "parse", "noteName", "(Ljava/lang/String;)Ljava/lang/Integer;", "app_release"})
public final class PitchName {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, java.lang.Integer> noteToSemitone = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.google.mediapipe.examples.objectdetection.PitchName INSTANCE = null;
    
    private PitchName() {
        super();
    }
    
    /**
     * @return MIDI note number, or null if the name cannot be parsed.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer parse(@org.jetbrains.annotations.NotNull()
    java.lang.String noteName) {
        return null;
    }
}