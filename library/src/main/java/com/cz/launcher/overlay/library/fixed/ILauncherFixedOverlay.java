package com.cz.launcher.overlay.library.fixed;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.WindowManager;

public interface ILauncherFixedOverlay extends IInterface {
    void onStart() throws RemoteException;

    void onResume() throws RemoteException;

    void onPause() throws RemoteException;

    void onStop() throws RemoteException;

    void show() throws RemoteException;

    void hide() throws RemoteException;

    boolean isVisible() throws RemoteException;

    void onWindowAttached(WindowManager.LayoutParams attrs, ILauncherFixedOverlayCallback callbacks, int options) throws RemoteException;

    void onWindowDetached(boolean isChangingConfigurations) throws RemoteException;

    abstract class Stub extends Binder implements ILauncherFixedOverlay {
        static final int WINDOW_ATTACHED_TRANSACTION = 1;
        static final int WINDOW_DETACHED_TRANSACTION = 2;
        static final int ON_PAUSE_TRANSACTION = 3;
        static final int ON_RESUME_TRANSACTION = 4;
        static final int ON_START_TRANSACTION = 5;
        static final int ON_STOP_TRANSACTION = 6;
        static final int SHOW_TRANSACTION = 7;
        static final int HIDE_TRANSACTION = 8;
        static final int IS_VISIBLE_TRANSACTION = 9;


        public static ILauncherFixedOverlay asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }

            IInterface iin = obj.queryLocalInterface(ILauncherFixedOverlay.class.getName());
            if (iin != null && iin instanceof ILauncherFixedOverlay) {
                return (ILauncherFixedOverlay) iin;
            }
            else {
                return new Proxy(obj);
            }
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION:
                    reply.writeString(ILauncherFixedOverlay.class.getName());
                    return true;
                case WINDOW_ATTACHED_TRANSACTION:
                    data.enforceInterface(ILauncherFixedOverlay.class.getName());
                    WindowManager.LayoutParams layoutParams = null;
                    if (data.readInt() != 0) {
                        layoutParams = WindowManager.LayoutParams.CREATOR.createFromParcel(data);
                    }
                    onWindowAttached(
                        layoutParams,
                        ILauncherFixedOverlayCallback.Stub.asInterface(data.readStrongBinder()), data.readInt());
                    return true;
                case WINDOW_DETACHED_TRANSACTION:
                    data.enforceInterface(ILauncherFixedOverlay.class.getName());
                    onWindowDetached(data.readInt() != 0);
                    return true;
                case ON_PAUSE_TRANSACTION:
                    data.enforceInterface(ILauncherFixedOverlay.class.getName());
                    onPause();
                    return true;
                case ON_RESUME_TRANSACTION:
                    data.enforceInterface(ILauncherFixedOverlay.class.getName());
                    onResume();
                    return true;
                case ON_START_TRANSACTION:
                    data.enforceInterface(ILauncherFixedOverlay.class.getName());
                    onStart();
                    return true;
                case ON_STOP_TRANSACTION:
                    data.enforceInterface(ILauncherFixedOverlay.class.getName());
                    onStop();
                    return true;
                case SHOW_TRANSACTION:
                    data.enforceInterface(ILauncherFixedOverlay.class.getName());
                    show();
                    return true;
                case HIDE_TRANSACTION:
                    data.enforceInterface(ILauncherFixedOverlay.class.getName());
                    hide();
                    return true;
                case IS_VISIBLE_TRANSACTION:
                    data.enforceInterface(ILauncherFixedOverlay.class.getName());
                    boolean _result = this.isVisible();
                    reply.writeNoException();
                    reply.writeInt(((_result)?(1):(0)));
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ILauncherFixedOverlay {
            private IBinder mRemote;

            public Proxy(IBinder remote) {
                mRemote = remote;
            }

            public IBinder asBinder() {
                return mRemote;
            }

            @Override
            public void onStart() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherFixedOverlay.class.getName());
                    mRemote.transact(ON_START_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            @Override
            public void onPause() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherFixedOverlay.class.getName());
                    mRemote.transact(ON_PAUSE_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            @Override
            public void onStop() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherFixedOverlay.class.getName());
                    mRemote.transact(ON_STOP_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            @Override
            public void onResume() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherFixedOverlay.class.getName());

                    mRemote.transact(ON_RESUME_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            @Override
            public void show() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherFixedOverlay.class.getName());
                    mRemote.transact(SHOW_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            public void hide() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherFixedOverlay.class.getName());

                    mRemote.transact(HIDE_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            @Override
            public boolean isVisible() throws RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(ILauncherFixedOverlay.class.getName());
                    mRemote.transact(IS_VISIBLE_TRANSACTION, _data, _reply, 0);
                    _reply.readException();
                    _result = (0!=_reply.readInt());
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public void onWindowAttached(WindowManager.LayoutParams attrs, ILauncherFixedOverlayCallback callbacks, int options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILauncherFixedOverlay.class.getName());
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
                    _data.writeInterfaceToken(ILauncherFixedOverlay.class.getName());
                    _data.writeInt(isChangingConfigurations ? 1 : 0);

                    mRemote.transact(WINDOW_DETACHED_TRANSACTION, _data, null, FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }
        }


    }
}