package com.example.wangqiubo.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

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
        Log.d("aidlPool：", " queryBinder");
        if (null == aidlPoolInterface)
            return null;
        mBinder = aidlPoolInterface.queryBinder(binderId);
        return mBinder;
    }

    public IAidlPoolInterface getAidlPoolInterface(){
        return aidlPoolInterface;
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("aidlPool", " ServiceConnection");
            aidlPoolInterface = IAidlPoolInterface.Stub.asInterface(service);
            if (null != aidlPoolInterface)
                Log.d("aidlPool:", "aidlPoolInterface is not null");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
