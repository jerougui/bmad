package com.google.mediapipe.examples.objectdetection

import android.content.Context
import android.util.Log

private const val TAG = "NoteResourceLoader"

/**
 * Discovers and maps note sample resource IDs from the `R.raw` auto-generated class.
 *
 * Naming convention for instrument samples:
 *   <instrument><root><repeats><octave>.ogg
 *   instrument: violin, flute, piano, etc.
 *   root  : c–g         (lowercase)
 *   repeat: s = sharp / f = flat (empty = natural)
 *   octave: digit 0–9
 *   Examples: violin_c4, flute_cs4, piano_df4, etc.
 *
 * The note-names list comes from `harmonicScaleToNoteNames()` (lowercase ASCII stems).
 *
 * @param context    Application context
 * @param noteNames  List of note names (e.g., ["c4", "d4", "e4"])
 * @param instrument Instrument prefix (e.g., "violin", "flute", "piano")
 * @return map of [note-index → R.raw resource id]; empty map if no matches found.
 */
fun loadNoteResourceMap(context: Context, noteNames: List<String>, instrument: String): Map<Int, Int> {
    try {
        // R$raw is the static inner class Android generates for all res/raw/ files.
        // Field name == file stem (without extension), e.g. "violin_c4" for res/raw/violin_c4.ogg
        val rawClass = Class.forName(
            "com.google.mediapipe.examples.objectdetection.R\$raw"
        )
        val fieldMap = rawClass.fields
            .filter { it.type == Int::class.javaPrimitiveType }
            .associate { it.name to it.getInt(null) }

return noteNames.mapIndexedNotNull { idx, noteName ->
             val stem = "${instrument}_${noteName.trim().lowercase()}"
                 .replace("-", "").replace(" ", "")
             fieldMap[stem]?.let { idx to it }
         }.toMap()
    } catch (e: Exception) {
        Log.w(TAG, "Could not scan R.raw via reflection: ${e.message}")
        return emptyMap()
    }
}
