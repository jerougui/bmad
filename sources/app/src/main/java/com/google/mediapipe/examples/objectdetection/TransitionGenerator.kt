package com.google.mediapipe.examples.objectdetection

/**
 * Generates musical transition effects (riser, drop, impact).
 */
object TransitionGenerator {

    /**
     * Generate riser samples - upward frequency sweep.
     */
    fun generateRiser(durationMs: Int = 1500, startFreq: Float = 100f, endFreq: Float = 800f): ShortArray {
        val samples = ShortArray((durationMs * 44.1f / 1000 * 2).toInt()) // stéreo
        val numSamples = samples.size / 2

        for (i in 0 until numSamples step 2) {
            val progress = i.toFloat() / numSamples
            val freq = startFreq + (endFreq - startFreq) * progress
            val value = SynthEngine.generateSine(freq, 0.3f + progress * 0.4f)

            if (i < value.size) {
                samples[i] = value[i]
                samples[i + 1] = value[i] // stéreo identique
            }
        }
        return samples
    }

    /**
     * Generate impact noise burst.
     */
    fun generateImpact(durationMs: Int = 50): ShortArray {
        val samples = ShortArray((durationMs * 44.1f / 1000 * 2).toInt())
        for (i in samples.indices step 2) {
            if (i < samples.size) {
                val amp = (1 - (i.toFloat() / samples.size)) * 0.8f
                val val1 = (Math.random().toFloat() * 2 - 1) * amp
                val val2 = (Math.random().toFloat() * 2 - 1) * amp
                samples[i] = (val1 * Short.MAX_VALUE).toInt().toShort()
                samples[i + 1] = (val2 * Short.MAX_VALUE).toInt().toShort()
            }
        }
        return samples
    }

    /**
     * Check if riser should be triggered.
     */
    fun shouldTriggerRiser(acceleration: Float, avgSpeed: Float, threshold: Float = 2f): Boolean {
        return acceleration > avgSpeed * threshold
    }
}