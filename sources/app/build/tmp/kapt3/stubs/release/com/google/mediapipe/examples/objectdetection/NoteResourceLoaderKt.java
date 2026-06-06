package com.google.mediapipe.examples.objectdetection;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000 \n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u001a0\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0006\u0010\t\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"TAG", "", "loadNoteResourceMap", "", "", "context", "Landroid/content/Context;", "noteNames", "", "instrument", "app_release"})
public final class NoteResourceLoaderKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "NoteResourceLoader";
    
    /**
     * Discovers and maps note sample resource IDs from the `R.raw` auto-generated class.
     *
     * Naming convention for instrument samples:
     *  <instrument><root><repeats><octave>.ogg
     *  instrument: violin, flute, piano, etc.
     *  root  : c–g         (lowercase)
     *  repeat: s = sharp / f = flat (empty = natural)
     *  octave: digit 0–9
     *  Examples: violin_c4, flute_cs4, piano_df4, etc.
     *
     * The note-names list comes from `harmonicScaleToNoteNames()` (lowercase ASCII stems).
     *
     * @param context    Application context
     * @param noteNames  List of note names (e.g., ["c4", "d4", "e4"])
     * @param instrument Instrument prefix (e.g., "violin", "flute", "piano")
     * @return map of [note-index → R.raw resource id]; empty map if no matches found.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.Map<java.lang.Integer, java.lang.Integer> loadNoteResourceMap(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> noteNames, @org.jetbrains.annotations.NotNull()
    java.lang.String instrument) {
        return null;
    }
}