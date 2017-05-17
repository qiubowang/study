package com.example.wangqiubo.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.wangqiubo.myaidl.Book;
import com.example.wangqiubo.myaidl.IBooksAidlInterface;

import java.util.List;

/**
 * Created by wangqiubo on 2017/5/17.
 */

public class AidlPoolActivity extends Activity implements View.OnClickListener{
    private IBooksAidlInterface booksAidlInterface = null;
    private ListView mListView = null;
    private Button addBook = null;
    private Button queryBinder = null;
    private MyHandler mHandler = null;
    private AidlPool mAidlPool = null;
    private CustomBaseAdapter customBaseAdapter = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aidl_pool);
        mHandler = new MyHandler();
        mListView = (ListView) findViewById(R.id.my_list_view);
        customBaseAdapter = new CustomBaseAdapter(this);
        mListView.setAdapter(customBaseAdapter);

        addBook = (Button)findViewById(R.id.update);
        addBook.setOnClickListener(this);
        queryBinder = (Button)findViewById(R.id.query);
        queryBinder.setOnClickListener(this);
        mAidlPool = new AidlPool(this);
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

    private static final int ADD_BOOK_CODE = 0;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.update){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Book book = new Book("乔布斯", "名人传", "1");
                    try {
                        booksAidlInterface.addBook(book);
                        book = new Book("雷军", "名人传", "2");
                        booksAidlInterface.addBook(book);
                        book = new Book("柳传志", "名人传", "3");
                        booksAidlInterface.addBook(book);
                        List<Book> books = booksAidlInterface.getBookList();
                        customBaseAdapter.setBookDatas(books);
                        Message msg = mHandler.obtainMessage();
                        msg.what = ADD_BOOK_CODE;
                        msg.sendToTarget();

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }else if (v.getId() == R.id.query){
            try {
                IBinder binder = mAidlPool.queryBinder(AidlPool.BOOK_BINDER_ID);
                if (null != binder){
                    booksAidlInterface = IBooksAidlInterface.Stub.asInterface(binder);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ADD_BOOK_CODE:
                    customBaseAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }



}
