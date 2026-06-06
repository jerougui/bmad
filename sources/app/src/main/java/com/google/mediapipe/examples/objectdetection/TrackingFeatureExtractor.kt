package com.google.mediapipe.examples.objectdetection

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.RectF
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Utility functions for object re-identification using lightweight features.
 * Features: color histogram (YUV 3D) + aspect ratio.
 */
object TrackingFeatureExtractor {

    private const val TAG = "TrackingFeatureExtractor"
    private const val HISTOGRAM_BINS = 16
    const val HISTOGRAM_SIZE = HISTOGRAM_BINS * HISTOGRAM_BINS * HISTOGRAM_BINS // 4096

    /**
     * Computes feature vector for a detection.
     * Crops the detected region from the frame bitmap and extracts:
     * - Normalized 3D YUV color histogram (4096 bins)
     * - Aspect ratio (width/height)
     *
     * @param frameBitmap Full camera frame bitmap (RGBA_8888)
     * @param detection Bounding box and metadata (MediaPipe Detection)
     * @return FloatArray of size [HISTOGRAM_SIZE + 1] where last element is aspect ratio
     */
    fun computeFeatures(frameBitmap: Bitmap, detection: Any): FloatArray {
        val box = run {
            val method = detection.javaClass.getMethod("boundingBox")
            val result = method.invoke(detection)
            result as RectF
        }
        
        val x = box.left.toInt().coerceAtLeast(0)
        val y = box.top.toInt().coerceAtLeast(0)
        val width = box.width().toInt().coerceAtMost(frameBitmap.width - x)
        val height = box.height().toInt().coerceAtMost(frameBitmap.height - y)
        
        if (width <= 0 || height <= 0) {
            return FloatArray(HISTOGRAM_SIZE + 1)
        }
        
        val croppedBitmap = Bitmap.createBitmap(frameBitmap, x, y, width, height)
        
        val histogram = computeYUVHistogram(croppedBitmap, HISTOGRAM_BINS)
        val total = histogram.sum().toFloat()
        if (total > 0) {
            for (i in histogram.indices) {
                histogram[i] /= total
            }
        }
        
        val aspectRatio = width.toFloat() / height.toFloat()
        
        return histogram + aspectRatio
    }

    /**
     * Extracts bounding box from detection using reflection.
     * Returns Rect for compatibility with existing code.
     */
    fun extractBoundingBox(detection: Any): Rect {
        val method = detection.javaClass.getMethod("boundingBox")
        val result = method.invoke(detection)
        val boxF = result as RectF
        return Rect(boxF.left.toInt(), boxF.top.toInt(), boxF.right.toInt(), boxF.bottom.toInt())
    }

    /**
     * Extracts class name from detection using reflection.
     */
    fun extractClassName(detection: Any): String {
        val categoriesMethod = detection.javaClass.getMethod("categories")
        @Suppress("UNCHECKED_CAST")
        val categories = categoriesMethod.invoke(detection) as List<Any>
        val firstCategory = categories[0]
        val nameMethod = firstCategory.javaClass.getMethod("categoryName")
        return nameMethod.invoke(firstCategory) as String
    }

    /**
     * Computes a 3D YUV histogram from a bitmap.
     * Y: [0-255], U: [0-255], V: [0-255] quantized into [HISTOGRAM_BINS] bins each.
     *
     * Bin index: y * BINS² + u * BINS + v
     *
     * @param bitmap Input RGB bitmap
     * @param bins Number of bins per channel (recommended 16)
     * @return Flat float array of length bins³
     */
    private fun computeYUVHistogram(bitmap: Bitmap, bins: Int): FloatArray {
        val histogram = FloatArray(bins * bins * bins)
        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        for (pixel in pixels) {
            val r = Color.red(pixel)
            val g = Color.green(pixel)
            val b = Color.blue(pixel)

            val y = (0.299 * r + 0.587 * g + 0.114 * b).toInt()
            val u = ((-0.169 * r - 0.331 * g + 0.5 * b) + 128).toInt()
            val v = ((0.5 * r - 0.419 * g - 0.081 * b) + 128).toInt()

            val yBin = (y * bins / 256).coerceIn(0, bins - 1)
            val uBin = (u * bins / 256).coerceIn(0, bins - 1)
            val vBin = (v * bins / 256).coerceIn(0, bins - 1)

            val index = yBin * bins * bins + uBin * bins + vBin
            histogram[index]++
        }

        return histogram
    }

    /**
     * Computes similarity score between a target feature vector and a candidate detection.
     *
     * Weighted combination:
     * - Class match: 0.5 (binary 1 if same class, else 0)
     * - Histogram similarity (Bhattacharyya coefficient): 0.3
     * - Aspect ratio difference (normalized): 0.2
     *
     * @param targetFeatures Target feature vector (from selected object)
     * @param candidateFeatures Candidate detection feature vector
     * @param targetClass Target class label (for exact class match check)
     * @param candidateClass Candidate class label
     * @return Similarity score in [0, 1]. Higher means more similar.
     */
    fun computeSimilarity(
        targetFeatures: FloatArray,
        candidateFeatures: FloatArray,
        targetClass: String,
        candidateClass: String
    ): Float {
        require(targetFeatures.size == HISTOGRAM_SIZE + 1) { "Target features size mismatch" }
        require(candidateFeatures.size == HISTOGRAM_SIZE + 1) { "Candidate features size mismatch" }

        val classScore = if (targetClass.equals(candidateClass, ignoreCase = true)) 0.5f else 0f

        val targetHist = targetFeatures.sliceArray(0 until HISTOGRAM_SIZE)
        val candidateHist = candidateFeatures.sliceArray(0 until HISTOGRAM_SIZE)
        val targetAspect = targetFeatures[HISTOGRAM_SIZE]
        val candidateAspect = candidateFeatures[HISTOGRAM_SIZE]

        val histScore = bhattacharyyaCoefficient(targetHist, candidateHist) * 0.3f

        val aspectDiff = abs(targetAspect - candidateAspect) / kotlin.math.max(targetAspect, candidateAspect)
        val aspectScore = (1.0f - aspectDiff.coerceAtMost(1.0f)) * 0.2f

        return classScore + histScore + aspectScore
    }

    /**
     * Computes the Bhattacharyya coefficient between two probability distributions.
     * ρ(p,q) = Σ √(p_i * q_i)
     *
     * @param p First distribution (already normalized, sum to 1)
     * @param q Second distribution (already normalized, sum to 1)
     * @return Coefficient in [0, 1]
     */
    private fun bhattacharyyaCoefficient(p: FloatArray, q: FloatArray): Float {
        var sum = 0f
        for (i in p.indices) {
            sum += sqrt(p[i] * q[i])
        }
        return sum
    }
}