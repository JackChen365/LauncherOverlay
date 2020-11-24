package com.cz.launcher.overlay.library.scroll;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILauncherScrollOverlayCallback extends IInterface {
    void overlayScrollChanged(float progress) throws RemoteException;

    void overlayStatusChanged(int status) throws RemoteException;

    abstract class Stub extends Binder implements ILauncherScrollOverlayCallback {
        static final int OVERLAY_SCROLL_CHANGED_TRANSACTION = 1;
        static final int OVERLAY_STATUS_CHANGED_TRANSACTION = 2;

        public Stub() {
            attachInterface(this, ILauncherScrollOverlayCallback.class.getName());
        }

        public static ILauncherScrollOverlayCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }

            IInterface iin = obj.queryLocalInterface(ILauncherScrollOverlayCallback.class.getName());
            if (iin != null && iin instanceof ILauncherScrollOverlayCallback) {
                return (ILauncherScrollOverlayCallback) iin;
            } else {
                return new Proxy(obj);
            }
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION:
                    reply.writeString(ILauncherScrollOverlay.class.getName());
                    return true;
                case OVERLAY_SCROLL_CHANGED_TRANSACTION:
                    data.enforceInterface(ILauncherScrollOverlayCallback.class.getName());
                    overlayScrollChanged(data.readFloat());
                    return true;
                case OVERLAY_STATUS_CHANGED_TRANSACTION:
                    data.enforceInterface(ILauncherScrollOverlayCallback.class.getName());
                    overlayStatusChanged(data.readInt());
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ILauncherScrollOverlayCallback {
            private IBinder mRemote;

            public Proxy(IBinder remote) {
                mRemote = remote;
            }

            public IBinder asBinder() {
                return mRemote;
            }

            public void overlayScrollChanged(float progress) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherScrollOverlayCallback.class.getName());
                    _data.writeFloat(progress);

                    mRemote.transact(OVERLAY_SCROLL_CHANGED_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            public void overlayStatusChanged(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherScrollOverlayCallback.class.getName());
                    _data.writeInt(status);

                    mRemote.transact(OVERLAY_STATUS_CHANGED_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }
        }

    }
}
