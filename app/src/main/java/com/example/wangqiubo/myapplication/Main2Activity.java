package com.example.wangqiubo.myapplication;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {
    private Messenger remoteMessenger = null;
    private Messenger clientMessenger = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    private ServiceConnection mySer = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            remoteMessenger = new Messenger(service);
            Message message = Message.obtain(null,1);
            Bundle mBundle = new Bundle();
            mBundle.putString("aa","bb");
            message.setData(mBundle);
            ClientHanndler clientHanndler = new ClientHanndler();
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

    private static class ClientHanndler  extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
