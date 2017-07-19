package com.example.wangqiubo.myapplication.CustomBitmapFactory;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.support.v4.util.LruCache;

import com.example.wangqiubo.libcore.io.DiskLruCache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangqiubo on 2017/6/6.
 */

public class PictureSampleUtil {
    private static LruCache<String, Bitmap> mLruCache = null;
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

    public static void LruCacheUtil(){
        long maxMemorySize = Runtime.getRuntime().maxMemory()/1024;
        int catchTotalSize = (int)(maxMemorySize/8);
        mLruCache = new LruCache<String, Bitmap>(catchTotalSize){

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
            }

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() /1024;
            }
        };
    }

    public static class DiskCacheUtil{
        private  static DiskLruCache diskLruCache = null;

        public static File getDiskLruCacheDir(Context context, String uniqueName){
            String catchPath = null;
//            if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || ! Environment.isExternalStorageRemovable()){
//                catchPath = context.getExternalCacheDir().getPath();
//            }else{
//                catchPath = context.getCacheDir().getPath();
//            }
            catchPath = "D:\\workproject\\myself\\MyApplication2\\app\\src\\main\\java\\com\\example\\wangqiubo\\myapplication\\CustomBitmapFactory";
            return new File(catchPath + File.separator + uniqueName);
        }

        public static int getAppVersion(Context context){
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                return packageInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return  1;
        }

        public static void cacheBitmap(Context context, String url){

            DiskLruCache diskLruCache = createDiskLruCache(context);
            String hasKey = getMd5Key(url);
            try {
                DiskLruCache.Editor editor = diskLruCache.edit(hasKey);
                if (null != editor){
                    OutputStream outputStream = editor.newOutputStream(0);
                    byte[] bitMapDatas = downLoadPicture(url);
                    if (null != bitMapDatas && bitMapDatas.length > 0) {
                        outputStream.write(bitMapDatas);
                        outputStream.close(); //切记输入输出流关闭
                        editor.commit();
                    }else {
                        editor.abort();
                    }
                    diskLruCache.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static DiskLruCache createDiskLruCache(Context context){
            File catchDir = getDiskLruCacheDir(context, "bitMaps");
            if (!catchDir.exists())
                catchDir.mkdir();
            try {
                diskLruCache = DiskLruCache.open(catchDir, 1, 1, 10 *1024 * 1024);//getAppVersion(context)
            } catch (IOException e) {
                e.printStackTrace();
            }

            return diskLruCache;
        }


    }

    public static byte[] downLoadPicture(String urlPath){
        try {
            URL url = new URL(urlPath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();;
            InputStream stream = con.getInputStream();
            byte[] datas = getByteByStream(stream);
            return datas;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
        String urlPath = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
//        downLoadPicture(urlPath);
        DiskCacheUtil.cacheBitmap(null, urlPath);
    }

    public static int DATA_SIZE = 1024  * 8;
    public static byte[] getByteByStream(InputStream inputStream){

        byte[] byteDatas = new byte[DATA_SIZE];
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream(DATA_SIZE);

        int readIndex = 0;
        try {
            while ((readIndex = inputStream.read(byteDatas)) != -1){
                byteArrayInputStream.write(byteDatas, 0 , readIndex);
            }
            return byteArrayInputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                byteArrayInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }

    public static String getMd5Key(String urlPath){
        String cachKey = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(urlPath.getBytes());
            cachKey = byteToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return cachKey;
    }

    public static String byteToHexString(byte[] datas){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < datas.length; i++) {
            String hex = Integer.toHexString(0xFF & datas[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
