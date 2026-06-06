package com.google.mediapipe.examples.objectdetection;

/**
 * Utility functions for object re-identification using lightweight features.
 * Features: color histogram (YUV 3D) + aspect ratio.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0002J\u0016\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0001J&\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0007J\u0018\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00020\u0001J\u000e\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/TrackingFeatureExtractor;", "", "()V", "HISTOGRAM_BINS", "", "HISTOGRAM_SIZE", "TAG", "", "bhattacharyyaCoefficient", "", "p", "", "q", "computeFeatures", "frameBitmap", "Landroid/graphics/Bitmap;", "detection", "computeSimilarity", "targetFeatures", "candidateFeatures", "targetClass", "candidateClass", "computeYUVHistogram", "bitmap", "bins", "extractBoundingBox", "Landroid/graphics/Rect;", "extractClassName", "app_debug"})
public final class TrackingFeatureExtractor {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "TrackingFeatureExtractor";
    private static final int HISTOGRAM_BINS = 16;
    public static final int HISTOGRAM_SIZE = 4096;
    @org.jetbrains.annotations.NotNull()
    public static final com.google.mediapipe.examples.objectdetection.TrackingFeatureExtractor INSTANCE = null;
    
    private TrackingFeatureExtractor() {
        super();
    }
    
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
    @org.jetbrains.annotations.NotNull()
    public final float[] computeFeatures(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap frameBitmap, @org.jetbrains.annotations.NotNull()
    java.lang.Object detection) {
        return null;
    }
    
    /**
     * Extracts bounding box from detection using reflection.
     * Returns Rect for compatibility with existing code.
     */
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Rect extractBoundingBox(@org.jetbrains.annotations.NotNull()
    java.lang.Object detection) {
        return null;
    }
    
    /**
     * Extracts class name from detection using reflection.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String extractClassName(@org.jetbrains.annotations.NotNull()
    java.lang.Object detection) {
        return null;
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
    private final float[] computeYUVHistogram(android.graphics.Bitmap bitmap, int bins) {
        return null;
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
    public final float computeSimilarity(@org.jetbrains.annotations.NotNull()
    float[] targetFeatures, @org.jetbrains.annotations.NotNull()
    float[] candidateFeatures, @org.jetbrains.annotations.NotNull()
    java.lang.String targetClass, @org.jetbrains.annotations.NotNull()
    java.lang.String candidateClass) {
        return 0.0F;
    }
    
    /**
     * Computes the Bhattacharyya coefficient between two probability distributions.
     * ρ(p,q) = Σ √(p_i * q_i)
     *
     * @param p First distribution (already normalized, sum to 1)
     * @param q Second distribution (already normalized, sum to 1)
     * @return Coefficient in [0, 1]
     */
    private final float bhattacharyyaCoefficient(float[] p, float[] q) {
        return 0.0F;
    }
}