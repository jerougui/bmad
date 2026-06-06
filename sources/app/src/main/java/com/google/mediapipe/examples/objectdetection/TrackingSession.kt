package com.google.mediapipe.examples.objectdetection

import android.graphics.PointF
import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable

/**
 * Encapsulates the persistent state for a single-object tracking session.
 *
 * This class maintains the target's appearance features and tracking status
 * across detection frames, allowing the system to persist tracking through
 * occlusions and automatically re-acquire the object when it reappears.
 *
 * @property targetFeatures Normalized feature vector (histogram + aspect ratio) for re-identification
 * @property targetClass Class label of the tracked object (e.g., "person", "car")
 * @property targetInitialBounds Initial bounding box at selection time (for reference)
 * @property isActive True when tracking mode is enabled (object selected, not stopped)
 * @property isLost True when target is temporarily not detected but tracking remains active
 * @property framesSinceLastSeen Number of consecutive frames the target has been missing
 * @property lastKnownPosition Last detected center position (for velocity smoothing during loss)
 */
data class TrackingSession(
    val targetFeatures: FloatArray,
    val targetClass: String,
    val targetInitialBounds: Rect,
    var isActive: Boolean = false,
    var isLost: Boolean = false,
    var framesSinceLastSeen: Int = 0,
    var lastKnownPosition: PointF? = null,
    var targetDetectionIndex: Int? = null
) : Parcelable {
    /**
     * Flag indicating whether the target has been lost long enough to consider
     * entering the lost state. Threshold is 3 consecutive frames without detection.
     */
    fun markFrameProcessed(wasDetected: Boolean) {
        if (isActive) {
            if (wasDetected) {
                framesSinceLastSeen = 0
                isLost = false
            } else {
                framesSinceLastSeen++
                if (framesSinceLastSeen >= 3) {
                    isLost = true
                }
            }
        }
    }

    /**
     * Resets the lost flag and updates the last known position when the
     * target is re-acquired after being lost.
     */
    fun onReacquired(position: PointF) {
        isLost = false
        framesSinceLastSeen = 0
        lastKnownPosition = position
    }

    /**
     * Ends the tracking session and clears all state.
     */
    fun reset() {
        isActive = false
        isLost = false
        framesSinceLastSeen = 0
        lastKnownPosition = null
        targetDetectionIndex = null
    }

    // Parcelable implementation
    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeFloatArray(targetFeatures)
        dest.writeString(targetClass)
        dest.writeParcelable(targetInitialBounds, flags)
        dest.writeByte(if (isActive) 1 else 0)
        dest.writeByte(if (isLost) 1 else 0)
        dest.writeInt(framesSinceLastSeen)
        dest.writeParcelable(lastKnownPosition, flags)
        dest.writeValue(targetDetectionIndex)
    }

    companion object CREATOR : Parcelable.Creator<TrackingSession> {
        override fun createFromParcel(source: Parcel): TrackingSession {
            val targetFeatures = source.createFloatArray() ?: FloatArray(0)
            val targetClass = source.readString() ?: ""
            val targetInitialBounds = source.readParcelable<Rect>(Rect::class.java.classLoader)!!
            val isActive = source.readByte() != 0.toByte()
            val isLost = source.readByte() != 0.toByte()
            val framesSinceLastSeen = source.readInt()
            val lastKnownPosition = source.readParcelable<PointF>(PointF::class.java.classLoader)
            val targetDetectionIndex = source.readValue(Int::class.java.classLoader) as? Int

            return TrackingSession(
                targetFeatures = targetFeatures,
                targetClass = targetClass,
                targetInitialBounds = targetInitialBounds,
                isActive = isActive,
                isLost = isLost,
                framesSinceLastSeen = framesSinceLastSeen,
                lastKnownPosition = lastKnownPosition,
                targetDetectionIndex = targetDetectionIndex
            )
        }

        override fun newArray(size: Int): Array<TrackingSession?> {
            return arrayOfNulls(size)
        }
    }
}
