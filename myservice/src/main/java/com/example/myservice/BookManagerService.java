package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.wangqiubo.myaidl.Book;
import com.example.wangqiubo.myaidl.IBooksAidlInterface;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerService extends Service {
    private static final String TAG = "BMS";

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    public BookManagerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book("乔布斯","1000","名人传"));
        mBookList.add(new Book("雷军","1001","名人传"));
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private Binder mBinder = new IBooksAidlInterface.Stub(){
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(com.example.wangqiubo.myaidl.Book book) throws RemoteException {
            mBookList.add(book);
        }
    };
}
