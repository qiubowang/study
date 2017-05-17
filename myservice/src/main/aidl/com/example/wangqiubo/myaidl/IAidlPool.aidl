// IAidlPool.aidl
package com.example.wangqiubo.myaidl;

import android.os.IBinder;

// Declare any non-default types here with import statements

interface IAidlPool {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     IBinder queryBinder(int binderId);
}
