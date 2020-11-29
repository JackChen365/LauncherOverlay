package com.cz.launcher.overlay.library.tab;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.cz.launcher.overlay.library.fixed.ILauncherFixedOverlay;

public interface ILauncherTabOverlayCallback extends IInterface {

    abstract class Stub extends Binder implements ILauncherTabOverlayCallback {

        public Stub() {
            attachInterface(this, ILauncherTabOverlayCallback.class.getName());
        }

        public static ILauncherTabOverlayCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }

            IInterface iin = obj.queryLocalInterface(ILauncherTabOverlayCallback.class.getName());
            if (iin != null && iin instanceof ILauncherTabOverlayCallback) {
                return (ILauncherTabOverlayCallback) iin;
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

        private static class Proxy implements ILauncherTabOverlayCallback {
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
