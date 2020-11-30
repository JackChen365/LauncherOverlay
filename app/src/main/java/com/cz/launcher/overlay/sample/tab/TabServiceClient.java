package com.cz.launcher.overlay.sample.tab;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.WindowManager;

import com.cz.launcher.overlay.library.fixed.ILauncherFixedOverlay;
import com.cz.launcher.overlay.library.fixed.ILauncherFixedOverlayCallback;
import com.cz.launcher.overlay.sample.fixed.service.LauncherOverlayService;

public class TabServiceClient {
    private static final String TAG="TabServiceClient";
    private static final String SERVER_PACKAGE_NAME="com.cz.launcher.overlay.sample";
    private static AppServiceConnection applicationConnection;

    private final Activity hostActivity;
    private OverlayCallbacks currentCallbacks;
    private ILauncherFixedOverlay overlay;
    private OverlayServiceConnection serviceConnection;
    private final Intent serviceIntent;
    private WindowManager.LayoutParams windowAttrs;
    private boolean isDestroyed;
    private boolean isResumed;

    public TabServiceClient(Activity activity,String action,String serviceClassName) {
        isResumed = false;
        isDestroyed = false;
        hostActivity = activity;
        serviceIntent = new Intent(action).setClassName(SERVER_PACKAGE_NAME,serviceClassName).setPackage(SERVER_PACKAGE_NAME);
        serviceConnection = new OverlayServiceConnection();
        windowAttrs =activity.getWindow().getAttributes();
    }

    private void applyWindowToken() {
        if (overlay == null) {
            return;
        }
        try {
            if (currentCallbacks == null) {
                currentCallbacks = new OverlayCallbacks();
            }
            overlay.onWindowAttached(windowAttrs, currentCallbacks, 0);
            if (isResumed) {
                overlay.onResume();
            } else {
                overlay.onPause();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private boolean connectSafely(Context context, ServiceConnection conn, int flags) {
        try {
            return context.bindService(serviceIntent, conn, flags | Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            Log.e(TAG, "Unable to connect to overlay service");
            return false;
        }
    }

    public boolean isConnected() {
        return overlay != null;
    }

    public boolean isVisible(){
        try {
            if(isConnected()){
                return overlay.isVisible();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void show(){
        if(isConnected()){
            try {
                overlay.show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void hide(){
        if(isConnected()){
            try {
                overlay.hide();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect(){
        if(isConnected()){
            if(null!=applicationConnection){
                try {
                    hostActivity.getApplicationContext().unbindService(applicationConnection);
                } catch (IllegalArgumentException e){
                    Log.w(TAG,"unbindService failed.");
                }
            }
            if (null!=serviceConnection) {
                hostActivity.unbindService(serviceConnection);
            }
        }
    }

    public void reconnect() {
        if (isDestroyed) {
            return;
        }

        if (applicationConnection != null && !applicationConnection.packageName.equals(serviceIntent.getPackage())) {
            hostActivity.getApplicationContext().unbindService(applicationConnection);
        }

        if (applicationConnection == null) {
            applicationConnection = new AppServiceConnection(serviceIntent.getPackage());
            if (!connectSafely(hostActivity.getApplicationContext(), applicationConnection, Context.BIND_WAIVE_PRIORITY)) {
                applicationConnection = null;
            }
        }

        if (applicationConnection != null) {
            connectSafely(hostActivity, serviceConnection, Context.BIND_ADJUST_WITH_ACTIVITY);
        }
    }

    final class AppServiceConnection implements ServiceConnection {
        public final String packageName;

        public AppServiceConnection(String packageName) {
            this.packageName = packageName;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"onServiceConnected:"+name);
        }

        public void onServiceDisconnected(ComponentName name) {
            if (name.getPackageName().equals(packageName)) {
                applicationConnection = null;
            }
        }
    }

    private static class OverlayCallbacks extends ILauncherFixedOverlayCallback.Stub {
        public OverlayCallbacks() {
        }
    }

    private class OverlayServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder service) {
            overlay = ILauncherFixedOverlay.Stub.asInterface(service);
            if (windowAttrs != null) {
                applyWindowToken();
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            overlay = null;
        }
    }


}
