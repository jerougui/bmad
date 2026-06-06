package com.google.mediapipe.examples.objectdetection

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log

private const val TAG = "MusicPlayer"
private const val MAX_STREAMS = 8

object MusicPlayer {

    @Volatile private var soundPool: SoundPool? = null
    @Volatile private var noteIds: Map<Int, Int> = emptyMap()
    @Volatile private var volumeMultiplier: Float = 1f
    @Volatile private var _initialized = false

    @Synchronized
    fun init(context: Context) {
        if (_initialized) return
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(MAX_STREAMS)
            .setAudioAttributes(attrs)
            .build()
        _initialized = true
    }

    /**
     * Register note samples: each pair is [logical noteIndex → R.raw resource id].
     * @param context    Application context (needed by SoundPool.load)
     * @param noteResIdMap index → resId for each note to pre-load
     */
    @Synchronized
    fun registerNotes(context: Context, noteResIdMap: Map<Int, Int>) {
        soundPool.let { sp ->
            if (sp != null) return@let
            init(context)
        }
        val sp = soundPool ?: return
        val uploaded = mutableMapOf<Int, Int>()
        for ((index, resId) in noteResIdMap) {
            val sndId = sp.load(context, resId, 1)
            uploaded[index] = sndId
            Log.v(TAG, "Loaded note[$index] → resId=$resId → soundId=$sndId")
        }
        noteIds = uploaded
        Log.d(TAG, "Registered ${uploaded.size} note samples")
    }

    @Synchronized
    fun registerNote(index: Int, context: Context, resId: Int) {
        soundPool.let { sp ->
            if (sp != null) return@let
            init(context)
        }
        val sp = soundPool ?: return
        noteIds = noteIds + (index to sp.load(context, resId, 1))
    }

    @Synchronized
    fun play(index: Int, volume: Float) {
        val sp = soundPool ?: run {
            Log.d(TAG, "play() skipped: SoundPool not initialized")
            return
        }
        val sndId = noteIds[index] ?: run {
            Log.d(TAG, "play() skipped: no sample registered for index $index")
            return
        }
        if (volume <= 0f) {
            Log.v(TAG, "play() skipped: volume=$volume")
            return
        }
        val finalVol = (volume * volumeMultiplier).coerceIn(0f, 1f)
        Log.d(TAG, "PLAY note[$index] sndId=$sndId volume=$finalVol")
        if (finalVol > 0f) {
            sp.play(sndId, finalVol, finalVol, /*priority*/ 1, /*loop*/ 0, /*rate*/ 1f)
        }
    }

    @Synchronized
    fun setVolumeMultiplier(mult: Float) {
        volumeMultiplier = mult.coerceIn(0f, 1f)
    }

    @Synchronized
    fun pauseAll() = soundPool?.autoPause()

    @Synchronized
    fun resumeAll() = soundPool?.autoResume()

    @Synchronized
    fun release() {
        soundPool?.release()
        soundPool = null
        noteIds = emptyMap()
        _initialized = false
    }

    @get:Synchronized
    val isInitialized: Boolean get() = _initialized
}
