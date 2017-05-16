package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.example.wangqiubo.myaidl.Book;
import com.example.wangqiubo.myaidl.IBooksAidlInterface;
import com.example.wangqiubo.myaidl.IBookCallBackInterface;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerService extends Service {
    private static final String TAG = "BMS";
    private static int BOOK_INDEX = 1000;
    private static RemoteCallbackList<IBookCallBackInterface> CALLBACK_LIST = new RemoteCallbackList<>();

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    public BookManagerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book("乔布斯","" + BOOK_INDEX++,"名人传"));
        mBookList.add(new Book("雷军", "" + BOOK_INDEX++, "名人传"));

        new Thread(new MyRunable()).start();
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

        @Override
        public void registerBookEvent(IBookCallBackInterface bookEvent){
            CALLBACK_LIST.register(bookEvent);
//            if (!CALLBACK_LIST.contains(bookEvent))
//                CALLBACK_LIST.add(bookEvent);
        }

        @Override
        public void unregisterBookEvent(IBookCallBackInterface bookEvent){
            CALLBACK_LIST.unregister(bookEvent);
//            if (CALLBACK_LIST.contains(bookEvent))
//                CALLBACK_LIST.remove(bookEvent);
        }

    };

    private class MyRunable implements Runnable{

        @Override
        public void run() {
            try {
                //Debug.waitForDebugger();
                while (true) {
                    Thread.sleep(1000);
                    mBookList.add(new Book("雷军" + BOOK_INDEX++, "" + BOOK_INDEX, "名人传"));
                    int n = CALLBACK_LIST.beginBroadcast();
                    for (int i = 0; i < n; i++){
                        CALLBACK_LIST.getBroadcastItem(i).newBookNotification(mBookList.get(mBookList.size()-1));
                    }
                    CALLBACK_LIST.finishBroadcast();
//                    for (int i = 0, size = CALLBACK_LIST.size(); i < size; i++) {
//                        CALLBACK_LIST.get(i).newBookNotification(mBookList.get(mBookList.size() - 1));
//                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
