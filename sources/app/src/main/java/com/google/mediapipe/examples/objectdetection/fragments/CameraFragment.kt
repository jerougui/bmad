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
package com.google.mediapipe.examples.objectdetection.fragments

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.PointF
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.mediapipe.examples.objectdetection.MainViewModel
import com.google.mediapipe.examples.objectdetection.ObjectDetectorHelper
import com.google.mediapipe.examples.objectdetection.R
import com.google.mediapipe.examples.objectdetection.TrackingSession
import com.google.mediapipe.examples.objectdetection.TrackingFeatureExtractor
import com.google.mediapipe.examples.objectdetection.MotionToMusicEngine
import com.google.mediapipe.examples.objectdetection.databinding.FragmentCameraBinding
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class CameraFragment : Fragment(), ObjectDetectorHelper.DetectorListener {

    private val TAG = "ObjectDetection"

    private var _fragmentCameraBinding: FragmentCameraBinding? = null

    private val fragmentCameraBinding
        get() = _fragmentCameraBinding!!

    private lateinit var objectDetectorHelper: ObjectDetectorHelper
    private val viewModel: MainViewModel by activityViewModels()
    private var preview: Preview? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var trackingSession: TrackingSession? = null
    private var currentDetections: List<Any>? = null
    private var currentFeatures: List<FloatArray>? = null
    // Shadow dimensions for MotionToMusicEngine; updated per-frame
    private var motionEngineWidthPx  = 1
    private var motionEngineHeightPx = 1

    private fun stopTrackingSession() {
        trackingSession?.reset()
        trackingSession = null
        fragmentCameraBinding.overlay.setTrackingSession(null)
        MotionToMusicEngine.setTrackingSession(null)
        fragmentCameraBinding.overlay.setSelectedDetectionIndex(null)
        fragmentCameraBinding.overlay.clearSelection()
    }

    private fun startTrackingSession(detection: Any, features: FloatArray) {
        val bounds = TrackingFeatureExtractor.extractBoundingBox(detection)
        val className = TrackingFeatureExtractor.extractClassName(detection)
        val index = currentDetections?.indexOf(detection) ?: -1

        trackingSession = TrackingSession(
            targetFeatures = features,
            targetClass = className,
            targetInitialBounds = Rect(bounds),
            isActive = true,
            isLost = false,
            framesSinceLastSeen = 0,
            lastKnownPosition = null,
            targetDetectionIndex = if (index >= 0) index else null
        )

        fragmentCameraBinding.overlay.setTrackingSession(trackingSession)
        MotionToMusicEngine.setTrackingSession(trackingSession)
        if (index >= 0) {
            fragmentCameraBinding.overlay.setSelectedDetectionIndex(index)
        }
    }

    private lateinit var backgroundExecutor: ExecutorService

    override fun onResume() {
        super.onResume()
        if (!PermissionsFragment.hasPermissions(requireContext())) {
            Navigation.findNavController(
                requireActivity(),
                R.id.fragment_container
            )
                .navigate(CameraFragmentDirections.actionCameraToPermissions())
        }

        backgroundExecutor.execute {
            if (objectDetectorHelper.isClosed()) {
                objectDetectorHelper.setupObjectDetector()
            }
        }

        // Start the motion-to-music engine on the main (UI) thread.
        // SoundPool requires a thread with a Looper (the main thread qualifies).
        fragmentCameraBinding.overlay.post {
            Log.d(TAG, "Starting MotionToMusicEngine from onResume")
            MotionToMusicEngine.start(requireContext())
            MotionToMusicEngine.updateScreenDimensions(
                fragmentCameraBinding.overlay.width,
                fragmentCameraBinding.overlay.height
            )
            Log.d(TAG, "MotionToMusicEngine started")
        }
    }

    override fun onPause() {
        super.onPause()
        MotionToMusicEngine.stop()

        if(this::objectDetectorHelper.isInitialized) {
            viewModel.setModel(objectDetectorHelper.currentModel)
            viewModel.setDelegate(objectDetectorHelper.currentDelegate)
            viewModel.setThreshold(objectDetectorHelper.threshold)
            viewModel.setMaxResults(objectDetectorHelper.maxResults)
            backgroundExecutor.execute { objectDetectorHelper.clearObjectDetector() }
        }

    }

    override fun onDestroyView() {
        _fragmentCameraBinding = null
        super.onDestroyView()

        backgroundExecutor.shutdown()
        backgroundExecutor.awaitTermination(
            Long.MAX_VALUE,
            TimeUnit.NANOSECONDS
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentCameraBinding =
            FragmentCameraBinding.inflate(inflater, container, false)

        return fragmentCameraBinding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let { bundle ->
            bundle.getParcelable<TrackingSession>("tracking_session")?.let { session ->
                trackingSession = session
                fragmentCameraBinding.overlay.setTrackingSession(trackingSession)
                MotionToMusicEngine.setTrackingSession(trackingSession)
                session.targetDetectionIndex?.let { fragmentCameraBinding.overlay.setSelectedDetectionIndex(it) }
            }
        }

        backgroundExecutor = Executors.newSingleThreadExecutor()

        backgroundExecutor.execute {
            objectDetectorHelper =
                ObjectDetectorHelper(
                    context = requireContext(),
                    threshold = viewModel.currentThreshold,
                    currentDelegate = viewModel.currentDelegate,
                    currentModel = viewModel.currentModel,
                    maxResults = viewModel.currentMaxResults,
                    objectDetectorListener = this,
                    runningMode = RunningMode.LIVE_STREAM
                )

            fragmentCameraBinding.viewFinder.post {
                setUpCamera()
            }
        }

        initBottomSheetControls()
        fragmentCameraBinding.overlay.setRunningMode(RunningMode.LIVE_STREAM)
        fragmentCameraBinding.overlay.onStopTracking = {
            MotionToMusicEngine.onTrackingStateChange(false, false)
            stopTrackingSession()
        }
    }

    private fun initBottomSheetControls() {
        fragmentCameraBinding.bottomSheetLayout.maxResultsValue.text =
            viewModel.currentMaxResults.toString()
        fragmentCameraBinding.bottomSheetLayout.thresholdValue.text =
            String.format("%.2f", viewModel.currentThreshold)

        fragmentCameraBinding.bottomSheetLayout.thresholdMinus.setOnClickListener {
            if (objectDetectorHelper.threshold >= 0.1) {
                objectDetectorHelper.threshold -= 0.1f
                updateControlsUi()
            }
        }

        fragmentCameraBinding.bottomSheetLayout.thresholdPlus.setOnClickListener {
            if (objectDetectorHelper.threshold <= 0.8) {
                objectDetectorHelper.threshold += 0.1f
                updateControlsUi()
            }
        }

        fragmentCameraBinding.bottomSheetLayout.maxResultsMinus.setOnClickListener {
            if (objectDetectorHelper.maxResults > 1) {
                objectDetectorHelper.maxResults--
                updateControlsUi()
            }
        }

        fragmentCameraBinding.bottomSheetLayout.maxResultsPlus.setOnClickListener {
            if (objectDetectorHelper.maxResults < 5) {
                objectDetectorHelper.maxResults++
                updateControlsUi()
            }
        }

        fragmentCameraBinding.bottomSheetLayout.spinnerDelegate.setSelection(
            viewModel.currentDelegate,
            false
        )
        fragmentCameraBinding.bottomSheetLayout.spinnerDelegate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long
                ) {
                    try {
                        objectDetectorHelper.currentDelegate = p2
                        updateControlsUi()
                    } catch(e: UninitializedPropertyAccessException) {
                        Log.e(TAG, "ObjectDetectorHelper has not been initialized yet.")
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    /* no op */
                }
            }

        fragmentCameraBinding.bottomSheetLayout.spinnerModel.setSelection(
            viewModel.currentModel,
            false
        )
        fragmentCameraBinding.bottomSheetLayout.spinnerModel.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long
                ) {
                    try {
                        objectDetectorHelper.currentModel = p2
                        updateControlsUi()
                    } catch(e: UninitializedPropertyAccessException) {
                        Log.e(TAG, "ObjectDetectorHelper has not been initialized yet.")
                    }
                }

override fun onNothingSelected(p0: AdapterView<*>?) {
                      /* no op */
                  }
              }

          // Instrument spinner - now includes "disabled" at index 0
          fragmentCameraBinding.bottomSheetLayout.spinnerInstrument.setSelection(
              when (viewModel.currentInstrument) {
                  "disabled" -> 0
                  "piano" -> 1
                  "flute" -> 2
                  "violin" -> 3
                  else -> 0
              },
              false
          )
          fragmentCameraBinding.bottomSheetLayout.spinnerInstrument.onItemSelectedListener =
              object : AdapterView.OnItemSelectedListener {
                  override fun onItemSelected(
                      p0: AdapterView<*>?,
                      p1: View?,
                      p2: Int,
                      p3: Long
                  ) {
                      val instrument = when (p2) {
                          0 -> "disabled"
                          1 -> "piano"
                          2 -> "flute"
                          3 -> "violin"
                          else -> "disabled"
                      }
                      viewModel.setInstrument(instrument)
                      if (instrument != "disabled") {
                          MotionToMusicEngine.setInstrument(requireContext(), instrument)
                      }
                  }

                  override fun onNothingSelected(p0: AdapterView<*>?) {
                      /* no op */
                  }
              }

          // Synthwave style spinner
          fragmentCameraBinding.bottomSheetLayout.spinnerSynthwaveStyle.setSelection(
              when (viewModel.currentSynthwaveStyle) {
                  "cinématique" -> 0
                  "retrowave" -> 1
                  "orchestral" -> 2
                  else -> 0
              },
              false
          )
          fragmentCameraBinding.bottomSheetLayout.spinnerSynthwaveStyle.onItemSelectedListener =
              object : AdapterView.OnItemSelectedListener {
                  override fun onItemSelected(
                      p0: AdapterView<*>?,
                      p1: View?,
                      p2: Int,
                      p3: Long
                  ) {
                      val style = when (p2) {
                          0 -> "cinématique"
                          1 -> "retrowave"
                          2 -> "orchestral"
                          else -> "cinématique"
                      }
                      viewModel.setSynthwaveStyle(style)
                  }

                  override fun onNothingSelected(p0: AdapterView<*>?) {
                      /* no op */
                  }
              }
      }

    private fun updateControlsUi() {
        fragmentCameraBinding.bottomSheetLayout.maxResultsValue.text =
            objectDetectorHelper.maxResults.toString()
        fragmentCameraBinding.bottomSheetLayout.thresholdValue.text =
            String.format("%.2f", objectDetectorHelper.threshold)

        backgroundExecutor.execute {
            objectDetectorHelper.clearObjectDetector()
            objectDetectorHelper.setupObjectDetector()
        }

        fragmentCameraBinding.overlay.clear()
    }

    private fun setUpCamera() {
        val cameraProviderFuture =
            ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            {
                cameraProvider = cameraProviderFuture.get()
                bindCameraUseCases()
            },
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun bindCameraUseCases() {

        val cameraProvider =
            cameraProvider
                ?: throw IllegalStateException("Camera initialization failed.")

        val cameraSelector =
            CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        preview =
            Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(fragmentCameraBinding.viewFinder.display.rotation)
                .build()

        imageAnalyzer =
            ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(fragmentCameraBinding.viewFinder.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()
                .also {
                    it.setAnalyzer(
                        backgroundExecutor,
                        objectDetectorHelper::detectLivestreamFrame
                    )
                }

        cameraProvider.unbindAll()

        try {
            camera = cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageAnalyzer
            )

            preview?.setSurfaceProvider(fragmentCameraBinding.viewFinder.surfaceProvider)
        } catch (exc: Exception) {
            Log.e(TAG, "Use case binding failed", exc)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        imageAnalyzer?.targetRotation =
            fragmentCameraBinding.viewFinder.display.rotation
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        trackingSession?.let { session ->
            outState.putParcelable("tracking_session", session)
        }
    }

    override fun onResults(resultBundle: ObjectDetectorHelper.ResultBundle) {
        activity?.runOnUiThread {
            if (_fragmentCameraBinding != null) {
                fragmentCameraBinding.bottomSheetLayout.inferenceTimeVal.text =
                    String.format("%d ms", resultBundle.inferenceTime)

                val detectionResult = resultBundle.results[0]
                if (isAdded) {
                    val detections = detectionResult.detections()
                    currentDetections = detections

                    val needFeatures = trackingSession != null || fragmentCameraBinding.overlay.getSelectedDetectionIndex() != null
                    if (needFeatures && resultBundle.frameBitmap != null && detections.isNotEmpty()) {
                        currentFeatures = detections.map { detection ->
                            TrackingFeatureExtractor.computeFeatures(resultBundle.frameBitmap!!, detection)
                        }
                    } else {
                        currentFeatures = null
                    }

                    val overlay = fragmentCameraBinding.overlay

                    if (trackingSession == null) {
                        val selectedIdx = overlay.getSelectedDetectionIndex()
                        if (selectedIdx != null && selectedIdx < detections.size) {
                            val features = currentFeatures?.getOrNull(selectedIdx)
                            if (features != null) {
                                startTrackingSession(detections[selectedIdx], features)
                            }
                        }
                    }

                    trackingSession?.let { session ->
                        val targetIdx = session.targetDetectionIndex
                        val targetFound = if (targetIdx != null && targetIdx < detections.size) {
                            val detection = detections[targetIdx]
                            TrackingFeatureExtractor.extractClassName(detection) == session.targetClass
                        } else false

                        session.markFrameProcessed(targetFound)

if (targetFound) {
                             session.targetDetectionIndex?.let { idx ->
                                 // Let OverlayView calculate the tracked object center
                                 // getTrackedObjectCenter() will be called separately
                             }
                         }

                        if (session.isLost && !targetFound) {
                            for ((i, detection) in detections.withIndex()) {
                                if (i == targetIdx) continue
                                val className = TrackingFeatureExtractor.extractClassName(detection)
                                if (className != session.targetClass) continue
                                val candFeatures = currentFeatures?.getOrNull(i) ?: continue
                                val score = TrackingFeatureExtractor.computeSimilarity(
                                    session.targetFeatures,
                                    candFeatures,
                                    session.targetClass,
                                    className
                                )
if (score >= 0.65f) {
                                     val box = TrackingFeatureExtractor.extractBoundingBox(detection)
                                     val center = PointF((box.left + box.right) / 2f, (box.top + box.bottom) / 2f)
                                     session.onReacquired(center)
                                     session.targetDetectionIndex = i
                                     overlay.setSelectedDetectionIndex(i)
                                     MotionToMusicEngine.setTrackingSession(session)
                                     Log.d(TAG, "Re-acquired target at index $i, score=$score")
                                     break
                                 }
                            }
                        }
                    }

                    overlay.setResults(
                        detectionResult,
                        resultBundle.inputImageHeight,
                        resultBundle.inputImageWidth,
                        resultBundle.inputImageRotation
                    )
                    overlay.setTrackingSession(trackingSession)
                }

                // ── Hook: MotionToMusicEngine per-frame update ──────────────────
                // Called after tracking state is settled but before invalidate(),
                // so the engine sees the complete frame data for this cycle.
                trackingSession?.let { session ->
                    Log.v(TAG, "onResults: trackingSession active=${session.isActive} isLost=${session.isLost}")
                    val center = fragmentCameraBinding.overlay.getTrackedObjectCenter()
                    if (center != null) {
                        val className = session.targetClass
                        MotionToMusicEngine.onPositionUpdate(
                            posXPx    = center.x,
                            posYPx    = center.y,
                            tsMs      = System.currentTimeMillis(),
                            _className = className,
                        )
                    } else {
                        Log.v(TAG, "onResults: no tracked object center found")
                    }
                }

                fragmentCameraBinding.overlay.invalidate()
            }
        }
    }

    override fun onError(error: String, errorCode: Int) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            if (errorCode == ObjectDetectorHelper.GPU_ERROR) {
                fragmentCameraBinding.bottomSheetLayout.spinnerDelegate.setSelection(
                    ObjectDetectorHelper.DELEGATE_CPU, false
                )
            }
        }
    }
}