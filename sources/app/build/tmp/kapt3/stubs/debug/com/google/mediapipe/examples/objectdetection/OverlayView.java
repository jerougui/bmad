package com.google.mediapipe.examples.objectdetection;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 A2\u00020\u0001:\u0001AB\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010,\u001a\u00020\u0013J\u0006\u0010-\u001a\u00020\u0013J\u0010\u0010.\u001a\u00020\u00132\u0006\u0010/\u001a\u000200H\u0016J\r\u00101\u001a\u0004\u0018\u00010\u0019\u00a2\u0006\u0002\u00102J\b\u00103\u001a\u0004\u0018\u00010\u000eJ\b\u00104\u001a\u00020\u0013H\u0002J\u0010\u00105\u001a\u00020\f2\u0006\u00106\u001a\u000207H\u0016J&\u00108\u001a\u00020\u00132\u0006\u00109\u001a\u00020\u001d2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010:\u001a\u00020\u0019J\u000e\u0010;\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u001fJ\u0015\u0010<\u001a\u00020\u00132\b\u0010=\u001a\u0004\u0018\u00010\u0019\u00a2\u0006\u0002\u0010>J\u0010\u0010?\u001a\u00020\u00132\b\u0010@\u001a\u0004\u0018\u00010*R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\"\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010#R\u000e\u0010$\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006B"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/OverlayView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "bounds", "Landroid/graphics/Rect;", "boxPaint", "Landroid/graphics/Paint;", "isStopButtonPressed", "", "lastSelectedCenter", "Landroid/graphics/PointF;", "lastSelectedTimestampNanos", "", "onStopTracking", "Lkotlin/Function0;", "", "getOnStopTracking", "()Lkotlin/jvm/functions/Function0;", "setOnStopTracking", "(Lkotlin/jvm/functions/Function0;)V", "outputHeight", "", "outputRotate", "outputWidth", "results", "Lcom/google/mediapipe/tasks/vision/objectdetector/ObjectDetectorResult;", "runningMode", "Lcom/google/mediapipe/tasks/vision/core/RunningMode;", "scaleFactor", "", "selectedDetectionIndex", "Ljava/lang/Integer;", "stopButtonPaint", "stopButtonTextPaint", "textBackgroundPaint", "textPaint", "trackingBackgroundPaint", "trackingSession", "Lcom/google/mediapipe/examples/objectdetection/TrackingSession;", "trackingTextPaint", "clear", "clearSelection", "draw", "canvas", "Landroid/graphics/Canvas;", "getSelectedDetectionIndex", "()Ljava/lang/Integer;", "getTrackedObjectCenter", "initPaints", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "setResults", "detectionResults", "imageRotation", "setRunningMode", "setSelectedDetectionIndex", "index", "(Ljava/lang/Integer;)V", "setTrackingSession", "session", "Companion", "app_debug"})
public final class OverlayView extends android.view.View {
    @org.jetbrains.annotations.Nullable()
    private com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult results;
    @org.jetbrains.annotations.NotNull()
    private android.graphics.Paint boxPaint;
    @org.jetbrains.annotations.NotNull()
    private android.graphics.Paint textBackgroundPaint;
    @org.jetbrains.annotations.NotNull()
    private android.graphics.Paint textPaint;
    private float scaleFactor = 1.0F;
    @org.jetbrains.annotations.NotNull()
    private android.graphics.Rect bounds;
    private int outputWidth = 0;
    private int outputHeight = 0;
    private int outputRotate = 0;
    @org.jetbrains.annotations.NotNull()
    private com.google.mediapipe.tasks.vision.core.RunningMode runningMode = com.google.mediapipe.tasks.vision.core.RunningMode.IMAGE;
    @org.jetbrains.annotations.Nullable()
    private com.google.mediapipe.examples.objectdetection.TrackingSession trackingSession;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Integer selectedDetectionIndex;
    @org.jetbrains.annotations.Nullable()
    private android.graphics.PointF lastSelectedCenter;
    private long lastSelectedTimestampNanos = 0L;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint trackingTextPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint trackingBackgroundPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint stopButtonPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint stopButtonTextPaint = null;
    private boolean isStopButtonPressed = false;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function0<kotlin.Unit> onStopTracking;
    private static final int BOUNDING_RECT_TEXT_PADDING = 8;
    @org.jetbrains.annotations.NotNull()
    public static final com.google.mediapipe.examples.objectdetection.OverlayView.Companion Companion = null;
    
    public OverlayView(@org.jetbrains.annotations.Nullable()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @org.jetbrains.annotations.Nullable()
    public final kotlin.jvm.functions.Function0<kotlin.Unit> getOnStopTracking() {
        return null;
    }
    
    public final void setOnStopTracking(@org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> p0) {
    }
    
    public final void clear() {
    }
    
    public final void setRunningMode(@org.jetbrains.annotations.NotNull()
    com.google.mediapipe.tasks.vision.core.RunningMode runningMode) {
    }
    
    public final void setTrackingSession(@org.jetbrains.annotations.Nullable()
    com.google.mediapipe.examples.objectdetection.TrackingSession session) {
    }
    
    public final void setSelectedDetectionIndex(@org.jetbrains.annotations.Nullable()
    java.lang.Integer index) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getSelectedDetectionIndex() {
        return null;
    }
    
    public final void clearSelection() {
    }
    
    /**
     * Returns the center of the tracked object in screen coordinates, or null if not tracking.
     */
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.PointF getTrackedObjectCenter() {
        return null;
    }
    
    private final void initPaints() {
    }
    
    @java.lang.Override()
    public void draw(@org.jetbrains.annotations.NotNull()
    android.graphics.Canvas canvas) {
    }
    
    @java.lang.Override()
    public boolean onTouchEvent(@org.jetbrains.annotations.NotNull()
    android.view.MotionEvent event) {
        return false;
    }
    
    public final void setResults(@org.jetbrains.annotations.NotNull()
    com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult detectionResults, int outputHeight, int outputWidth, int imageRotation) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/google/mediapipe/examples/objectdetection/OverlayView$Companion;", "", "()V", "BOUNDING_RECT_TEXT_PADDING", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}