package com.cz.launcher.overlay.library;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILauncherFixedOverlayCallback extends IInterface {

    abstract class Stub extends Binder implements ILauncherFixedOverlayCallback {

        public Stub() {
            attachInterface(this, ILauncherFixedOverlayCallback.class.getName());
        }

        public static ILauncherFixedOverlayCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }

            IInterface iin = obj.queryLocalInterface(ILauncherFixedOverlayCallback.class.getName());
            if (iin != null && iin instanceof ILauncherFixedOverlayCallback) {
                return (ILauncherFixedOverlayCallback) iin;
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
                    reply.writeString(ILauncherFixedOverlay.class.getName());
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ILauncherFixedOverlayCallback {
            private IBinder mRemote;

            public Proxy(IBinder remote) {
                mRemote = remote;
            }

            public IBinder asBinder() {
                return mRemote;
            }
        }

    }
}
