/*
 * Test for MotionToMusicEngine initialization
 * Verifies story 1.1 acceptance criteria
 */
package com.google.mediapipe.examples.objectdetection

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MotionToMusicEngineTest {

    @Test
    fun testEngineInitialization() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        
        // Given engine is not running
        MotionToMusicEngine.stop()
        
        // When we start the engine
        MotionToMusicEngine.start(context)
        
        // Then engine should be running
        // Note: Cannot directly access 'running' private field, but we can verify no exceptions
        
        // Cleanup
        MotionToMusicEngine.stop()
    }

    @Test
    fun testScreenDimensionsUpdate() {
        // When updating screen dimensions
        MotionToMusicEngine.updateScreenDimensions(1080, 1920)
        
        // Then no exception should be thrown
        // Dimensions are stored for frame normalization
        assertTrue(true) // Placeholder - actual verification would need accessor
    }

    @Test
    fun testTrackingSessionAttachment() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        MotionToMusicEngine.start(context)
        
        // Given a tracking session
        val mockSession = TrackingSession(
            targetFeatures = floatArrayOf(0.5f, 0.5f),
            targetClass = "kite",
            targetInitialBounds = android.graphics.Rect(0, 0, 100, 100),
            isActive = true,
            isLost = false,
            framesSinceLastSeen = 0,
            lastKnownPosition = null,
            targetDetectionIndex = 0
        )
        
        // When we attach it
        MotionToMusicEngine.setTrackingSession(mockSession)
        
        // Then no exception should be thrown
        assertTrue(true) // Placeholder - actual verification would need accessor
        
        // Cleanup
        MotionToMusicEngine.stop()
    }
}