package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by wangqiubo on 2017/5/17.
 */

public class AidlPoolService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return aidlPool;
    }

    private static IBinder aidlPool = new AidlPoolImpl();
}
