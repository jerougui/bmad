/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.mediapipe.examples.objectdetection

import androidx.lifecycle.ViewModel

/**
 *  This ViewModel is used to store object detector helper settings
 */
class MainViewModel : ViewModel() {
    private var _delegate: Int = ObjectDetectorHelper.DELEGATE_CPU
    private var _threshold: Float =
        ObjectDetectorHelper.THRESHOLD_DEFAULT
    private var _maxResults: Int =
        ObjectDetectorHelper.MAX_RESULTS_DEFAULT
    private var _model: Int = ObjectDetectorHelper.MODEL_EFFICIENTDETV0
    private var _instrument: String = "piano"
    private var _synthwaveStyle: String = "cinématique"
    private var _audioEnabled: Boolean = true

    val currentDelegate: Int get() = _delegate
    val currentThreshold: Float get() = _threshold
    val currentMaxResults: Int get() = _maxResults
    val currentModel: Int get() = _model
    val currentInstrument: String get() = _instrument
    val currentSynthwaveStyle: String get() = _synthwaveStyle
    val isAudioEnabled: Boolean get() = _audioEnabled

    fun setDelegate(delegate: Int) {
        _delegate = delegate
    }

    fun setThreshold(threshold: Float) {
        _threshold = threshold
    }

    fun setMaxResults(maxResults: Int) {
        _maxResults = maxResults
    }

    fun setModel(model: Int) {
        _model = model
    }

    fun setInstrument(instrument: String) {
        _instrument = instrument
        _audioEnabled = instrument != "disabled"
    }

    fun setSynthwaveStyle(style: String) {
        _synthwaveStyle = style
    }

    fun setAudioEnabled(enabled: Boolean) {
        _audioEnabled = enabled
    }
}
