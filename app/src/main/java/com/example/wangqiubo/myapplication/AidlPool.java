package com.example.wangqiubo.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.wangqiubo.myaidl.IAidlPoolInterface;

/**
 * Created by wangqiubo on 2017/5/17.
 */

public class AidlPool {
    public static final int BOOK_BINDER_ID = 0;
    public static final int USER_BINDER_ID = 1;
    private IAidlPoolInterface aidlPoolInterface = null;
    private IBinder mBinder = null;

    public AidlPool(Context context){
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.example.myservice","com.example.myservice.BookManagerService");
        intent.setComponent(componentName);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public IBinder queryBinder(int binderId) throws RemoteException {
//        switch(binderId){
//            case BOOK_BINDER_ID:
//                mBinder = aidlPoolInterface.queryBinder(binderId)
//                break;
//            case USER_BINDER_ID:
//
//                break;
//        }
        mBinder = aidlPoolInterface.queryBinder(binderId);
        return mBinder;
    }

    public IAidlPoolInterface getAidlPoolInterface(){
        return aidlPoolInterface;
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidlPoolInterface = IAidlPoolInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
