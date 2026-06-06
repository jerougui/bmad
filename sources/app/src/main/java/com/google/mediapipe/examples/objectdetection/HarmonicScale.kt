package com.google.mediapipe.examples.objectdetection

import android.util.Log

private const val TAG = "HarmonicScale"

/**
 * Predefined harmonic scales, expressed as semitone offset lists
 * relative to the scale's root note.
 *
 * Root = 0 semitones is always the tonic of the scale.
 * Offsets are sorted ascending.
 *
 * Example: PENTATONIC_MAJOR (C) = [0, 2, 4, 7, 9] → C D E G A
 */
enum class HarmonicScale(val semitoneOffsets: List<Int>, val label: String) {
    CHROMATIC(
        listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
        "chromatic"
    ),
    MAJOR(
        listOf(0, 2, 4, 5, 7, 9, 11),
        "major"
    ),
    NATURAL_MINOR(
        listOf(0, 2, 3, 5, 7, 8, 10),
        "natural_minor"
    ),
    PENTATONIC_MAJOR(
        listOf(0, 2, 4, 7, 9),
        "pentatonic_major"
    ),
    PENTATONIC_MINOR(
        listOf(0, 3, 5, 7, 10),
        "pentatonic_minor"
    ),
    BLUES(
        listOf(0, 3, 5, 6, 7, 10),
        "blues"
    ),
    DORIAN(
        listOf(0, 2, 3, 5, 7, 9, 10),
        "dorian"
    ),
    MIXOLYDIAN(
        listOf(0, 2, 4, 5, 7, 9, 10),
        "mixolydian"
    );

    companion object {
        /** Look up a scale by its JSON string label. Falls back to pentatonic_major. */
        fun fromLabel(label: String): HarmonicScale =
            entries.find { it.label.equals(label, ignoreCase = true) }
                ?: PENTATONIC_MAJOR.also {
                    Log.w(TAG, "Unknown scale '$label', falling back to ${it.label}")
                }
    }
}

/**
 * Map a scientific pitch notation note name ("C4", "Bb3", "F#5") to a MIDI note number.
 * C4 = 60 (C4 = middle C).
 *
 * A4 = 440 Hz by convention.
 */
object PitchName {

    private val noteToSemitone = mapOf(
        "C" to 0, "C#" to 1, "DB" to 1,
        "D" to 2, "D#" to 3, "EB" to 3,
        "E" to 4,
        "F" to 5, "F#" to 6, "GB" to 6,
        "G" to 7, "G#" to 8, "AB" to 8,
        "A" to 9, "A#" to 10, "BB" to 10,
        "B" to 11,
    )

    /**
     * @return MIDI note number, or null if the name cannot be parsed.
     */
    fun parse(noteName: String): Int? {
        val trimmed = noteName.trim().replace("-", "").replace(" ", "")
        if (trimmed.isEmpty()) return null

        // Try known accidental first, then natural
        val notePart = trimmed.takeWhile { it.isLetter() || it == '#' || it == 'b' }
        val octavePart = trimmed.drop(notePart.length)

        val semitone = noteToSemitone[notePart.uppercase()] ?: return null
        val octave = octavePart.toIntOrNull() ?: return null
        return 12 * (octave + 1) + semitone
    }
}

/**
 * Build the list of absolute note names (e.g. "C4", "D4", "E4", "G4", "A4")
 * implied by [cfgScale] + [cfgRootNote].
 */
fun harmonicScaleToNoteNames(
    cfgScale: String?,
    cfgRootNote: String,
    noteNames: List<String>,
    customSemitones: List<Int>?,
): List<String> {
    // If customSemitones are provided, use them directly anchored at root
    if (customSemitones != null) {
        val rootMidi = PitchName.parse(cfgRootNote) ?: 60
        return customSemitones.map { offset ->
            midiToNoteName(rootMidi + offset)
        }
    }
    // If the JSON already carries explicit note names, trust them
    if (noteNames.isNotEmpty()) return noteNames
    // Otherwise derive from the scale definition
    val scale = cfgScale?.let { HarmonicScale.fromLabel(it) } ?: HarmonicScale.PENTATONIC_MAJOR
    val rootMidi = PitchName.parse(cfgRootNote) ?: 60
    return scale.semitoneOffsets.map { offset ->
        midiToNoteName(rootMidi + offset)
    }
}

/**
 * Return the absolute MIDI note numbers for a scale defined by [cfgScale] + [cfgRootNote].
 */
fun harmonicScaleToMidiNumbers(
    cfgScale: String?,
    cfgRootNote: String,
    customSemitones: List<Int>?,
): List<Int> {
    if (customSemitones != null) {
        val rootMidi = PitchName.parse(cfgRootNote) ?: 60
        return customSemitones.map { rootMidi + it }
    }
    val scale = cfgScale?.let { HarmonicScale.fromLabel(it) } ?: HarmonicScale.PENTATONIC_MAJOR
    val rootMidi = PitchName.parse(cfgRootNote) ?: 60
    return scale.semitoneOffsets.map { rootMidi + it }
}

private fun midiToNoteName(midi: Int): String {
    val octave = midi / 12 - 1
    val semitone = midi % 12
    val names = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
    return "${names[semitone]}$octave"
}
