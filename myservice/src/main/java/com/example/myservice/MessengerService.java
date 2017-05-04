package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by wangqiubo on 2017/5/5.
 */

public class MessengerService extends Service {
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
            if (0 == msg.what){
                Messenger clientMss = msg.replyTo;
                Bundle bundle = new Bundle();
                bundle.putString("kkkk", "lllll");
                Message message = Message.obtain(null,2);
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
