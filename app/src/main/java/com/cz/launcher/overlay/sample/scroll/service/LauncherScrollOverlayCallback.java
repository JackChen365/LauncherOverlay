package com.cz.launcher.overlay.sample.scroll.service;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.cz.launcher.overlay.library.scroll.ILauncherScrollOverlayCallback;
import com.cz.launcher.overlay.sample.scroll.widget.ScrollOverlayComponent;
import com.cz.launcher.overlay.sample.scroll.widget.SlidingPanelLayout;

/**
 * @author Created by cz
 * @date 11/20/20 11:39 AM
 * @email bingo110@126.com
 */
public class LauncherScrollOverlayCallback implements Handler.Callback {
    private static final String TAG= ILauncherScrollOverlayCallback.class.getSimpleName();
    public static final int MSG_OPEN_OVERLAY=1;
    public static final int MSG_CLOSE_OVERLAY=2;
    public static final int MSG_ATTACH_TO_WINDOW = 3;
    public static final int MSG_DETACH_FROM_WINDOW = 4;
    public static final int MSG_ON_CREATE = 5;
    public static final int MSG_ON_START = 6;
    public static final int MSG_ON_PAUSE = 7;
    public static final int MSG_ON_RESUME = 8;
    public static final int MSG_ON_STOP = 9;
    public static final int MSG_ON_DESTROY = 10;
    public static final int MSG_START_SCROLL = 11;
    public static final int MSG_END_SCROLL = 12;
    public static final int MSG_ON_SCROLL = 13;
    private LauncherScrollOverlayComponent launcherOverlayComponent;
    private ScrollOverlayComponent overlayComponent;

    public LauncherScrollOverlayCallback(LauncherScrollOverlayComponent overlayComponent) {
        this.launcherOverlayComponent = overlayComponent;
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        switch (message.what){
            case MSG_ATTACH_TO_WINDOW:
                Bundle bundle = null;
                if (overlayComponent != null) {
                    bundle = new Bundle();
                    if (overlayComponent.window != null) {
                        bundle.putParcelable("view_state", overlayComponent.window.saveHierarchyState());
                    }
                    overlayComponent.onDestroy();
                    overlayComponent = null;
                }
                overlayComponent = launcherOverlayComponent.createOverlayComponent();
                try {
                    Bundle data = message.getData();
                    WindowManager.LayoutParams layoutParams=data.getParcelable("attrs");
                    overlayComponent.onPreCreated(layoutParams);
                    //redirect this message.
                    Message newMessage = Message.obtain(message);
                    newMessage.what = MSG_ON_CREATE;
                    if(null != bundle){
                        newMessage.setData(bundle);
                    }
                    newMessage.sendToTarget();
                } catch (Exception e) {
                    Log.w(TAG,e.getMessage());
                }
                break;
            case MSG_ON_CREATE:
                Bundle data = message.getData();
                if (data != null&&overlayComponent.window != null) {
                    Bundle viewState = data.getBundle("view_state");
                    if(null!=viewState){
                        overlayComponent.window.restoreHierarchyState(viewState);
                    }
                }
                overlayComponent.onCreate(data);
                break;
            case MSG_ON_START:
                if(null!=overlayComponent){
                    overlayComponent.onStart();
                }
                break;
            case MSG_ON_PAUSE:
                if(null!=overlayComponent){
                    overlayComponent.onPause();
                }
                break;
            case MSG_ON_RESUME:
                if(null!=overlayComponent){
                    overlayComponent.onResume();
                }
                break;
            case MSG_ON_STOP:
                if(null!=overlayComponent){
                    overlayComponent.onStop();
                }
                break;
            case MSG_DETACH_FROM_WINDOW:
            case MSG_ON_DESTROY:
                if(null!=overlayComponent){
                    overlayComponent.onDestroy();
                }
                break;
            case MSG_START_SCROLL:
                if(null!=overlayComponent){
                    overlayComponent.onStartScroll();
                }
                break;
            case MSG_END_SCROLL:
                if(null!=overlayComponent){
                    overlayComponent.onEndScroll();
                }
                break;
            case MSG_ON_SCROLL:
                if(null!=overlayComponent){
                    float progress= (float) message.obj;
                    overlayComponent.onScroll(progress);
                }
                break;
        }
        return true;
    }

    public final void dispatchTouchEvent(int action, int x, long eventTime) {
        MotionEvent obtain = MotionEvent.obtain(SystemClock.uptimeMillis(), eventTime, action, (float) x, 10.0f, 0);
        obtain.setSource(InputDevice.SOURCE_TOUCHSCREEN);//4098
        SlidingPanelLayout slidingPanelLayout = overlayComponent.getSlidingPanelLayout();
        slidingPanelLayout.dispatchTouchEvent(obtain);
        obtain.recycle();
    }

}
