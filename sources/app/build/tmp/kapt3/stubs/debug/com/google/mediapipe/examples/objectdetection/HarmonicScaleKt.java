package com.google.mediapipe.examples.objectdetection;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\b\u001a.\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0006\u001a\u00020\u00012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u001a<\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0006\u001a\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u001a\u0010\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0004H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"TAG", "", "harmonicScaleToMidiNumbers", "", "", "cfgScale", "cfgRootNote", "customSemitones", "harmonicScaleToNoteNames", "noteNames", "midiToNoteName", "midi", "app_debug"})
public final class HarmonicScaleKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "HarmonicScale";
    
    /**
     * Build the list of absolute note names (e.g. "C4", "D4", "E4", "G4", "A4")
     * implied by [cfgScale] + [cfgRootNote].
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<java.lang.String> harmonicScaleToNoteNames(@org.jetbrains.annotations.Nullable()
    java.lang.String cfgScale, @org.jetbrains.annotations.NotNull()
    java.lang.String cfgRootNote, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> noteNames, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Integer> customSemitones) {
        return null;
    }
    
    /**
     * Return the absolute MIDI note numbers for a scale defined by [cfgScale] + [cfgRootNote].
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<java.lang.Integer> harmonicScaleToMidiNumbers(@org.jetbrains.annotations.Nullable()
    java.lang.String cfgScale, @org.jetbrains.annotations.NotNull()
    java.lang.String cfgRootNote, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Integer> customSemitones) {
        return null;
    }
    
    private static final java.lang.String midiToNoteName(int midi) {
        return null;
    }
}