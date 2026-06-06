package com.google.mediapipe.examples.objectdetection

import android.graphics.PointF
import android.util.Log
import kotlin.math.sqrt

/**
 * Extracts and smooths motion parameters from per-frame camera data.
 *
 * The extractor owns its own smoothing state, so it is safe to call
 * [extractParameters] exactly once per 30-fps camera frame.
 *
 * Smoothing factors for velocity and acceleration are baked in here.
 * Position-to-pitch and position-to-timbre smoothing instead live in
 * [MusicParameterMapper], driven by MappingConfig values.
 */
object MotionParameterExtractor {

    private const val TAG = "MotionParameterExtractor"

    /** EMA factor for velocity smoothing (larger = snappier). */
    private const val VEL_ALPHA = 0.35f

    /** EMA factor for acceleration smoothing. */
    private const val ACC_ALPHA = 0.25f

    /** Result of a single [extractParameters] call. */
    data class MotionFrame(
        val normalizedX: Float,
        val normalizedY: Float,
        val rawVelocity: Float,
        val smoothedVelocity: Float,
        val rawAcceleration: Float,
        val smoothedAcceleration: Float,
        val dtSeconds: Float,
    )

    // --- Persistent smoothing state ---
    @Volatile private var lastCenter: PointF? = null
    @Volatile private var lastTimestampNanos: Long = 0
    @Volatile private var lastVelocityPxS: Float = 0f
    @Volatile private var emaVelocity: Float = 0f
    @Volatile private var emaAcceleration: Float = 0f

    /** Reset all smoothing — call when a new tracking session starts. */
    @Synchronized
    fun reset() {
        lastCenter       = null
        lastTimestampNanos = 0
        lastVelocityPxS  = 0f
        emaVelocity      = 0f
        emaAcceleration  = 0f
    }

    /**
     * Extract one frame's worth of motion data.
     *
     * @param centerPx     Tracked object centre in screen px coordinates.
     * @param screenWidthPx  View width in px.
     * @param screenHeightPx View height in px.
     * @param timestampMs  Frame wall-clock time in milliseconds.
     */
    @Synchronized
    fun extractParameters(
        centerPx: PointF,
        screenWidthPx: Int,
        screenHeightPx: Int,
        timestampMs: Long,
    ): MotionFrame {
        val nowNanos = timestampMs * 1_000_000L
        val dt = if (lastTimestampNanos > 0) {
            ((nowNanos - lastTimestampNanos) / 1_000_000_000f).coerceAtLeast(1e-4f)
        } else {
            1f / 30f
        }
        lastTimestampNanos = nowNanos

        val normX = (centerPx.x / screenWidthPx.toFloat()).coerceIn(0f, 1f)
        val normY = (centerPx.y / screenHeightPx.toFloat()).coerceIn(0f, 1f)

        val rawVel = velocityFromCentres(centerPx, dt)
        val rawAcc = accelerationFromVelocity(rawVel, dt)

        emaVelocity     = VEL_ALPHA * rawVel    + (1 - VEL_ALPHA) * emaVelocity
        emaAcceleration = ACC_ALPHA * rawAcc   + (1 - ACC_ALPHA) * emaAcceleration

        Log.v(TAG, "Frame dt=${dt}s rawVel=${rawVel} emaVel=${emaVelocity}")

        lastCenter      = PointF(centerPx.x, centerPx.y)
        lastVelocityPxS = rawVel

        return MotionFrame(
            normalizedX          = normX,
            normalizedY          = normY,
            rawVelocity          = rawVel,
            smoothedVelocity     = emaVelocity,
            rawAcceleration      = rawAcc,
            smoothedAcceleration = emaAcceleration,
            dtSeconds            = dt,
        )
    }

    private fun velocityFromCentres(current: PointF, dt: Float): Float {
        val prev = lastCenter ?: return 0f
        val dx = current.x - prev.x
        val dy = current.y - prev.y
        return sqrt(dx * dx + dy * dy) / dt
    }

    private fun accelerationFromVelocity(currentVel: Float, dt: Float): Float {
        if (dt < 1e-4f) return 0f
        return (currentVel - lastVelocityPxS) / dt
    }
}
