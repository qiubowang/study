package com.example.wangqiubo.myapplication.CustomRemoteView;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.wangqiubo.myapplication.R;

/**
 * Created by wangqiubo on 2017/7/17.
 */

public class MyAppWidgetProvider extends AppWidgetProvider {
    public static final String CLICK_ACTION = "com.example.wangqiubo.myapplication.CustomRemoteView.CLICK";

    public MyAppWidgetProvider(){
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent.getAction() == CLICK_ACTION){
            Toast.makeText(context, "触发了点击事件", Toast.LENGTH_LONG);
            new Thread(() ->{
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image1);
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                for (int i = 0; i < 37; i++){
                    int degree = (i * 10) % 360;
                    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                    remoteViews.setImageViewBitmap(R.id.widget_image1, rotateBitmap(bitmap, degree));
                    Intent intent1 = new Intent();
                    intent1.setAction(CLICK_ACTION);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, 0);
                    remoteViews.setOnClickPendingIntent(R.id.widget_image1, pendingIntent);
                    manager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class), remoteViews);
                    SystemClock.sleep(30);
                }
            }).start();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        int length = appWidgetIds.length;
        for (int i =0; i < length; i++){
            int appId = appWidgetIds[i];
            widgetUpdate(context, appWidgetManager, appId);
        }
    }

    public void widgetUpdate(Context context, AppWidgetManager manager,int widgetId){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_image1,pendingIntent);
        manager.updateAppWidget(widgetId, remoteViews);
    }

    private Bitmap rotateBitmap(Bitmap srcBitmap, int degree){
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(),matrix,true);
        return bitmap;
    }
}
