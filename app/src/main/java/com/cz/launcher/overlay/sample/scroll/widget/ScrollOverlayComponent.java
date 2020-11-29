package com.cz.launcher.overlay.sample.scroll.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.InputDevice;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.cz.launcher.overlay.library.component.OverlayComponent;
import com.cz.launcher.overlay.library.scroll.ILauncherScrollOverlayCallback;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

public class ScrollOverlayComponent extends OverlayComponent {
    private static final String TAG="OverlayComponent";
    private SlidingPanelLayout slidingPanelLayout;
    public ILauncherScrollOverlayCallback callback;
    DragListener overlayControllerStateChanger = new OverlayControllerStateChanger(this);
    PanelState panelState = PanelState.CLOSED;
    int eventX = 0;
    long downTime = 0;
    boolean acceptExternalMove = false;
    boolean unZ = true;

    public ScrollOverlayComponent(Context context, int theme) {
        super(context, theme);
    }

    public void setState(PanelState state) {
        if (state != panelState) {
            panelState = state;
        }
    }

    @Override
    protected void setContentView(int layoutId) {
        slidingPanelLayout = new OverlayControllerSlidingPanelLayout(this);
        View contentView = LayoutInflater.from(this).inflate(layoutId, slidingPanelLayout, false);
        slidingPanelLayout.addContentView(contentView);
        slidingPanelLayout.dragListener = overlayControllerStateChanger;
        window.setContentView(slidingPanelLayout);

        decorView = window.getDecorView();
        decorView.setFitsSystemWindows(true);
        windowManager.addView(decorView, window.getAttributes());
        setTouchable(false);
    }

    public SlidingPanelLayout getSlidingPanelLayout() {
        return slidingPanelLayout;
    }

    public void onStartScroll(){
        if (!isPanelOpen()) {
            if (slidingPanelLayout.panelX < slidingPanelLayout.mTouchSlop) {
                eventX = 0;
                acceptExternalMove = true;
                slidingPanelLayout.setPanelX(0);
                slidingPanelLayout.mForceDrag = true;
                dispatchTouchEvent(ACTION_DOWN, eventX, SystemClock.uptimeMillis());
                dispatchTouchEvent(ACTION_MOVE, eventX, SystemClock.uptimeMillis());
            }
        }
    }

    public void onEndScroll(){
        if (acceptExternalMove) {
            dispatchTouchEvent(ACTION_UP, eventX, SystemClock.uptimeMillis());
        }
        acceptExternalMove = false;
    }

    public void onScroll(float progress){
        if (acceptExternalMove) {
            eventX = (int) (progress * ((float) slidingPanelLayout.getMeasuredWidth()));
            dispatchTouchEvent(ACTION_MOVE, eventX, SystemClock.uptimeMillis());
        }
    }

    public boolean isPanelOpen() {
        return panelState == PanelState.OPEN_AS_LAYER || panelState == PanelState.OPEN_AS_DRAWER;
    }

    @Override
    public void onBackPressed() {
        closeOverlay(1);
    }

    public void closeOverlay(int value) {
        int i2 = 1;
        int duration = 0;
        if (isPanelOpen()) {
            int i4 = (value & 1) != 0 ? 1 : 0;
            if (panelState == PanelState.OPEN_AS_LAYER) {
                i2 = 0;
            }
            i4 &= i2;
            if (i4 != 0) {
                duration = 750;
            }
            slidingPanelLayout.closePanel(duration);
        }
    }

    // param is 1 or 0
    public final void openOverlay(int value) {
        int i2 = 0;
        if (this.panelState == PanelState.CLOSED) {
            int i3 = (value & 1) != 0 ? 1 : 0;
            if ((value & 2) != 0) {
                slidingPanelLayout.dragListener   = new TransparentOverlayController(this);
                i3 = 0;
            }
            if (i3 != 0) {
                i2 = 750;
            }
            slidingPanelLayout.openPanel(i2);
        }
    }

    public final void dispatchTouchEvent(int action, int x, long eventTime) {
        MotionEvent obtain = MotionEvent.obtain(downTime, eventTime, action, (float) x, 10.0f, 0);
        obtain.setSource(InputDevice.SOURCE_TOUCHSCREEN);
        slidingPanelLayout.dispatchTouchEvent(obtain);
        obtain.recycle();
    }


}
