package com.cz.launcher.overlay.library.tab;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.WindowManager;

import com.cz.launcher.overlay.library.fixed.ILauncherFixedOverlayCallback;

public interface ILauncherTabOverlay extends IInterface {
    void closeOverlay(int options) throws RemoteException;

    void onStart() throws RemoteException;

    void onResume() throws RemoteException;

    void onPause() throws RemoteException;

    void onStop() throws RemoteException;

    void openOverlay(int options) throws RemoteException;

    void onWindowAttached(WindowManager.LayoutParams attrs, ILauncherFixedOverlayCallback callbacks, int options) throws RemoteException;

    void onWindowDetached(boolean isChangingConfigurations) throws RemoteException;

    abstract class Stub extends Binder implements ILauncherTabOverlay {
        static final int WINDOW_ATTACHED_TRANSACTION = 1;
        static final int WINDOW_DETACHED_TRANSACTION = 2;
        static final int ON_PAUSE_TRANSACTION = 3;
        static final int ON_RESUME_TRANSACTION = 4;
        static final int ON_START_TRANSACTION = 5;
        static final int ON_STOP_TRANSACTION = 6;
        static final int OPEN_OVERLAY_TRANSACTION = 7;
        static final int CLOSE_OVERLAY_TRANSACTION = 8;


        public static ILauncherTabOverlay asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }

            IInterface iin = obj.queryLocalInterface(ILauncherTabOverlay.class.getName());
            if (iin != null && iin instanceof ILauncherTabOverlay) {
                return (ILauncherTabOverlay) iin;
            }
            else {
                return new Proxy(obj);
            }
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION:
                    reply.writeString(ILauncherTabOverlay.class.getName());
                    return true;
                case WINDOW_ATTACHED_TRANSACTION:
                    data.enforceInterface(ILauncherTabOverlay.class.getName());
                    WindowManager.LayoutParams layoutParams = null;
                    if (data.readInt() != 0) {
                        layoutParams = WindowManager.LayoutParams.CREATOR.createFromParcel(data);
                    }
                    onWindowAttached(
                        layoutParams,
                        ILauncherFixedOverlayCallback.Stub.asInterface(data.readStrongBinder()), data.readInt());
                    return true;
                case WINDOW_DETACHED_TRANSACTION:
                    data.enforceInterface(ILauncherTabOverlay.class.getName());
                    onWindowDetached(data.readInt() != 0);
                    return true;
                case CLOSE_OVERLAY_TRANSACTION:
                    data.enforceInterface(ILauncherTabOverlay.class.getName());
                    closeOverlay(data.readInt());
                    return true;
                case ON_PAUSE_TRANSACTION:
                    data.enforceInterface(ILauncherTabOverlay.class.getName());
                    onPause();
                    return true;
                case ON_RESUME_TRANSACTION:
                    data.enforceInterface(ILauncherTabOverlay.class.getName());
                    onResume();
                    return true;
                case ON_START_TRANSACTION:
                    data.enforceInterface(ILauncherTabOverlay.class.getName());
                    onStart();
                    return true;
                case ON_STOP_TRANSACTION:
                    data.enforceInterface(ILauncherTabOverlay.class.getName());
                    onStop();
                    return true;
                case OPEN_OVERLAY_TRANSACTION:
                    data.enforceInterface(ILauncherTabOverlay.class.getName());
                    openOverlay(data.readInt());
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ILauncherTabOverlay {
            private IBinder mRemote;

            public Proxy(IBinder remote) {
                mRemote = remote;
            }

            public IBinder asBinder() {
                return mRemote;
            }

            public void closeOverlay(int options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherTabOverlay.class.getName());
                    _data.writeInt(options);

                    mRemote.transact(CLOSE_OVERLAY_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            @Override
            public void onStart() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherTabOverlay.class.getName());
                    mRemote.transact(ON_START_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            @Override
            public void onPause() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherTabOverlay.class.getName());
                    mRemote.transact(ON_PAUSE_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            @Override
            public void onStop() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherTabOverlay.class.getName());
                    mRemote.transact(ON_STOP_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            @Override
            public void onResume() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherTabOverlay.class.getName());

                    mRemote.transact(ON_RESUME_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            @Override
            public void openOverlay(int options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherTabOverlay.class.getName());
                    _data.writeInt(options);

                    mRemote.transact(OPEN_OVERLAY_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            @Override
            public void onWindowAttached(WindowManager.LayoutParams attrs, ILauncherFixedOverlayCallback callbacks, int options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherTabOverlay.class.getName());
                    if (attrs != null) {
                        _data.writeInt(1);
                        attrs.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callbacks.asBinder());
                    _data.writeInt(options);

                    _data.writeInt(1);

                    mRemote.transact(WINDOW_ATTACHED_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            @Override
            public void onWindowDetached(boolean isChangingConfigurations) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherTabOverlay.class.getName());
                    _data.writeInt(isChangingConfigurations ? 1 : 0);

                    mRemote.transact(WINDOW_DETACHED_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }
        }


    }
}