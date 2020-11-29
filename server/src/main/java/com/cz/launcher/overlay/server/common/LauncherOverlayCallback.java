package com.cz.launcher.overlay.server.common;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.cz.launcher.overlay.library.component.OverlayComponent;
import com.cz.launcher.overlay.library.fixed.ILauncherFixedOverlayCallback;

/**
 * @author Created by cz
 * @date 11/20/20 11:39 AM
 * @email bingo110@126.com
 */
public class LauncherOverlayCallback implements Handler.Callback {
    private static final String TAG=LauncherOverlayCallback.class.getSimpleName();
    public static final int MSG_SHOW_OVERLAY=1;
    public static final int MSG_HIDE_OVERLAY=2;
    public static final int MSG_ATTACH_TO_WINDOW = 3;
    public static final int MSG_DETACH_FROM_WINDOW = 4;
    public static final int MSG_ON_CREATE = 5;
    public static final int MSG_ON_START = 6;
    public static final int MSG_ON_PAUSE = 7;
    public static final int MSG_ON_RESUME = 8;
    public static final int MSG_ON_STOP = 9;
    public static final int MSG_ON_DESTROY = 10;
    public static final int MSG_IS_VISIBLE=11;
    private LauncherOverlayComponent launcherOverlayComponent;
    private OverlayComponent overlayComponent;

    public LauncherOverlayCallback(LauncherOverlayComponent overlayComponent) {
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
                ILauncherFixedOverlayCallback callbacks = (ILauncherFixedOverlayCallback) message.obj;
                if (data != null&&overlayComponent.window != null) {
                    overlayComponent.window.restoreHierarchyState(data.getBundle("view_state"));
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
            case MSG_SHOW_OVERLAY:
                if(null!=overlayComponent){
                    overlayComponent.show();
                }
                break;
            case MSG_HIDE_OVERLAY:
                if(null!=overlayComponent){
                    overlayComponent.hide();
                }
                break;
        }
        return true;
    }
}
