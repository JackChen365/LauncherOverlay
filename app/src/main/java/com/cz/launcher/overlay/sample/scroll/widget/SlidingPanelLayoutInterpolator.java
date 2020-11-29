package com.cz.launcher.overlay.sample.scroll.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.animation.Interpolator;

final class SlidingPanelLayoutInterpolator extends AnimatorListenerAdapter implements Interpolator {

    private ObjectAnimator mAnimator;
    int mFinalX;
    private final SlidingPanelLayout slidingPanelLayout;

    public SlidingPanelLayoutInterpolator(SlidingPanelLayout slidingPanelLayoutVar) {
        this.slidingPanelLayout = slidingPanelLayoutVar;
    }

    public final void cancelAll() {
        if (this.mAnimator != null) {
            this.mAnimator.removeAllListeners();
            this.mAnimator.cancel();
        }
    }

    public final void animatorToX(int finalX, int duration) {
        cancelAll();
        mFinalX = finalX;
        if (duration > 0) {
            this.mAnimator = ObjectAnimator.ofInt(slidingPanelLayout, SlidingPanelLayout.PANEL_X, new int[]{finalX})
                    .setDuration((long) duration);
            this.mAnimator.setInterpolator(this);
            this.mAnimator.addListener(this);
            this.mAnimator.start();
        } else {
            onAnimationEnd(null);
        }
    }

    public final boolean isFinished() {
        return mAnimator == null;
    }

    public final void onAnimationEnd(Animator animator) {
        mAnimator = null;
        slidingPanelLayout.setPanelX(this.mFinalX);
        SlidingPanelLayout panelLayout = slidingPanelLayout;
        if (panelLayout.mSettling) {
            panelLayout.mSettling = false;
            if (panelLayout.panelX == 0) {
                if (SlidingPanelLayout.DEBUG) {
                    Log.d("wo.SlidingPanelLayout", "onPanelClosed");
                }
                panelLayout.disableHardwareIfNeed();
                panelLayout.mIsPanelOpen = false;
                panelLayout.mIsPageMoving = false;
                if (panelLayout.dragListener != null) {
                    panelLayout.dragListener.close();
                }
            } else if (panelLayout.panelX == panelLayout.getMeasuredWidth()) {
                panelLayout.onPanelOpened();
            }
        }
    }

    public final float getInterpolation(float f) {
        float f2 = f - 1.0f;
        return (f2 * (((f2 * f2) * f2) * f2)) + 1.0f;
    }
}
