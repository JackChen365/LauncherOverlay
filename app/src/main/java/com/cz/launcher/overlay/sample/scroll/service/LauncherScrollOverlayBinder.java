package com.cz.launcher.overlay.sample.scroll.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.view.WindowManager;

import com.cz.launcher.overlay.library.scroll.ILauncherScrollOverlay;
import com.cz.launcher.overlay.library.scroll.ILauncherScrollOverlayCallback;

public class LauncherScrollOverlayBinder extends ILauncherScrollOverlay.Stub{
    private final Handler dispatcher;
    final String clientPackage;

    public LauncherScrollOverlayBinder(LauncherScrollOverlayComponent overlayComponent, String clientPackage) {
        this.clientPackage = clientPackage;
        this.dispatcher  = new Handler(Looper.getMainLooper(),new LauncherScrollOverlayCallback(overlayComponent));
    }
    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    public void onPause() {
        dispatchMessage(Message.obtain(dispatcher, LauncherScrollOverlayCallback.MSG_ON_PAUSE));
    }

    @Override
    public void onStop() {
        dispatchMessage(Message.obtain(dispatcher, LauncherScrollOverlayCallback.MSG_CLOSE_OVERLAY));
    }

    @Override
    public void onResume() {
        dispatchMessage(Message.obtain(dispatcher, LauncherScrollOverlayCallback.MSG_ON_RESUME));
    }

    public void onDestroy(){
        dispatchMessage(Message.obtain(dispatcher, LauncherScrollOverlayCallback.MSG_ON_DESTROY));
    }

    @Override
    public void openOverlay(int options) {
        dispatchMessage(Message.obtain(dispatcher, LauncherScrollOverlayCallback.MSG_OPEN_OVERLAY));
    }

    @Override
    public void closeOverlay(int options) {
        dispatchMessage(Message.obtain(dispatcher, LauncherScrollOverlayCallback.MSG_CLOSE_OVERLAY));
    }

    @Override
    public void onStart() {
        dispatchMessage(Message.obtain(dispatcher, LauncherScrollOverlayCallback.MSG_CLOSE_OVERLAY));
    }


    @Override
    public void startScroll() throws RemoteException {
        dispatchMessage(Message.obtain(dispatcher, LauncherScrollOverlayCallback.MSG_START_SCROLL));
    }

    @Override
    public void onScroll(float progress) throws RemoteException {
        Message newMessage = Message.obtain(dispatcher, LauncherScrollOverlayCallback.MSG_ON_SCROLL);
        newMessage.obj = progress;
        dispatchMessage(newMessage);
    }

    @Override
    public void endScroll() throws RemoteException {
        dispatchMessage(Message.obtain(dispatcher, LauncherScrollOverlayCallback.MSG_END_SCROLL));
    }

    @Override
    public void onWindowAttached(WindowManager.LayoutParams attrs, ILauncherScrollOverlayCallback callback, int option) throws RemoteException {
        Message newMessage = Message.obtain(dispatcher, LauncherScrollOverlayCallback.MSG_ATTACH_TO_WINDOW);
        Bundle data=new Bundle();
        data.putParcelable("attrs", attrs);
        newMessage.setData(data);
        newMessage.obj = callback;
        dispatchMessage(newMessage);
    }

    @Override
    public void onWindowDetached(boolean isChangingConfigurations) {
        dispatchMessage(Message.obtain(dispatcher, LauncherScrollOverlayCallback.MSG_DETACH_FROM_WINDOW));
    }

    private void dispatchMessage(Message message){
        dispatcher.sendMessage(message);
    }
}
