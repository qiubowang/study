package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.wangqiubo.myaidl.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqiubo on 2017/5/5.
 */

public class MessengerService extends Service {
     private static ArrayList<Book> mBookList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book("乔布斯","1000","名人传"));
        mBookList.add(new Book("雷军","1001","名人传"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    private static class MessengerHandler extends Handler{


        public MessengerHandler() {

        }

        @Override
        public void handleMessage(Message msg) {
//            Debug.waitForDebugger();
            if (0 == msg.what){
                Log.d("messagerServer", "客户端发来了数据！");
                Messenger clientMss = msg.replyTo;
                Bundle bundle = new Bundle();
                bundle.putString("testStr", "lllll");
                bundle.putParcelable("serverBook",mBookList.get(0));
                bundle.putParcelableArrayList("serverBooks", mBookList);
                Message message = new Message();
                message.what = 2;
                Book book = bundle.getParcelable("serverBook");
                List<Book> myBooks = bundle.getParcelableArrayList("serverBooks");
                message.setData(bundle);
                try {
                    clientMss.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }else {
                super.handleMessage(msg);
            }
        }
    }

    private static Messenger messenger = new Messenger(new MessengerHandler());
}
