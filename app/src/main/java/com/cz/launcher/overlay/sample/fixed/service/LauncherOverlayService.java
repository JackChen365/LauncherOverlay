package com.cz.launcher.overlay.sample.fixed.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author Created by cz
 * @date 11/20/20 10:39 AM
 * @email bingo110@126.com
 */
public class LauncherOverlayService extends Service {
    private LauncherOverlayComponent launcherOverlayComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        launcherOverlayComponent = new LauncherOverlayComponent(this);
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
