package com.example.wangqiubo.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.wangqiubo.myaidl.IBooksAidlInterface;

/**
 * Created by wangqiubo on 2017/5/17.
 */

public class AidlPoolActivity extends Activity{
    private IBooksAidlInterface booksAidlInterface = null;
    private ListView mListView = null;
    private CustomBaseAdapter customBaseAdapter = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.my_list_view);
        customBaseAdapter = new CustomBaseAdapter(this);
        mListView.setAdapter(customBaseAdapter);

        AidlPool aidlPool = new AidlPool(this);
        try {
            IBinder binder = aidlPool.queryBinder(AidlPool.BOOK_BINDER_ID);
            if (null != binder){
                booksAidlInterface = IBooksAidlInterface.Stub.asInterface(binder);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
