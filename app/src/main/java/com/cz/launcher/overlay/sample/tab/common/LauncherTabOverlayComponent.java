package com.cz.launcher.overlay.sample.tab.common;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.ArrayMap;

import com.cz.launcher.overlay.library.component.OverlayComponent;

public class LauncherTabOverlayComponent {
    private final Context context;
    private final OverlayComponent overlayComponent;
    private final ArrayMap<String, LauncherTabOverlayFixedBinder> clients = new ArrayMap<>();

    public LauncherTabOverlayComponent(Service service, OverlayComponent overlayComponent) {
        this.context = service;
        this.overlayComponent = overlayComponent;
    }

    public IBinder bindService(Intent intent) {
        // app://a.b.c:uid?v=0
        ComponentName component = intent.getComponent();
        String packageName = component.getPackageName();
        LauncherTabOverlayFixedBinder binder = clients.get(packageName);
        if (binder != null && packageName.equals(binder.clientPackage)) {
            binder.onDestroy();
            binder = null;
        }
        if (binder == null) {   //create new if need.
            binder = new LauncherTabOverlayFixedBinder(this, packageName);
            clients.put(packageName, binder);
        }
        return binder;
    }

    public synchronized void unbind(Intent intent) {
        if (intent.getData() == null) return;
        int port = intent.getData().getPort();
        if (port == -1) return;
        LauncherTabOverlayFixedBinder client = clients.get(port);
        if (client != null) {
            client.onDestroy();
            clients.remove(port);
        }
    }

    public void destroy() {
        for (int size = this.clients.size() - 1; size >= 0; size--) {
            LauncherTabOverlayFixedBinder binder = clients.valueAt(size);
            if (binder != null) {
                binder.onDestroy();
            }
        }
        clients.clear();
    }

    final OverlayComponent createOverlayComponent() {
        return overlayComponent;
    }
}
