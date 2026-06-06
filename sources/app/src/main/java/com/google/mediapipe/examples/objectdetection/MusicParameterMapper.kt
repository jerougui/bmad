package com.google.mediapipe.examples.objectdetection

/**
 * Maps raw motion parameters to musical output parameters
 * as defined by the loaded [MappingConfig].
 *
 * All output values are clamped so callers never need to double-check.
 */
object MusicParameterMapper {

    private const val TAG = "MusicParameterMapper"

    // Axis smoothing state
    private var smoothedNormX: Float = 0.5f
    private var smoothedNormY: Float = 0.5f

    /** Reset axis smoothing. Call when tracking restarts. */
    fun resetSmoothedPosition() {
        smoothedNormX = 0.5f
        smoothedNormY = 0.5f
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
    fun map(
        normX: Float,
        normY: Float,
        smoothedVelocityPxS: Float,
        config: MappingConfig,
        scale: List<String>,
        isActiveFrame: Boolean,
    ): MusicParameters {

        val xCfg   = config.mapping.xAxis
        val yCfg   = config.mapping.yAxis
        val velCfg = config.mapping.velocity

        // --- Smooth positional axes ---
        val alphaX = xCfg.smoothing.toFloat().coerceIn(0.01f, 1f)
        val alphaY = yCfg.smoothing.toFloat().coerceIn(0.01f, 1f)
        smoothedNormX = alphaX * normX + (1 - alphaX) * smoothedNormX
        smoothedNormY = alphaY * normY + (1 - alphaY) * smoothedNormY

        // --- X → pitch ---
        val pitchResult = mapPitch(
            smoothedNormX, scale, xCfg.notes ?: emptyList(), xCfg.glissando
        )

        // --- Y → timbre (filter Hz) ---
        val filterHz = if (yCfg.filterRange != null && yCfg.filterRange.size >= 2) {
            lerp(smoothedNormY, yCfg.filterRange[0].toFloat(), yCfg.filterRange[1].toFloat())
        } else 200f

        // --- Velocity → volume ---
        val volume = mapVolume(smoothedVelocityPxS, isActiveFrame, velCfg)

        return MusicParameters(
            noteIndex             = pitchResult.index,
            noteFractionalOffset  = pitchResult.fractionalOffset,
            volume                = volume,
            filterHz              = filterHz,
        )
    }

    private fun mapPitch(
        normX: Float,
        scaleNotes: List<String>,
        xAxisNotes: List<String>,
        glissando: Boolean,
    ): PitchResult {
        val noteCount = scaleNotes.size
        if (noteCount == 0) return PitchResult(0, 0f)

        return if (glissando) {
            val f    = normX * (noteCount - 1)
            val i0   = f.toInt().coerceIn(0, noteCount - 2)
            PitchResult(i0, f - i0)
        } else {
            val i = (normX * noteCount).toInt().coerceIn(0, noteCount - 1)
            PitchResult(i, 0f)
        }
    }

    private fun mapVolume(
        velocityPxS: Float,
        isActiveFrame: Boolean,
        velCfg:VelocityMapping,
    ): Float {
        if (!isActiveFrame) return 0f
        val threshold = velCfg.threshold.toFloat()
        val maxVel    = (threshold * 3).coerceAtLeast(threshold + 50f)
        if (velocityPxS <= threshold) return 0f
        val raw = ((velocityPxS - threshold) / (maxVel - threshold))
            .coerceIn(0f, 1f)
        // lerp from velConfig.min to velConfig.max
        return lerp(raw, velCfg.min.toFloat(), velCfg.max.toFloat())
    }

    private fun lerp(t: Float, a: Float, b: Float): Float = a + t * (b - a)

    data class MusicParameters(
        val noteIndex: Int,
        val noteFractionalOffset: Float,
        val volume: Float,
        val filterHz: Float,
    )

    private data class PitchResult(val index: Int, val fractionalOffset: Float)
}
