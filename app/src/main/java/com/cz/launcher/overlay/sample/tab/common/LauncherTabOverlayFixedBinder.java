package com.cz.launcher.overlay.sample.tab.common;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.view.WindowManager;

import com.cz.launcher.overlay.library.component.OverlayComponent;
import com.cz.launcher.overlay.library.fixed.ILauncherFixedOverlay;
import com.cz.launcher.overlay.library.fixed.ILauncherFixedOverlayCallback;

public class LauncherTabOverlayFixedBinder extends ILauncherFixedOverlay.Stub{
    private final Handler dispatcher;
    private final OverlayComponent overlayComponent;
    final String clientPackage;

    public LauncherTabOverlayFixedBinder(LauncherTabOverlayComponent launcherOverlayComponent, String clientPackage) {
        this.clientPackage = clientPackage;
        this.overlayComponent = launcherOverlayComponent.createOverlayComponent();
        this.dispatcher  = new Handler(Looper.getMainLooper(),new LauncherTabOverlayCallback(overlayComponent));
    }
    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    public void onPause() {
        dispatchMessage(Message.obtain(dispatcher, LauncherTabOverlayCallback.MSG_ON_PAUSE));
    }

    @Override
    public void onStop() {
        dispatchMessage(Message.obtain(dispatcher, LauncherTabOverlayCallback.MSG_ON_STOP));
    }

    @Override
    public void show() throws RemoteException {
        dispatchMessage(Message.obtain(dispatcher, LauncherTabOverlayCallback.MSG_SHOW_OVERLAY));
    }

    @Override
    public void hide() throws RemoteException {
        dispatchMessage(Message.obtain(dispatcher, LauncherTabOverlayCallback.MSG_HIDE_OVERLAY));
    }

    @Override
    public boolean isVisible() throws RemoteException {
        return overlayComponent.isVisible();
    }

    @Override
    public void onResume() {
        dispatchMessage(Message.obtain(dispatcher, LauncherTabOverlayCallback.MSG_ON_RESUME));
    }

    public void onDestroy(){
        dispatchMessage(Message.obtain(dispatcher, LauncherTabOverlayCallback.MSG_ON_DESTROY));
    }

    @Override
    public void onStart() {
        dispatchMessage(Message.obtain(dispatcher, LauncherTabOverlayCallback.MSG_ON_START));
    }

    @Override
    public void onWindowAttached(WindowManager.LayoutParams attrs, ILauncherFixedOverlayCallback callbacks, int options) throws RemoteException {
        Message newMessage = Message.obtain(dispatcher, LauncherTabOverlayCallback.MSG_ATTACH_TO_WINDOW);
        Bundle data=new Bundle();
        data.putParcelable("attrs", attrs);
        newMessage.setData(data);
        newMessage.obj = callbacks;
        dispatchMessage(newMessage);
    }

    @Override
    public void onWindowDetached(boolean isChangingConfigurations) {
        dispatchMessage(Message.obtain(dispatcher, LauncherTabOverlayCallback.MSG_DETACH_FROM_WINDOW));
    }

    private void dispatchMessage(Message message){
        dispatcher.sendMessage(message);
    }
}
