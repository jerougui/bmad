package com.google.mediapipe.examples.objectdetection

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.random.Random

/**
 * Procedural synthesis engine for generating synthwave-style sounds in real-time.
 * Features: supersaw, sub-oscillator, Moog-style ladder filter, LFO, ADSR envelope.
 */
object SynthEngine {
    private const val SAMPLE_RATE = 44100
    private const val BUFFER_SIZE = 1024
    private const val CHANNELS = AudioFormat.CHANNEL_OUT_STEREO
    private const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT

    @Volatile private var audioTrack: AudioTrack? = null
    @Volatile private var running = false
    @Volatile private var thread: Thread? = null

    private val leftBuffer = FloatArray(BUFFER_SIZE)
    private val rightBuffer = FloatArray(BUFFER_SIZE)

    enum class Waveform {
        SINE, SAWTOOTH, SUPERSAW, SUBSAW, NOISE
    }

    data class VoiceState(
        var frequency: Float = 440f,
        var amplitude: Float = 0.0f,
        var targetAmplitude: Float = 0.0f,
        var waveform: Waveform = Waveform.SUPERSAW,
        var filterCutoff: Float = 8000f,
        var resonance: Float = 0.7f,
        var lfoRate: Float = 5.2f,
        var lfoDepth: Float = 0.3f,
        var subMix: Float = 0.4f
    )

    private var voiceState = VoiceState()
    private var filterZL = 0.0
    private var filterZR = 0.0
    private var phaseLFO = 0.0
    private val random = Random(System.currentTimeMillis())

    fun setVoice(frequency: Float, amplitude: Float, waveform: Waveform = Waveform.SUPERSAW,
                 filterCutoff: Float = 8000f, resonance: Float = 0.7f) {
        voiceState.frequency = frequency
        voiceState.targetAmplitude = amplitude
        voiceState.waveform = waveform
        voiceState.filterCutoff = filterCutoff
        voiceState.resonance = resonance
    }

    fun setLFO(rateHz: Float, depth: Float = 0.3f) {
        voiceState.lfoRate = rateHz
        voiceState.lfoDepth = depth
    }

    fun setSubMix(ratio: Float) {
        voiceState.subMix = ratio
    }

    @Synchronized
    fun start() {
        if (running) return

        val minBufferSize = AudioTrack.getMinBufferSize(
            SAMPLE_RATE, CHANNELS, AUDIO_FORMAT
        )
        val bufferSize = maxOf(minBufferSize, BUFFER_SIZE * 2)

        val track = AudioTrack(
            AudioManager.STREAM_MUSIC,
            SAMPLE_RATE,
            CHANNELS,
            AUDIO_FORMAT,
            bufferSize,
            AudioTrack.MODE_STREAM
        )
        audioTrack = track
        running = true

        thread = Thread {
            while (running && !Thread.currentThread().isInterrupted) {
                val samples = generateFrame()
                track.write(samples, 0, samples.size)
            }
        }
        thread?.start()
        track.play()
    }

    @Synchronized
    fun stop() {
        running = false
        thread?.interrupt()
        try {
            audioTrack?.flush()
        } catch (e: Exception) {
        }
        audioTrack?.release()
        audioTrack = null
    }

    private fun generateFrame(): ShortArray {
        val vp = voiceState
        val dt = 1.0 / SAMPLE_RATE
        val omega = 2.0 * PI * vp.frequency / SAMPLE_RATE

        // Smooth amplitude transition (simple release)
        if (vp.amplitude < vp.targetAmplitude) {
            vp.amplitude += dt * 15.0f
            if (vp.amplitude > vp.targetAmplitude) vp.amplitude = vp.targetAmplitude
        } else if (vp.amplitude > vp.targetAmplitude) {
            vp.amplitude -= dt * 30.0f
            if (vp.amplitude < 0) vp.amplitude = 0f
        }

        val result = ShortArray(BUFFER_SIZE)
        val halfBuffer = BUFFER_SIZE / 2

        for (i in 0 until BUFFER_SIZE step 2) {
            val t = i.toDouble() / SAMPLE_RATE
            val sampleL = generateSample(t, vp, omega, isLeft = true)
            val sampleR = generateSample(t, vp, omega, isLeft = false)

            val l = (sampleL * 0.5f).toInt().coerceIn(-32767, 32767)
            val r = (sampleR * 0.5f).toInt().coerceIn(-32767, 32767)
            result[i] = l.toShort()
            result[i + 1] = r.toShort()
        }
        return result
    }

    private fun generateSample(t: Double, vp: VoiceState, omega: Double, isLeft: Boolean): Float {
        if (vp.amplitude <= 0.001f) return 0f

        val sample = when (vp.waveform) {
            Waveform.SUPERSAW -> generateSupersaw(t, vp, omega, isLeft)
            Waveform.SAWTOOTH -> generateSawtooth(t, vp, omega)
            Waveform.SINE -> generateSineSample(t, vp, omega)
            Waveform.NOISE -> (random.nextFloat() * 2 - 1) * 0.3f
            Waveform.SUBSAW -> generateSubSaw(t, vp, omega)
        }

        // Apply Moog-style ladder filter
        val cutoff = vp.filterCutoff * (1f + (voiceState.lfoDepth * 0.5f * sin(phaseLFO)).coerceIn(-1f, 1f))
        val filtered = moogLadderFilter(sample, cutoff, vp.resonance)

        return filtered * vp.amplitude
    }

    private fun generateSupersaw(t: Double, vp: VoiceState, omega: Double, isLeft: Boolean): Float {
        // 7 detuned sawtooth oscillators for thick sound
        val detunes = floatArrayOf(-0.025f, -0.018f, -0.009f, 0f, 0.007f, 0.014f, 0.022f)
        var sum = 0.0

        for (i in detunes.indices) {
            val detune = if (i == 0 && isLeft) detunes[i] * 1.1f else detunes[i]
            val freq = vp.frequency * (2.0.pow(detune.toDouble())).toFloat()
            val phase = (t * freq * 2 * PI).toInt() % 10000
            sum += sawtoothSample(phase.toFloat() / 10000f)
        }
        return (sum / detunes.size).toFloat()
    }

    private fun generateSubSaw(t: Double, vp: VoiceState, omega: Double): Float {
        val main = generateSawtooth(t, vp, omega)
        val subFreq = vp.frequency / 2f
        val subOmega = 2.0 * PI * subFreq / SAMPLE_RATE
        val sub = generateSawtooth(t, vp.copy(frequency = subFreq), subOmega)
        return main * (1f - vp.subMix) + sub * vp.subMix
    }

    private fun generateSawtooth(t: Double, vp: VoiceState, omega: Double): Float {
        val phase = (omega * t) % (2 * PI)
        return ((phase / PI) - 1).toFloat()
    }

    private fun generateSineSample(t: Double, vp: VoiceState, omega: Double): Float {
        return sin(omega * t).toFloat()
    }

    private fun sawtoothSample(phase: Float): Float {
        return phase * 2 - 1
    }

    private fun moogLadderFilter(input: Float, cutoffHz: Float, resonance: Float): Float {
        val f = cutoffHz / SAMPLE_RATE
        val k = 2.0f * sin(PI.toFloat() * f * 0.5f)
        val p = 1.0f - k
        val scale = exp(-2.0 * PI.toFloat() * resonance * cutoffHz / SAMPLE_RATE)

        filterZL = input + p * (filterZL - input) + k * filterZL
        filterZR = filterZL + p * (filterZR - filterZL) + k * filterZR

        val output = filterZR.toFloat() * scale
        return output.coerceIn(-1f, 1f)
    }

    private fun exp(x: Float): Float = kotlin.math.exp(x)

    fun release() {
        voiceState.targetAmplitude = 0f
    }
}