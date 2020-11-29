package com.cz.launcher.overlay.sample.scroll.widget;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.MotionEvent;

@SuppressLint("ViewConstructor")
public final class OverlayControllerSlidingPanelLayout extends SlidingPanelLayout {

    private final ScrollOverlayComponent overlayController;

    public OverlayControllerSlidingPanelLayout(ScrollOverlayComponent overlayControllerVar) {
        super(overlayControllerVar);
        overlayController = overlayControllerVar;
    }

    @Override
    protected final void determineScrollingStart(MotionEvent motionEvent, float f) {
        Object obj = 1;
        if (motionEvent.findPointerIndex(mActivePointerId) != -1) {
            float x = motionEvent.getX() - mDownX;
            float abs = Math.abs(x);
            float abs2 = Math.abs(motionEvent.getY() - mDownY);
            if (Float.compare(abs, 0.0f) != 0) {
                abs = (float) Math.atan((double) (abs2 / abs));
                Object obj2;
                if (mIsRtl) {
                    obj2 = x < 0.0f ? 1 : null;
                } else if (x > 0.0f) {
                    obj2 = 1;//TODO: different from source
                } else {
                    obj2 = null;
                }
                if (!mIsPanelOpen || mIsPageMoving) {
                    obj = null;
                }
                if (obj != null && obj2 != null) {//TODO: different from source
                    return;
                }
                if ((obj != null && dragListener.cnI()) || abs > 1.0471976f) {
                    return;
                }
                if (abs > 0.5235988f) {
                    super.determineScrollingStart(motionEvent, (((float) Math.sqrt((double) ((abs - 0.5235988f) / 0.5235988f))) * 4.0f) + 1.0f);
                } else {
                    super.determineScrollingStart(motionEvent, f);
                }
            }
        }
    }

    @Override
    protected final boolean fitSystemWindows(Rect rect) {
        return !overlayController.unZ || super.fitSystemWindows(rect);
    }
}
