package com.example.wangqiubo.myapplication.CustomBitmapFactory;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by wangqiubo on 2017/6/6.
 */

public class PictureSampleUtil {
    /**options.inSampleSize 采样频率一般为2的n次幂，当inSampleSize <= 1的时候图片像素保持不变
     * inSampleSize =2，则width为原来的1/2，height也为原来的1/2，即是图片的像素为原来的1/4
     * 公式为：采用后的图片像素 = 图片原始像素/(inSampleSize * inSampleSize)
     */
    public static Bitmap getSampleBitmapFromResource(Resources resources, int redId, int requestWidth, int requestHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //inJustDecodeBounds从名称就可以看出本值设置为true只是解析边界，换句话说不会输出bitmMap
        //进入源代码是这样说明的，inJustDecodeBounds=true，解码将会返回null,而不会返回bitMap,并且bitmMap的边界值会设定。
        //也就是此时itmapFactory.decodeResource(返回的为null，inJustDecodeBounds =true,decodeResource解析是轻量级操作
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, redId, options);

        //设置采样频率
        options.inSampleSize = calSampleSize(requestWidth, requestHeight, options);

        //inJustDecodeBounds = false 解码才会返回bitMap
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(resources, redId, options);
    }

    public static int calSampleSize(int requestWidth, int requestHeight, BitmapFactory.Options options){
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
        int inSampeSize = 1;
        int halfWidth = imageWidth/2;
        int halfHeight = imageHeight/2;
        while (halfHeight >= requestHeight && halfWidth >= requestWidth){
            inSampeSize *= 2;
            halfHeight /= 2;
            halfWidth /= 2;
        }
        return inSampeSize;
    }
}
