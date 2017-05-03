package com.example.wangqiubo.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.wangqiubo.myaidl.Book;
import com.example.wangqiubo.myaidl.IBooksAidlInterface;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private IBooksAidlInterface mBooksAidl;
    private List<Book> mBookList;
    private MyHandler myHandler = null;
    private ListView mListView = null;
    private CustomBaseAdapter mCustomBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myHandler = new MyHandler(Looper.getMainLooper());

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mListView = (ListView)findViewById(R.id.my_list_view);
        mCustomBaseAdapter = new CustomBaseAdapter(this);
        mListView.setAdapter(mCustomBaseAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.example.myservice","com.example.myservice.BookManagerService");
                intent.setComponent(componentName);
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        }).start();;
    }

    private class MyHandler extends Handler{

        public MyHandler() {
        }

        public MyHandler(Callback callback) {
            super(callback);
        }

        public MyHandler(Looper looper) {
            super(looper);
        }

        public MyHandler(Looper looper, Callback callback) {
            super(looper, callback);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if (null != mBooksAidl){
                        try {
                            mBookList = mBooksAidl.getBookList();
                            if (null != mBookList){
                                mCustomBaseAdapter.setBookDatas(mBookList);
                                mListView.postInvalidate();
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBooksAidl = IBooksAidlInterface.Stub.asInterface(service);
            Message message = myHandler.obtainMessage();
            message.what = 1;
            message.sendToTarget();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
