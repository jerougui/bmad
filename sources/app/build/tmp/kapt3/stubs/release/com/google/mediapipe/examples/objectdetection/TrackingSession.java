package com.google.mediapipe.examples.objectdetection;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b&\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\b\u0018\u0000 B2\u00020\u0001:\u0001BBS\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\u0010J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0005H\u00c6\u0003J\t\u0010*\u001a\u00020\u0007H\u00c6\u0003J\t\u0010+\u001a\u00020\tH\u00c6\u0003J\t\u0010,\u001a\u00020\tH\u00c6\u0003J\t\u0010-\u001a\u00020\fH\u00c6\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\u0010\u0010/\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010 Jb\u00100\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\fH\u00c6\u0001\u00a2\u0006\u0002\u00101J\b\u00102\u001a\u00020\fH\u0016J\u0013\u00103\u001a\u00020\t2\b\u00104\u001a\u0004\u0018\u000105H\u00d6\u0003J\t\u00106\u001a\u00020\fH\u00d6\u0001J\u000e\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\tJ\u000e\u0010:\u001a\u0002082\u0006\u0010;\u001a\u00020\u000eJ\u0006\u0010<\u001a\u000208J\t\u0010=\u001a\u00020\u0005H\u00d6\u0001J\u0018\u0010>\u001a\u0002082\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\fH\u0016R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\n\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0015\"\u0004\b\u0018\u0010\u0017R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001e\u0010\u000f\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010#\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'\u00a8\u0006C"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/TrackingSession;", "Landroid/os/Parcelable;", "targetFeatures", "", "targetClass", "", "targetInitialBounds", "Landroid/graphics/Rect;", "isActive", "", "isLost", "framesSinceLastSeen", "", "lastKnownPosition", "Landroid/graphics/PointF;", "targetDetectionIndex", "([FLjava/lang/String;Landroid/graphics/Rect;ZZILandroid/graphics/PointF;Ljava/lang/Integer;)V", "getFramesSinceLastSeen", "()I", "setFramesSinceLastSeen", "(I)V", "()Z", "setActive", "(Z)V", "setLost", "getLastKnownPosition", "()Landroid/graphics/PointF;", "setLastKnownPosition", "(Landroid/graphics/PointF;)V", "getTargetClass", "()Ljava/lang/String;", "getTargetDetectionIndex", "()Ljava/lang/Integer;", "setTargetDetectionIndex", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getTargetFeatures", "()[F", "getTargetInitialBounds", "()Landroid/graphics/Rect;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "([FLjava/lang/String;Landroid/graphics/Rect;ZZILandroid/graphics/PointF;Ljava/lang/Integer;)Lcom/google/mediapipe/examples/objectdetection/TrackingSession;", "describeContents", "equals", "other", "", "hashCode", "markFrameProcessed", "", "wasDetected", "onReacquired", "position", "reset", "toString", "writeToParcel", "dest", "Landroid/os/Parcel;", "flags", "CREATOR", "app_release"})
public final class TrackingSession implements android.os.Parcelable {
    @org.jetbrains.annotations.NotNull()
    private final float[] targetFeatures = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String targetClass = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Rect targetInitialBounds = null;
    private boolean isActive;
    private boolean isLost;
    private int framesSinceLastSeen;
    @org.jetbrains.annotations.Nullable()
    private android.graphics.PointF lastKnownPosition;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Integer targetDetectionIndex;
    @org.jetbrains.annotations.NotNull()
    public static final com.google.mediapipe.examples.objectdetection.TrackingSession.CREATOR CREATOR = null;
    
    @org.jetbrains.annotations.NotNull()
    public final float[] component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Rect component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.PointF component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.mediapipe.examples.objectdetection.TrackingSession copy(@org.jetbrains.annotations.NotNull()
    float[] targetFeatures, @org.jetbrains.annotations.NotNull()
    java.lang.String targetClass, @org.jetbrains.annotations.NotNull()
    android.graphics.Rect targetInitialBounds, boolean isActive, boolean isLost, int framesSinceLastSeen, @org.jetbrains.annotations.Nullable()
    android.graphics.PointF lastKnownPosition, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetDetectionIndex) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    public TrackingSession(@org.jetbrains.annotations.NotNull()
    float[] targetFeatures, @org.jetbrains.annotations.NotNull()
    java.lang.String targetClass, @org.jetbrains.annotations.NotNull()
    android.graphics.Rect targetInitialBounds, boolean isActive, boolean isLost, int framesSinceLastSeen, @org.jetbrains.annotations.Nullable()
    android.graphics.PointF lastKnownPosition, @org.jetbrains.annotations.Nullable()
    java.lang.Integer targetDetectionIndex) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final float[] getTargetFeatures() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTargetClass() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Rect getTargetInitialBounds() {
        return null;
    }
    
    public final boolean isActive() {
        return false;
    }
    
    public final void setActive(boolean p0) {
    }
    
    public final boolean isLost() {
        return false;
    }
    
    public final void setLost(boolean p0) {
    }
    
    public final int getFramesSinceLastSeen() {
        return 0;
    }
    
    public final void setFramesSinceLastSeen(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.PointF getLastKnownPosition() {
        return null;
    }
    
    public final void setLastKnownPosition(@org.jetbrains.annotations.Nullable()
    android.graphics.PointF p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getTargetDetectionIndex() {
        return null;
    }
    
    public final void setTargetDetectionIndex(@org.jetbrains.annotations.Nullable()
    java.lang.Integer p0) {
    }
    
    /**
     * Flag indicating whether the target has been lost long enough to consider
     * entering the lost state. Threshold is 3 consecutive frames without detection.
     */
    public final void markFrameProcessed(boolean wasDetected) {
    }
    
    /**
     * Resets the lost flag and updates the last known position when the
     * target is re-acquired after being lost.
     */
    public final void onReacquired(@org.jetbrains.annotations.NotNull()
    android.graphics.PointF position) {
    }
    
    /**
     * Ends the tracking session and clears all state.
     */
    public final void reset() {
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
    }
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel dest, int flags) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001d\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/TrackingSession$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/google/mediapipe/examples/objectdetection/TrackingSession;", "()V", "createFromParcel", "source", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lcom/google/mediapipe/examples/objectdetection/TrackingSession;", "app_release"})
    public static final class CREATOR implements android.os.Parcelable.Creator<com.google.mediapipe.examples.objectdetection.TrackingSession> {
        
        private CREATOR() {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.google.mediapipe.examples.objectdetection.TrackingSession createFromParcel(@org.jetbrains.annotations.NotNull()
        android.os.Parcel source) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.google.mediapipe.examples.objectdetection.TrackingSession[] newArray(int size) {
            return null;
        }
    }
}