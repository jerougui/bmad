/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mediapipe.examples.objectdetection

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult
import kotlin.math.max
import kotlin.math.min

class OverlayView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private var results: ObjectDetectorResult? = null
    private var boxPaint = Paint()
    private var textBackgroundPaint = Paint()
    private var textPaint = Paint()
    private var scaleFactor: Float = 1f
    private var bounds = Rect()
    private var outputWidth = 0
    private var outputHeight = 0
    private var outputRotate = 0
    private var runningMode: RunningMode = RunningMode.IMAGE
    private var trackingSession: TrackingSession? = null

    // --- New state for object selection and tracking ---
    private var selectedDetectionIndex: Int? = null
    private var lastSelectedCenter: PointF? = null
    private var lastSelectedTimestampNanos: Long = 0
    private val trackingTextPaint = Paint().apply {
        color = Color.WHITE
        textSize = 48f
        style = Paint.Style.FILL
    }
    private val trackingBackgroundPaint = Paint().apply {
        color = Color.BLACK
        alpha = 180
        style = Paint.Style.FILL
    }
    private val stopButtonPaint = Paint().apply {
        style = Paint.Style.FILL
    }
    private val stopButtonTextPaint = Paint().apply {
        color = Color.WHITE
        textSize = 36f
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
    }
    private var isStopButtonPressed = false
    var onStopTracking: (() -> Unit)? = null
    // ---------------------------------------------------

    init {
        initPaints()
    }

    fun clear() {
        results = null
        selectedDetectionIndex = null
        lastSelectedCenter = null
        lastSelectedTimestampNanos = 0
        trackingSession = null
        textPaint.reset()
        textBackgroundPaint.reset()
        boxPaint.reset()
        invalidate()
        initPaints()
    }

    fun setRunningMode(runningMode: RunningMode) {
        this.runningMode = runningMode
    }

    fun setTrackingSession(session: TrackingSession?) {
        this.trackingSession = session
    }

    fun setSelectedDetectionIndex(index: Int?) {
        selectedDetectionIndex = index
    }

    fun getSelectedDetectionIndex(): Int? = selectedDetectionIndex

fun clearSelection() {
         selectedDetectionIndex = null
         lastSelectedCenter = null
         lastSelectedTimestampNanos = 0
     }

     /** Returns the center of the tracked object in screen coordinates, or null if not tracking. */
     fun getTrackedObjectCenter(): PointF? {
         if (trackingSession?.isActive != true) return null
         val session = trackingSession!!
         val detections = results?.detections() ?: return null
         val targetIdx = session.targetDetectionIndex ?: return null
         if (targetIdx >= detections.size) return null
         
         val detection = detections[targetIdx]
         val boxRect = RectF(
             detection.boundingBox().left,
             detection.boundingBox().top,
             detection.boundingBox().right,
             detection.boundingBox().bottom
         )
         
         val matrix = Matrix()
         matrix.postTranslate(-outputWidth / 2f, -outputHeight / 2f)
         matrix.postRotate(outputRotate.toFloat())
         if (outputRotate == 90 || outputRotate == 270) {
             matrix.postTranslate(outputHeight / 2f, outputWidth / 2f)
         } else {
             matrix.postTranslate(outputWidth / 2f, outputHeight / 2f)
         }
         matrix.mapRect(boxRect)
         
         val centerX = (boxRect.left + boxRect.right) / 2f * scaleFactor
         val centerY = (boxRect.top + boxRect.bottom) / 2f * scaleFactor
         return PointF(centerX, centerY)
     }

    private fun initPaints() {
        textBackgroundPaint.color = Color.BLACK
        textBackgroundPaint.style = Paint.Style.FILL
        textBackgroundPaint.textSize = 50f

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 50f

        boxPaint.color = ContextCompat.getColor(context!!, R.color.mp_primary)
        boxPaint.strokeWidth = 8F
        boxPaint.style = Paint.Style.STROKE

        stopButtonPaint.color = ContextCompat.getColor(context!!, R.color.mp_primary)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        val detections = results?.detections() ?: return
        if (detections.isEmpty()) return

        val indicesToDraw: List<Int> = if (trackingSession?.isActive == true) {
            listOfNotNull(trackingSession?.targetDetectionIndex)
        } else {
            detections.indices.toList()
        }

        for (index in indicesToDraw) {
            if (index >= detections.size) continue

            val detection = detections[index]
            val boxRect = RectF(
                detection.boundingBox().left,
                detection.boundingBox().top,
                detection.boundingBox().right,
                detection.boundingBox().bottom
            )
            val matrix = Matrix()
            matrix.postTranslate(-outputWidth / 2f, -outputHeight / 2f)
            matrix.postRotate(outputRotate.toFloat())

            if (outputRotate == 90 || outputRotate == 270) {
                matrix.postTranslate(outputHeight / 2f, outputWidth / 2f)
            } else {
                matrix.postTranslate(outputWidth / 2f, outputHeight / 2f)
            }
            matrix.mapRect(boxRect)

            val top = boxRect.top * scaleFactor
            val bottom = boxRect.bottom * scaleFactor
            val left = boxRect.left * scaleFactor
            val right = boxRect.right * scaleFactor

            val drawableRect = RectF(left, top, right, bottom)
            if (selectedDetectionIndex == index) {
                boxPaint.color = ContextCompat.getColor(context!!, R.color.mp_green_500)
                canvas.drawRect(drawableRect, boxPaint)
                boxPaint.color = ContextCompat.getColor(context!!, R.color.mp_primary)
            } else {
                canvas.drawRect(drawableRect, boxPaint)
            }

            val category = detection.categories()[0]
            val drawableText = "${category.categoryName()} ${String.format("%.2f", category.score())}"

            textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length, bounds)
            val textWidth = bounds.width()
            val textHeight = bounds.height()
            canvas.drawRect(
                left,
                top,
                left + textWidth + BOUNDING_RECT_TEXT_PADDING,
                top + textHeight + BOUNDING_RECT_TEXT_PADDING,
                textBackgroundPaint
            )

            canvas.drawText(drawableText, left, top + bounds.height(), textPaint)
        }

        if (trackingSession?.isActive == true) {
            val session = trackingSession!!
            var currentCenter: PointF? = null
            
            val trackingIdx = session.targetDetectionIndex
            if (trackingIdx != null && trackingIdx < detections.size && !session.isLost) {
                val detection = detections[trackingIdx]
                val boxRect = RectF(
                    detection.boundingBox().left,
                    detection.boundingBox().top,
                    detection.boundingBox().right,
                    detection.boundingBox().bottom
                )
                val matrix = Matrix()
                matrix.postTranslate(-outputWidth / 2f, -outputHeight / 2f)
                matrix.postRotate(outputRotate.toFloat())
                if (outputRotate == 90 || outputRotate == 270) {
                    matrix.postTranslate(outputHeight / 2f, outputWidth / 2f)
                } else {
                    matrix.postTranslate(outputWidth / 2f, outputHeight / 2f)
                }
                matrix.mapRect(boxRect)
                val centerX = (boxRect.left + boxRect.right) / 2
                val centerY = (boxRect.top + boxRect.bottom) / 2
                currentCenter = PointF(centerX * scaleFactor, centerY * scaleFactor)
            } else if (session.isLost && session.lastKnownPosition != null) {
                currentCenter = session.lastKnownPosition
            }

            currentCenter?.let { center ->
                val nowNanos = System.nanoTime()
                var velocityText = ""
                if (!session.isLost) {
                    lastSelectedCenter?.let { lastCenter ->
                        val dt = (nowNanos - lastSelectedTimestampNanos) / 1_000_000_000f
                        if (dt > 0) {
                            val vx = (center.x - lastCenter.x) / dt
                            val vy = (center.y - lastCenter.y) / dt
                            velocityText = "Velocity: %.1f %.1f px/s".format(vx, vy)
                        }
                    }
                } else {
                    velocityText = "Searching..."
                }
                lastSelectedCenter = center
                lastSelectedTimestampNanos = nowNanos

                val positionText = "Position: %.1f %.1f".format(center.x, center.y)
                val statusText = if (session.isLost) "Status: Lost - Searching for target" else ""

                val trackingMargin = 24f
                val trackingPadding = 16f
                val lines = listOf(positionText, velocityText, statusText).filter { it.isNotEmpty() }

                val fm = trackingTextPaint.fontMetrics
                val lineHeight = fm.bottom - fm.top
                val boxWidth = lines.maxOfOrNull { trackingTextPaint.measureText(it) } ?: 0f
                val boxHeight = lines.size * lineHeight + trackingPadding * 2

                canvas.drawRoundRect(
                    trackingMargin,
                    trackingMargin,
                    trackingMargin + boxWidth + trackingPadding * 2,
                    trackingMargin + boxHeight,
                    12f, 12f,
                    trackingBackgroundPaint
                )

                var y = trackingMargin + trackingPadding - fm.top
                lines.forEach { line ->
                    canvas.drawText(line, trackingMargin + trackingPadding, y, trackingTextPaint)
                    y += lineHeight
                }
            }

            val stopButtonWidth = 120f
            val stopButtonHeight = 70f
            val stopButtonMargin = 24f

            val canvasLeft = width - stopButtonWidth - stopButtonMargin
            val canvasTop = stopButtonMargin
            val rect = RectF(canvasLeft, canvasTop, canvasLeft + stopButtonWidth, canvasTop + stopButtonHeight)

            stopButtonPaint.color = if (isStopButtonPressed)
                ContextCompat.getColor(context!!, R.color.mp_variant)
            else
                ContextCompat.getColor(context!!, R.color.mp_primary)

            canvas.drawRoundRect(rect, 12f, 12f, stopButtonPaint)

            val iconSize = stopButtonHeight * 0.40f
            val iconLeft = canvasLeft + (stopButtonWidth - iconSize) / 2f
            val iconTop = canvasTop + (stopButtonHeight - iconSize) / 2f
            val iconRect = RectF(iconLeft, iconTop, iconLeft + iconSize, iconTop + iconSize)

            val iconPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.RED
                style = Paint.Style.FILL
            }

            canvas.drawRect(iconRect, iconPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (results == null || results?.detections()?.isEmpty() == true) {
            return super.onTouchEvent(event)
        }

        val detections = results?.detections()
        val action = event.actionMasked
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP) {
            val touchX = event.x
            val touchY = event.y

            val stopButtonWidth = 180f
            val stopButtonHeight = 50f
            val stopButtonMargin = 24f
            val stopButtonLeft = width - stopButtonWidth - stopButtonMargin
            val stopButtonTop = stopButtonMargin
            if (touchX >= stopButtonLeft && touchX <= stopButtonLeft + stopButtonWidth &&
                    touchY >= stopButtonTop && touchY <= stopButtonTop + stopButtonHeight) {
                if (action == MotionEvent.ACTION_DOWN) {
                    isStopButtonPressed = true
                    invalidate()
                    return true
                } else if (action == MotionEvent.ACTION_UP) {
                    isStopButtonPressed = false
                    selectedDetectionIndex = null
                    lastSelectedCenter = null
                    lastSelectedTimestampNanos = 0
                    onStopTracking?.invoke()
                    invalidate()
                    return true
                }
            }

            detections?.forEachIndexed { index, detection ->
                val boxRect = RectF(
                    detection.boundingBox().left,
                    detection.boundingBox().top,
                    detection.boundingBox().right,
                    detection.boundingBox().bottom
                )
                val matrix = Matrix()
                matrix.postTranslate(-outputWidth / 2f, -outputHeight / 2f)
                matrix.postRotate(outputRotate.toFloat())
                if (outputRotate == 90 || outputRotate == 270) {
                    matrix.postTranslate(outputHeight / 2f, outputWidth / 2f)
                } else {
                    matrix.postTranslate(outputWidth / 2f, outputHeight / 2f)
                }
                matrix.mapRect(boxRect)

                if (touchX >= boxRect.left && touchX <= boxRect.right &&
                        touchY >= boxRect.top && touchY <= boxRect.bottom) {
                    if (action == MotionEvent.ACTION_UP) {
                        selectedDetectionIndex = index
                        lastSelectedCenter = null
                        lastSelectedTimestampNanos = 0
                        invalidate()
                    }
                    return true
                }
            }

            if (action == MotionEvent.ACTION_UP) {
                selectedDetectionIndex = null
                lastSelectedCenter = null
                lastSelectedTimestampNanos = 0
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun setResults(
        detectionResults: ObjectDetectorResult,
        outputHeight: Int,
        outputWidth: Int,
        imageRotation: Int
    ) {
        results = detectionResults
        this.outputWidth = outputWidth
        this.outputHeight = outputHeight
        this.outputRotate = imageRotation

        val rotatedWidthHeight = when (imageRotation) {
            0, 180 -> Pair(outputWidth, outputHeight)
            90, 270 -> Pair(outputHeight, outputWidth)
            else -> return
        }

        scaleFactor = when (runningMode) {
            RunningMode.IMAGE,
            RunningMode.VIDEO -> {
                min(
                    width * 1f / rotatedWidthHeight.first,
                    height * 1f / rotatedWidthHeight.second
                )
            }

            RunningMode.LIVE_STREAM -> {
                max(
                    width * 1f / rotatedWidthHeight.first,
                    height * 1f / rotatedWidthHeight.second
                )
            }
        }

        invalidate()
    }

    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
    }
}