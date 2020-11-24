package com.cz.launcher.overlay.sample.scroll;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.WindowManager;

import com.cz.launcher.overlay.library.fixed.ILauncherFixedOverlay;
import com.cz.launcher.overlay.library.fixed.ILauncherFixedOverlayCallback;
import com.cz.launcher.overlay.library.scroll.ILauncherScrollOverlay;
import com.cz.launcher.overlay.library.scroll.ILauncherScrollOverlayCallback;
import com.cz.launcher.overlay.sample.fixed.service.LauncherOverlayService;
import com.cz.launcher.overlay.sample.scroll.service.LauncherScrollOverlayService;

public class ScrollServiceClient {
    private static final String TAG=ScrollServiceClient.class.getSimpleName();
    private static AppServiceConnection applicationConnection;
    private final Activity hostActivity;
    private OverlayCallbacks currentCallbacks;
    private ILauncherScrollOverlay overlay;
    private OverlayServiceConnection serviceConnection;
    private final Intent serviceIntent;
    private WindowManager.LayoutParams windowAttrs;
    private boolean isDestroyed;
    private boolean isResumed;

    public ScrollServiceClient(Activity activity) {
        isResumed = false;
        isDestroyed = false;
        hostActivity = activity;
        serviceIntent = new Intent(activity, LauncherScrollOverlayService.class);
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
            Log.e("DrawerOverlayClient", "Unable to connect to overlay service");
            return false;
        }
    }

    public void endMove() {
        if (!isConnected()) {
            return;
        }
        try {
            overlay.endScroll();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void hideOverlay(boolean animate) {
        if (overlay == null) {
            return;
        }
        try {
            overlay.closeOverlay(animate ? 1 : 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void onAttachedToWindow() {
        if (isDestroyed) {
            return;
        }
        windowAttrs = hostActivity.getWindow().getAttributes();
    }

    public void onDestroy() {
    }

    public final void onDetachedFromWindow() {
        if (isDestroyed) {
            return;
        }
        overlay = null;
        windowAttrs = null;
    }

    public void onStart() {
        if (isDestroyed) {
            return;
        }
        if (overlay != null && windowAttrs != null) {
            try {
                overlay.onStart();
            } catch (RemoteException ignored) {
            }
        }
    }

    public void onStop() {
        if (isDestroyed) {
            return;
        }
        if (overlay != null && windowAttrs != null) {
            try {
                overlay.onStop();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onPause() {
        if (isDestroyed) {
            return;
        }
        isResumed = false;
        if (overlay != null && windowAttrs != null) {
            try {
                overlay.onPause();
            } catch (RemoteException ignored) {
            }
        }
    }

    public void onResume() {
        if (isDestroyed) {
            return;
        }
        reconnect();
        isResumed = true;
        if (overlay != null && windowAttrs != null) {
            try {
                overlay.onResume();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void startScroll() {
        if (!isConnected()) {
            return;
        }
        try {
            overlay.startScroll();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void scroll(float progress) {
        if (!isConnected()) {
            return;
        }
        try {
            Log.i(TAG,"scroll:"+progress);
            overlay.onScroll(progress);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopScroll() {
        if (!isConnected()) {
            return;
        }
        try {
            overlay.endScroll();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private boolean isConnected() {
        return overlay != null;
    }

    public void reconnect() {
        if (isDestroyed) {
            return;
        }

        if (null != applicationConnection && null!=applicationConnection.packageName && !applicationConnection.packageName.equals(serviceIntent.getPackage())) {
            hostActivity.getApplicationContext().unbindService(applicationConnection);
        }

        if (null == applicationConnection) {
            applicationConnection = new AppServiceConnection(serviceIntent.getPackage());
            if (!connectSafely(hostActivity.getApplicationContext(), applicationConnection, Context.BIND_WAIVE_PRIORITY)) {
                applicationConnection = null;
            }
        }

        if (null != applicationConnection) {
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

    private static class OverlayCallbacks extends ILauncherScrollOverlayCallback.Stub {
        public OverlayCallbacks() {
        }

        @Override
        public void overlayScrollChanged(float progress) throws RemoteException {
        }

        @Override
        public void overlayStatusChanged(int status) throws RemoteException {
        }
    }

    private class OverlayServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder service) {
            overlay = ILauncherScrollOverlay.Stub.asInterface(service);
            if (windowAttrs != null) {
                applyWindowToken();
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            overlay = null;
        }
    }


}
