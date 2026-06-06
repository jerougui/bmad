/*
 * Test for TrackingSession state management
 * Verifies story 1.2 acceptance criteria
 */
package com.google.mediapipe.examples.objectdetection

import android.graphics.PointF
import android.graphics.Rect
import org.junit.Assert.*
import org.junit.Test

class TrackingSessionTest {

    @Test
    fun testMarkFrameProcessedDetected() {
        // Given a tracking session
        val session = TrackingSession(
            targetFeatures = floatArrayOf(0.5f, 0.5f),
            targetClass = "kite",
            targetInitialBounds = Rect(0, 0, 100, 100),
            isActive = true,
            isLost = false,
            framesSinceLastSeen = 0,
            lastKnownPosition = null,
            targetDetectionIndex = 0
        )
        
        // When frame is processed with detection
        session.markFrameProcessed(wasDetected = true)
        
        // Then state should remain active and not lost
        assertFalse(session.isLost)
        assertEquals(0, session.framesSinceLastSeen)
    }

    @Test
    fun testMarkFrameProcessedLost() {
        // Given a tracking session
        val session = TrackingSession(
            targetFeatures = floatArrayOf(0.5f, 0.5f),
            targetClass = "kite",
            targetInitialBounds = Rect(0, 0, 100, 100),
            isActive = true,
            isLost = false,
            framesSinceLastSeen = 0,
            lastKnownPosition = null,
            targetDetectionIndex = 0
        )
        
        // When 3 consecutive frames without detection
        session.markFrameProcessed(wasDetected = false)
        assertEquals(1, session.framesSinceLastSeen)
        assertFalse(session.isLost)
        
        session.markFrameProcessed(wasDetected = false)
        assertEquals(2, session.framesSinceLastSeen)
        assertFalse(session.isLost)
        
        session.markFrameProcessed(wasDetected = false)
        
        // Then isLost should be true
        assertTrue(session.isLost)
        assertEquals(3, session.framesSinceLastSeen)
    }

    @Test
    fun testOnReacquired() {
        // Given a lost session
        val session = TrackingSession(
            targetFeatures = floatArrayOf(0.5f, 0.5f),
            targetClass = "kite",
            targetInitialBounds = Rect(0, 0, 100, 100),
            isActive = true,
            isLost = true,
            framesSinceLastSeen = 5,
            lastKnownPosition = null,
            targetDetectionIndex = 0
        )
        
        // When re-acquired
        val newPosition = PointF(500f, 300f)
        session.onReacquired(newPosition)
        
        // Then state should be reset
        assertFalse(session.isLost)
        assertEquals(0, session.framesSinceLastSeen)
        assertEquals(newPosition, session.lastKnownPosition)
    }

    @Test
    fun testReset() {
        // Given an active session
        val session = TrackingSession(
            targetFeatures = floatArrayOf(0.5f, 0.5f),
            targetClass = "kite",
            targetInitialBounds = Rect(0, 0, 100, 100),
            isActive = true,
            isLost = false,
            framesSinceLastSeen = 3,
            lastKnownPosition = PointF(100f, 100f),
            targetDetectionIndex = 1
        )
        
        // When reset
        session.reset()
        
        // Then all state should be cleared
        assertFalse(session.isActive)
        assertFalse(session.isLost)
        assertEquals(0, session.framesSinceLastSeen)
        assertNull(session.lastKnownPosition)
        assertNull(session.targetDetectionIndex)
    }
}