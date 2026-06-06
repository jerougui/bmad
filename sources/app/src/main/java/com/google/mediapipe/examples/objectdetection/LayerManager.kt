package com.google.mediapipe.examples.objectdetection

/**
 * Manages musical layers (pad, bass, percussion, lead) with fade in/out.
 */
class LayerManager {
    private var currentLayers = MotionMusicMapper.LayerConfig(false, false, false, false)
    private val fadeState = mutableMapOf<String, Float>()

    /**
     * Update layers with smooth transitions.
     */
    fun updateLayers(newLayers: MotionMusicMapper.LayerConfig) {
        // Fade out disabled layers
        if (currentLayers.pad && !newLayers.pad) fadeOut("pad")
        if (currentLayers.bass && !newLayers.bass) fadeOut("bass")
        if (currentLayers.percussion && !newLayers.percussion) fadeOut("percussion")
        if (currentLayers.lead && !newLayers.lead) fadeOut("lead")

        // Fade in enabled layers
        if (!currentLayers.pad && newLayers.pad) fadeIn("pad")
        if (!currentLayers.bass && newLayers.bass) fadeIn("bass")
        if (!currentLayers.percussion && newLayers.percussion) fadeIn("percussion")
        if (!currentLayers.lead && newLayers.lead) fadeIn("lead")

        currentLayers = newLayers
    }

    private fun fadeIn(layer: String) {
        fadeState[layer] = 0f
    }

    private fun fadeOut(layer: String) {
        fadeState[layer] = 1f
    }

    /**
     * Get current layer configuration.
     */
    fun getLayers(): MotionMusicMapper.LayerConfig = currentLayers

    /**
     * Get fade value for a layer (0=no layer, 1=full volume).
     */
    fun getFade(layer: String): Float = fadeState[layer] ?: 1f
}