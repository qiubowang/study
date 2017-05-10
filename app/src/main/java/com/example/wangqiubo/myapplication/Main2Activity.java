package com.example.wangqiubo.myapplication;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;

import com.example.wangqiubo.myaidl.Book;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private Messenger remoteMessenger = null;
    private Messenger clientMessenger = null;
    private ListView mListView = null;
    private CustomBaseAdapter mAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new CustomBaseAdapter(this);
        mListView = (ListView) this.findViewById(R.id.my_list_view);
        mListView.setAdapter(mAdapter);
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.example.myservice", "com.example.myservice.MessengerService");
        intent.setComponent(componentName);
        this.bindService(intent, mySer, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mySer = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //SDebug.waitForDebugger();
            Log.d("messager", "连接服务！");
            remoteMessenger = new Messenger(service);
            Message message = Message.obtain(null,0);
            Bundle mBundle = new Bundle();
            mBundle.putString("aa","bb");
            message.setData(mBundle);
            ClientHanndler clientHanndler = new ClientHanndler();
            Log.d("messagerLooper", clientHanndler.getLooper().toString());
            clientMessenger = new Messenger(clientHanndler);
            message.replyTo = clientMessenger;
            try {
                remoteMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private class ClientHanndler  extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    //Debug.waitForDebugger();
                    Log.d("messager", "服务器进行了反馈！");
                    Bundle serverData = msg.getData();
                    if (null == serverData){
                        Log.d("messager", "serverData为空值");
                        return;
                    }
                    Book book = serverData.getParcelable("serverBook");
                    List<Book> books = serverData.getParcelableArrayList("serverBooks");
                    if (null != books && books.size() > 0)
                        Log.d("messager", "从服务器中已经获取到了数据");
                    else {

                        Log.d("messager", "从服务器中没有获取到了数据");
                        return;
                    }
                    mAdapter.setBookDatas(books);
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;

            }
            super.handleMessage(msg);
        }
    }
}
