package com.example.myservice;

import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.wangqiubo.myaidl.IAidlPool;

/**
 * Created by wangqiubo on 2017/5/17.
 */

public class AidlPoolImpl extends IAidlPool.Stub {
    public static final int BOOK_BINDER_ID = 0;
    public static final int USER_BINDER_ID = 1;
    IBinder mBinder = null;
    @Override
    public IBinder queryBinder(int binderId) throws RemoteException {
//        Debug.waitForDebugger();
        switch(binderId){
            case BOOK_BINDER_ID:
                mBinder = new BookImpl();
                break;
            case USER_BINDER_ID:
                mBinder = new UserImpl();
                break;
        }
        return mBinder;
    }
}
