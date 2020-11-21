package com.cz.launcher.overlay.sample.fixed.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.ArrayMap;

import com.cz.launcher.overlay.library.component.OverlayComponent;
import com.cz.launcher.overlay.sample.fixed.component.ListSampleOverlay;

public class LauncherOverlayComponent {
    private static final String TAG=LauncherOverlayComponent.class.getSimpleName();
    private final Context context;
    private final ArrayMap<String,LauncherOverlayFixedBinder> clients = new ArrayMap<>();

    public LauncherOverlayComponent(Service service) {
        context = service;
    }

    public IBinder bindService(Intent intent) {
        // app://a.b.c:uid?v=0
        ComponentName component = intent.getComponent();
        String packageName = component.getPackageName();
        LauncherOverlayFixedBinder binder = clients.get(packageName);
        if (binder != null && packageName.equals(binder.clientPackage)) {
            binder.onDestroy();
            binder = null;
        }
        if (binder == null) {   //create new if need.
            binder = new LauncherOverlayFixedBinder(this, packageName);
            clients.put(packageName, binder);
        }
        return binder;
    }

    public synchronized void unbind(Intent intent) {
        if (intent.getData() == null) return;
        int port = intent.getData().getPort();
        if (port == -1) return;
        LauncherOverlayFixedBinder client = clients.get(port);
        if (client != null) {
            client.onDestroy();
            clients.remove(port);
        }
    }

    public void destroy() {
        for (int size = this.clients.size() - 1; size >= 0; size--) {
            LauncherOverlayFixedBinder binder = clients.valueAt(size);
            if (binder != null) {
                binder.onDestroy();
            }
        }
        clients.clear();
    }

    final OverlayComponent createOverlayComponent() {
        Configuration configuration = context.getResources().getConfiguration();
        Context localContext = context.createConfigurationContext(configuration);
        return new ListSampleOverlay(localContext, 0);
    }
}
