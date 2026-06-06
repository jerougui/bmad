package com.google.mediapipe.examples.objectdetection

/**
 * Maps motion parameters to musical synthesis parameters.
 * Enhanced for synthwave-style sounds.
 */
object MotionMusicMapper {

    // Intensity thresholds
    const val SPEED_LOW = 0.2f    // Slow < 0.2: pad atmosphere
    const val SPEED_MEDIUM = 0.6f // Medium 0.2-0.6: add bass
    const val SPEED_HIGH = 0.8f   // Fast > 0.8: add percussion/lead

    // Direction change detection
    const val DIRECTION_CHANGE_THRESHOLD = 45f // degrees

    /**
     * Get synthesis parameters based on speed.
     * Returns parameters optimized for synthwave sound.
     */
    fun getParamsForSpeed(speed: Float): SynthParams {
        return when {
            speed < SPEED_LOW -> SynthParams(
                layer = LayerConfig(pad = true, bass = false, percussion = false, lead = false),
                cutoff = 1200f,
                resonance = 0.5f,
                lfoRate = 2.2f,
                lfoDepth = 0.2f,
                waveform = SynthEngine.Waveform.SUPERS,
                subMix = 0.3f,
                attack = 0.01f,
                release = 1.2f
            )
            speed < SPEED_MEDIUM -> SynthParams(
                layer = LayerConfig(pad = true, bass = true, percussion = false, lead = false),
                cutoff = 1800f,
                resonance = 0.6f,
                lfoRate = 3.5f,
                lfoDepth = 0.25f,
                waveform = SynthEngine.Waveform.SUPERSAW,
                subMix = 0.4f,
                attack = 0.005f,
                release = 0.8f
            )
            else -> SynthParams(
                layer = LayerConfig(pad = true, bass = true, percussion = true, lead = true),
                cutoff = 2200f,
                resonance = 0.7f,
                lfoRate = 5.5f,
                lfoDepth = 0.3f,
                waveform = SynthEngine.Waveform.SUPERSAW,
                subMix = 0.5f,
                attack = 0.001f,
                release = 0.3f
            )
        }
    }

    /**
     * Detect direction change in degrees.
     */
    fun detectDirectionChange(oldAngle: Float, newAngle: Float): Boolean {
        val diff = Math.abs(newAngle - oldAngle)
        val normalizedDiff = Math.min(diff, 360 - diff)
        return normalizedDiff > DIRECTION_CHANGE_THRESHOLD
    }

    /**
     * Detect acceleration spike (2x threshold).
     */
    fun detectAccelerationSpike(currentSpeed: Float, avgSpeed: Float, threshold: Float = 2f): Boolean {
        return currentSpeed > avgSpeed * threshold
    }

    data class SynthParams(
        val layer: LayerConfig,
        val cutoff: Float,
        val resonance: Float,
        val lfoRate: Float = 4.0f,
        val lfoDepth: Float = 0.2f,
        val waveform: SynthEngine.Waveform = SynthEngine.Waveform.SUPERSAW,
        val subMix: Float = 0.4f,
        val attack: Float = 0.01f,
        val release: Float = 0.5f
    )

    data class LayerConfig(
        val pad: Boolean,
        val bass: Boolean,
        val percussion: Boolean,
        val lead: Boolean
    )
}