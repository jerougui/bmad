package com.google.mediapipe.examples.objectdetection;

/**
 * Procedural synthesis engine for generating waveforms in real-time.
 * Uses AudioTrack in streaming mode for low-latency output.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0017\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u0013J\u001e\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0013J\b\u0010\u0016\u001a\u00020\u000bH\u0002J\u000e\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u0013J \u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00132\b\b\u0002\u0010\u001b\u001a\u00020\u0013J\u0016\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0013J\u0016\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0013J\u0006\u0010\u001e\u001a\u00020\u001fJ\u0006\u0010 \u001a\u00020\u001fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/SynthEngine;", "", "()V", "AUDIO_FORMAT", "", "BUFFER_SIZE", "CHANNELS", "SAMPLE_RATE", "audioTrack", "Landroid/media/AudioTrack;", "outputBuffer", "", "running", "", "thread", "Ljava/lang/Thread;", "applyLowPass", "samples", "cutoffHz", "", "applyResonance", "resonance", "generateFrame", "generateNoise", "amplitude", "generateSine", "frequency", "phase", "generateSquare", "generateTriangle", "start", "", "stop", "app_debug"})
public final class SynthEngine {
    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = 512;
    private static final int CHANNELS = android.media.AudioFormat.CHANNEL_OUT_STEREO;
    private static final int AUDIO_FORMAT = android.media.AudioFormat.ENCODING_PCM_16BIT;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile android.media.AudioTrack audioTrack;
    @kotlin.jvm.Volatile()
    private static volatile boolean running = false;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile java.lang.Thread thread;
    @org.jetbrains.annotations.NotNull()
    private static final short[] outputBuffer = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.google.mediapipe.examples.objectdetection.SynthEngine INSTANCE = null;
    
    private SynthEngine() {
        super();
    }
    
    /**
     * Start the synthesis engine.
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void start() {
    }
    
    /**
     * Stop the synthesis engine and release resources.
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void stop() {
    }
    
    /**
     * Generate one frame of audio samples.
     * Override this method or add parameters for dynamic synthesis.
     */
    private final short[] generateFrame() {
        return null;
    }
    
    /**
     * Generate a sine wave at the given frequency and amplitude.
     */
    @org.jetbrains.annotations.NotNull()
    public final short[] generateSine(float frequency, float amplitude, float phase) {
        return null;
    }
    
    /**
     * Generate a square wave.
     */
    @org.jetbrains.annotations.NotNull()
    public final short[] generateSquare(float frequency, float amplitude) {
        return null;
    }
    
    /**
     * Generate white noise.
     */
    @org.jetbrains.annotations.NotNull()
    public final short[] generateNoise(float amplitude) {
        return null;
    }
    
    /**
     * Generate triangle wave.
     */
    @org.jetbrains.annotations.NotNull()
    public final short[] generateTriangle(float frequency, float amplitude) {
        return null;
    }
    
    /**
     * Simple low-pass filter using one-pole IIR (dc blocker + smoothing).
     * cutoffHz: cutoff frequency in Hz
     */
    @org.jetbrains.annotations.NotNull()
    public final short[] applyLowPass(@org.jetbrains.annotations.NotNull()
    short[] samples, float cutoffHz) {
        return null;
    }
    
    /**
     * Apply resonance (emphasis around cutoff frequency).
     */
    @org.jetbrains.annotations.NotNull()
    public final short[] applyResonance(@org.jetbrains.annotations.NotNull()
    short[] samples, float cutoffHz, float resonance) {
        return null;
    }
}