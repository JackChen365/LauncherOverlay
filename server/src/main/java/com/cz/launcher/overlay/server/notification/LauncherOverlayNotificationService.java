package com.cz.launcher.overlay.server.notification;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;

import com.cz.launcher.overlay.server.R;
import com.cz.launcher.overlay.server.common.LauncherOverlayComponent;
import com.cz.launcher.overlay.server.home.HomeOverlay;

/**
 * @author Created by cz
 * @date 11/20/20 10:39 AM
 * @email bingo110@126.com
 */
public class LauncherOverlayNotificationService extends Service {
    private LauncherOverlayComponent launcherOverlayComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Context baseContext = getBaseContext();
        Configuration configuration = baseContext.getResources().getConfiguration();
        Context localContext = baseContext.createConfigurationContext(configuration);
        NotificationOverlay overlay = new NotificationOverlay(localContext, R.style.AppTheme);
        launcherOverlayComponent = new LauncherOverlayComponent(this,overlay);
    }

    @Override
    public void onDestroy() {
        launcherOverlayComponent.destroy();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return launcherOverlayComponent.bindService(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        launcherOverlayComponent.unbind(intent);
        return true;
    }
}
