package com.cz.launcher.overlay.sample.scroll.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.cz.launcher.overlay.sample.BuildConfig;

public class SlidingPanelLayout extends FrameLayout {
    public static boolean DEBUG = BuildConfig.DEBUG;//true;
    public static boolean enableAlpha = false;
    public static boolean hardware = false;
    public static final SlidingPanelLayoutProperty PANEL_X = new SlidingPanelLayoutProperty(Integer.class, "panelX");
    public float mDownX;
    public float mDownY;
    public int mActivePointerId = -1;
    public float mDensity;
    public int mFlingThresholdVelocity;
    public boolean mIsPageMoving = false;
    public final boolean mIsRtl;
    public float mLastMotionX;
    public int mMaximumVelocity;
    public int mMinFlingVelocity;
    public int mMinSnapVelocity;
    public float mTotalMotionX;
    public int mTouchSlop;
    private int mTouchState = 0; // 0 idle 不接受， 1 dragging！接收/处理 TouchEvent
    public VelocityTracker mVelocityTracker;
    public View contentView;
    public View uoB;
    public int panelX;
    public float mPanelPositionRatio;
    public float uoE;
    public float uoF;
    public SlidingPanelLayoutInterpolator slidingPanelLayoutInterpolator;
    public DragListener dragListener;
    public boolean mIsPanelOpen = false;
    public boolean mForceDrag;
    public boolean mSettling;
    public DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(3.0f);

    public SlidingPanelLayout(Context context) {
        super(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mDensity = getResources().getDisplayMetrics().density;
        this.mFlingThresholdVelocity = (int) (500.0f * this.mDensity);
        this.mMinFlingVelocity = (int) (250.0f * this.mDensity);
        this.mMinSnapVelocity = (int) (1500.0f * this.mDensity);
        this.slidingPanelLayoutInterpolator = new SlidingPanelLayoutInterpolator(this);
        this.mIsRtl = isRtl(getResources());
    }

    public final void addContentView(View view) {
        contentView = view;
        super.addView(contentView);
    }

    public final void setPanelX(int _panelX) {
        if (_panelX <= 1) {
            _panelX = 0;
        }
        int measuredWidth = getMeasuredWidth();
        mPanelPositionRatio = ((float) _panelX) / ((float) measuredWidth);
        panelX = Math.max(Math.min(_panelX, measuredWidth), 0);
        contentView.setTranslationX(mIsRtl ? (float) (-this.panelX) : (float) this.panelX);
        if (enableAlpha)
            contentView.setAlpha(Math.max(0.1f, decelerateInterpolator.getInterpolation(mPanelPositionRatio)));
        if (dragListener != null) {
            dragListener.overlayScrollChanged(mPanelPositionRatio);
        }
    }

    public final void openPanel(int duration) {
        onPanelOpening();
        mSettling = true;
        slidingPanelLayoutInterpolator.animatorToX(getMeasuredWidth(), duration);
    }

    public final void closePanel(int duration) {
        if (DEBUG) {
            Log.d("wo.SlidingPanelLayout", "onPanelClosing, " + duration);
        }
        mIsPageMoving = true;
        if (dragListener != null) {
            dragListener.close(mTouchState == 1);
        }
        mSettling = true;
        slidingPanelLayoutInterpolator.animatorToX(0, duration);
    }

    public final void em(View view) {
        if (this.uoB != null) {
            super.removeView(this.uoB);
        }
        this.uoB = view;
        super.addView(this.uoB, 0);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (getChildCount() <= 0) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        acquireVelocityTrackerAndAddMovement(motionEvent);
        int action = motionEvent.getAction();
        if (action == MotionEvent.ACTION_MOVE && mTouchState == 1) {
            return true;
        }
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                boolean z;
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                if (DEBUG) {
                    String valueOf = String.valueOf(motionEvent);
                    Log.d("wo.SlidingPanelLayout", "Intercept touch down: " + valueOf);
                }
                mDownX = x;
                mDownY = y;
                uoF = (float) panelX;
                mLastMotionX = x;
                mTotalMotionX = 0.0f;
                mActivePointerId = motionEvent.getPointerId(0);
                int dx = Math.abs(slidingPanelLayoutInterpolator.mFinalX - panelX);
                z = slidingPanelLayoutInterpolator.isFinished() || dx < mTouchSlop / 3;
                if (!z || this.mForceDrag) {
                    this.mForceDrag = false;
                    startDrag();
                    this.uoE = x;
                    break;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                resetTouchState();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId != -1) {
                    determineScrollingStart(motionEvent, 1.0f);
                    break;
                }
                break;
            case 6: //ACTION_POINTER_UP
                onSecondaryPointerUp(motionEvent);
                releaseVelocityTracker();
                break;
        }
        return mTouchState != 0;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
//        super.onTouchEvent(motionEvent);
        if (this.contentView == null) {
            return super.onTouchEvent(motionEvent);
        }
        acquireVelocityTrackerAndAddMovement(motionEvent);
        float x;
        float y;
        int xVelocity;
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                boolean z;
                x = motionEvent.getX();
                y = motionEvent.getY();
                mDownX = x;
                mDownY = y;
                uoF = (float) panelX;
                mLastMotionX = x;
                mTotalMotionX = 0.0f;
                mActivePointerId = motionEvent.getPointerId(0);
                xVelocity = Math.abs(slidingPanelLayoutInterpolator.mFinalX - panelX);
                z = slidingPanelLayoutInterpolator.isFinished() || xVelocity < mTouchSlop / 3;
                if (z && !mForceDrag) {
                    return true;
                }
                mForceDrag = false;
                startDrag();
                uoE = x;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mTouchState != 1) {
                    return true;
                }
                this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                xVelocity = (int) mVelocityTracker.getXVelocity(mActivePointerId);
                boolean z2 = mTotalMotionX > 25.0f && Math.abs(xVelocity) > this.mFlingThresholdVelocity;
                if (z2) {
                    if (mIsRtl) {
                        xVelocity = -xVelocity;
                    }
                    if (Math.abs(xVelocity) < mMinFlingVelocity) {
                        if (xVelocity >= 0) {
                            openPanel(750);
                        } else {//Todo: this else was not there initially
                            closePanel(750);
                        }
                    } else {
                        float measuredWidth = ((float) (getMeasuredWidth() / 2)) + (((float) Math.sin((double) ((float) (((double) (Math.min(1.0f, (((float) (xVelocity < 0 ? this.panelX : getMeasuredWidth() - this.panelX)) * 1.0f) / ((float) getMeasuredWidth())) - 0.5f)) * 0.4712389167638204d)))) * ((float) (getMeasuredWidth() / 2)));
                        z2 = xVelocity > 0;
                        xVelocity = Math.round(Math.abs(measuredWidth / ((float) Math.max(mMinSnapVelocity, Math.abs(xVelocity)))) * 1000.0f) * 4;
                        if (z2) {
                            openPanel(xVelocity);
                        } else {
                            closePanel(xVelocity);
                        }
                    }
                } else {
                    if (panelX >= getMeasuredWidth() / 2) {
                        openPanel(750);
                    } else { //Todo: this else was not there initially
                        closePanel(750);
                    }
                }
                resetTouchState();
                return true;
            case MotionEvent.ACTION_MOVE:
                if (mTouchState == 1) {
                    int pIndex = motionEvent.findPointerIndex(mActivePointerId);
                    if (pIndex == -1) {
                        return true;
                    }
                    float moveX = motionEvent.getX(pIndex);
                    mTotalMotionX += Math.abs(moveX - mLastMotionX);
                    mLastMotionX = moveX;
                    moveX -= uoE;//deltaX
                    x = uoF;
                    if (mIsRtl) {
                        moveX = -moveX;
                    }
                    setPanelX((int) (moveX + x));//final/targetX
                    return true;
                }
                determineScrollingStart(motionEvent, 1.0f);
                return true;
            case MotionEvent.ACTION_POINTER_UP://6:
                onSecondaryPointerUp(motionEvent);
                releaseVelocityTracker();
                return true;
            default:
                return true;
        }
    }

    private void resetTouchState() {
        releaseVelocityTracker();
        this.mForceDrag = false;
        this.mTouchState = 0;
        this.mActivePointerId = -1;
    }

    protected void determineScrollingStart(MotionEvent motionEvent, float f) {
        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        if (findPointerIndex != -1) {
            float x = motionEvent.getX(findPointerIndex);
            if (((int) Math.abs(x - this.mDownX)) > Math.round(((float) this.mTouchSlop) * f)) {
                this.mTotalMotionX += Math.abs(this.mLastMotionX - x);
                this.uoE = x;
                this.mLastMotionX = x;
                startDrag();
            }
        }
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int action = (motionEvent.getAction() >> 8) & 255;
        if (motionEvent.getPointerId(action) == this.mActivePointerId) {
            action = action == 0 ? 1 : 0;
            float x = motionEvent.getX(action);
            this.uoE += x - this.mLastMotionX;
            this.mDownX = x;
            this.mLastMotionX = x;
            this.mActivePointerId = motionEvent.getPointerId(action);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void acquireVelocityTrackerAndAddMovement(MotionEvent motionEvent) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
            mVelocityTracker.clear();
        }
        mVelocityTracker.addMovement(motionEvent);
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    protected void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (uoB != null) {
            uoB.measure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(size2, MeasureSpec.EXACTLY));
        }
        if (contentView != null) {
            contentView.measure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(size2, MeasureSpec.EXACTLY));
        }
        setMeasuredDimension(size, size2);
        setPanelX((int) (((float) size) * this.mPanelPositionRatio));
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (uoB != null) {
            uoB.layout(0, 0, this.uoB.getMeasuredWidth(), this.uoB.getMeasuredHeight());
        }
        if (contentView != null) {
            int measuredWidth = contentView.getMeasuredWidth();
            int measuredHeight = contentView.getMeasuredHeight();
            int left = mIsRtl ? measuredWidth : -measuredWidth;
            if (mIsRtl) {
                measuredWidth *= 2;
            } else {
                measuredWidth = 0;
            }
            contentView.layout(left, 0, measuredWidth, measuredHeight);
        }
    }

    public static boolean isRtl(Resources resources) {
        return resources.getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    private final void startDrag() {
        mTouchState = 1;
        mIsPageMoving = true;
        mSettling = false;
        slidingPanelLayoutInterpolator.cancelAll();
        if (hardware) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        if (DEBUG) {
            Log.d("wo.SlidingPanelLayout", "onDragStarted");
        }
        if (dragListener != null) {
            dragListener.drag();
        }
    }

    public final void onPanelOpening() {
        if (DEBUG) {
            Log.d("wo.SlidingPanelLayout", "onPanelOpening");
        }
        mIsPageMoving = true;
        if (dragListener != null) {
            dragListener.dragTouchable();
        }
    }

    public final void onPanelOpened() {
        if (DEBUG) {
            Log.d("wo.SlidingPanelLayout", "onPanelOpened");
        }
        disableHardwareIfNeed();
        mIsPanelOpen = true;
        mIsPageMoving = false;
        if (dragListener != null) {
            dragListener.open();
        }
    }

    final void disableHardwareIfNeed() {
        if (hardware) {
            setLayerType(View.LAYER_TYPE_NONE, null);
        }
    }
}
