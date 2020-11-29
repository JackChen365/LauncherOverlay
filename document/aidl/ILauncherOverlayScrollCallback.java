package com.cz.launcher.overlay.sample.scroll.service;
public interface ILauncherOverlayScrollCallback extends android.os.IInterface {
    public void overlayScrollChanged(float progress) throws android.os.RemoteException;
    public void overlayStatusChanged(int status) throws android.os.RemoteException;
    /** Local-side IPC implementation stub class. */
    public static abstract class Stub extends android.os.Binder implements ILauncherOverlayScrollCallback {
        private static final java.lang.String DESCRIPTOR = "com.cz.launcher.overlay.sample.scroll.service.ILauncherOverlayCallback";
        /** Construct the stub at attach it to the interface. */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }
        /**
         * Cast an IBinder object into an cn.demo.overlaylibrary.ILauncherOverlayCallback interface,
         * generating a proxy if needed.
         */
        public static ILauncherOverlayScrollCallback asInterface(android.os.IBinder obj) {
            if ((obj==null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin!=null)&&(iin instanceof ILauncherOverlayScrollCallback))) {
                return ((ILauncherOverlayScrollCallback)iin);
            }
            return new ILauncherOverlayScrollCallback.Stub.Proxy(obj);
        }
        @Override
        public android.os.IBinder asBinder() {
            return this;
        }
        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            java.lang.String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_overlayScrollChanged: {
                    data.enforceInterface(descriptor);
                    float _arg0;
                    _arg0 = data.readFloat();
                    this.overlayScrollChanged(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_overlayStatusChanged: {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    this.overlayStatusChanged(_arg0);
                    reply.writeNoException();
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }
        private static class Proxy implements ILauncherOverlayScrollCallback {
            private android.os.IBinder mRemote;
            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }
            @Override
            public android.os.IBinder asBinder() {
            return mRemote;
            }
            public java.lang.String getInterfaceDescriptor() {
            return DESCRIPTOR;
            }
            @Override
            public void overlayScrollChanged(float progress) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeFloat(progress);
                    mRemote.transact(Stub.TRANSACTION_overlayScrollChanged, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            @Override
            public void overlayStatusChanged(int status) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(status);
                    mRemote.transact(Stub.TRANSACTION_overlayStatusChanged, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
        static final int TRANSACTION_overlayScrollChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_overlayStatusChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    }
}
