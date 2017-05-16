package com.example.wangqiubo.myapplication;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.wangqiubo.myaidl.Book;

import java.net.URI;

public class ContentProviderActivity extends AppCompatActivity implements View.OnClickListener {
    ListView mListView = null;
    CustomBaseAdapter mAdapter = null;
    Button inserButton = null;
    Button updateButton = null;
    Button queryButton = null;
    MyHander myHander = null;
    private ContentResolver mResolver = null;
    private static final String DATA_URI = "content://com.example.myservice.MyContentProvider/book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHander = new MyHander();

        mListView = (ListView)findViewById(R.id.my_list_view);
        mAdapter = new CustomBaseAdapter(this);
        mListView.setAdapter(mAdapter);

        queryButton = (Button) findViewById(R.id.query);
        inserButton = (Button)findViewById(R.id.insert);
        updateButton = (Button)findViewById(R.id.update);
        queryButton.setOnClickListener(this);
        inserButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);

        mResolver = getContentResolver();
        //Debug.waitForDebugger();
//        Uri uri = Uri.parse(DATA_URI);
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("_id", "10");
//        contentValues.put("name","人生的追求");
//        mResolver.insert(uri, contentValues);
//        Cursor cursor = mResolver.query(uri, new String[]{"_id", "name"},null,null,null);
//        while (cursor.moveToNext()){
//            Book book = new Book(cursor.getString(1), "编程", "" + cursor.getInt(0));
//            mAdapter.addBookData(book);
//        }
//        cursor.close();
//        mAdapter.notifyDataSetChanged();

//        resolver.query(uri,null,null,null,null);
//        resolver.query(uri,null,null,null,null);
    }

    @Override
    public void onClick(View v) {
        //Debug.waitForDebugger();
        Log.d("ThreadProvider", "进入Onclick");
        final Uri uri = Uri.parse(DATA_URI);
        if (v.getId() == R.id.insert){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("_id", 5);
                    contentValues.put("name","JAVASCRIPT");
                    Uri insertUri =  mResolver.insert(uri, contentValues);
                    if (null != insertUri){
                        Message message = myHander.obtainMessage();
                        message.what = INSERT_MESSAGE;
                        message.sendToTarget();
                    }
                }
            }).start();

        }else if (v.getId() == R.id.update){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("ThreadProvider", "进入update Onclick");
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", "UNIX");
                    String selection = "where _id=?";
                    String[] selArg = new String[]{"2"};
                    int rowId = mResolver.update(uri, contentValues, selection, selArg);
                    if (rowId >=0){
                        Message message = myHander.obtainMessage();
                        message.what = UPDATE_MESSAGE;
                        message.sendToTarget();
                    }
                }
            }).start();

        }else if (v.getId() == R.id.query){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("ThreadProvider", "进入query Onclick");
//                    Debug.waitForDebugger();
                    ContentValues contentValues = new ContentValues();
                    String[] projection = new String[]{"_id", "name"};
                    Cursor cursor =mResolver.query(uri, projection,null, null, null);
                    if (null != cursor){
                        cursor.moveToFirst();
                        while (cursor.moveToNext()){
                            Book book = new Book(cursor.getString(1), "编程", "" + cursor.getInt(0));
                            mAdapter.addBookData(book);
                        }
                        cursor.close();
                        Message message = myHander.obtainMessage();
                        message.what = QUERY_MESSAGE;
                        message.sendToTarget();
                    }
                }
            }).start();
        }
    }


    private static int QUERY_MESSAGE = 0;
    private static int INSERT_MESSAGE = 1;
    private static int UPDATE_MESSAGE = 2;
    private class MyHander extends Handler{

        @Override
        public void handleMessage(Message msg) {
            Log.d("ThreadProvider", "进入handleMessage");
            if (msg.what == QUERY_MESSAGE || msg.what == INSERT_MESSAGE  || msg.what == UPDATE_MESSAGE){
                Log.d("ThreadProvider", "进入notifyDataSetChanged");
                Log.d("ThreadProvider", "BookSize: " + mAdapter.getBookDatas().size());
                mAdapter.notifyDataSetChanged();
                return;
            }
            super.handleMessage(msg);
        }
    }
}
