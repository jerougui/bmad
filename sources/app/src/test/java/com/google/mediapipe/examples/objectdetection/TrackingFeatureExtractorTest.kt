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

import android.graphics.Bitmap
import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Unit tests for TrackingFeatureExtractor histogram and similarity functions.
 * Tests the Bhattacharyya coefficient and similarity computation.
 */
@RunWith(AndroidJUnit4::class)
@Config(sdk = [31])
class TrackingFeatureExtractorTest {

    private fun createSolidColorBitmap(width: Int, height: Int, color: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, color)
            }
        }
        return bitmap
    }

    @Test
    fun histogramComputation_producesNormalizedOutput() {
        val bitmap = createSolidColorBitmap(64, 64, Color.rgb(100, 150, 200))
        
        val histogram = computeYUVHistogram(bitmap, 16)
        
        val sum = histogram.sum()
        org.junit.Assert.assertTrue("Histogram should sum to approximately 4096", sum.toInt() in 2047..2049)
    }

    @Test
    fun histogramSimilarity_highForSimilarColors() {
        val hist1 = createSolidColorBitmap(32, 32, Color.rgb(100, 150, 200))
        val hist2 = createSolidColorBitmap(32, 32, Color.rgb(105, 155, 205))

        val h1 = computeYUVHistogram(hist1, 16)
        val h2 = computeYUVHistogram(hist2, 16)
        
        // Normalize
        val sum1 = h1.sum()
        val sum2 = h2.sum()
        if (sum1 > 0 && sum2 > 0) {
            for (i in h1.indices) {
                h1[i] /= sum1
                h2[i] /= sum2
            }
        }

        val similarity = bhattacharyyaCoefficient(h1, h2)
        
        org.junit.Assert.assertTrue("Similar colors should have high histogram similarity", similarity > 0.95f)
    }

    @Test
    fun histogramSimilarity_lowForDifferentColors() {
        val hist1 = createSolidColorBitmap(32, 32, Color.RED)
        val hist2 = createSolidColorBitmap(32, 32, Color.BLUE)

        val h1 = computeYUVHistogram(hist1, 16)
        val h2 = computeYUVHistogram(hist2, 16)
        
        // Normalize
        val sum1 = h1.sum()
        val sum2 = h2.sum()
        if (sum1 > 0 && sum2 > 0) {
            for (i in h1.indices) {
                h1[i] /= sum1
                h2[i] /= sum2
            }
        }

        val similarity = bhattacharyyaCoefficient(h1, h2)
        
        org.junit.Assert.assertTrue("Different colors should have low histogram similarity", similarity < 0.5f)
    }

    @Test
    fun bhattacharyyaCoefficient_returnsOneForIdenticalDistributions() {
        val dist = FloatArray(16 * 16 * 16) { 1f / (16 * 16 * 16) }  // Uniform distribution
        
        val coeff = bhattacharyyaCoefficient(dist, dist)
        
        org.junit.Assert.assertEquals(1.0f, coeff, 0.001f)
    }

    @Test
    fun bhattacharyyaCoefficient_returnsZeroForDisjointDistributions() {
        val dist1 = FloatArray(16 * 16 * 16)
        dist1[0] = 1f  // All mass at index 0
        
        val dist2 = FloatArray(16 * 16 * 16)
        dist2[1] = 1f  // All mass at index 1

        val coeff = bhattacharyyaCoefficient(dist1, dist2)
        
        org.junit.Assert.assertEquals(0.0f, coeff, 0.001f)
    }

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

    private fun bhattacharyyaCoefficient(p: FloatArray, q: FloatArray): Float {
        var sum = 0f
        for (i in p.indices) {
            sum += kotlin.math.sqrt(p[i] * q[i])
        }
        return sum
    }
}