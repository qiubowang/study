package com.example.wangqiubo.myapplication.CustomBitmapFactory;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import com.example.wangqiubo.libcore.io.DiskLruCache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangqiubo on 2017/6/16.
 *
 * 本工具栏采用三级缓存，以及缓存内存，二级缓存磁盘，三级缓存网络加载。
 * 本缓存采用Lruch(最近最少使用)策略，LruchCache（内存）、DiskLruchCache。
 * Lruch维护了一个列表，最近使用的一直排在列表最前面，最长时间未使用的排在列表最后，
 * 超过缓存大小或者虚拟机回收的时候总是会删除最长时间未使用的。
 *
 * 缓存的图片是网络下载后通过采用频率的计算，对图片进行了一次压缩处理
 */

public class ImageLoader {
    private static LruCache<String, Bitmap> LRU_CACHE = null;
    private static DiskLruCache DISK_LRU_CACHE = null;
    private static final int DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;
    private Context mContext = null;

    public ImageLoader(Context context){
        mContext = context;
        if (null == LRU_CACHE) {
            long maxMemorySize = Runtime.getRuntime().maxMemory();
            //最大内存的1/8，单位为M
            int lruCacheSize = (int)(maxMemorySize/(1024 * 1024 * 8));
            LRU_CACHE = new LruCache<String, Bitmap>(lruCacheSize){

                @Override
                protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                    super.entryRemoved(evicted, key, oldValue, newValue);
                }

                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight() / 1024/1024;
                }
            };
        }

        if(null == DISK_LRU_CACHE){
            String dir = null;
            DISK_LRU_CACHE = getDiskLruCache(dir);
        }
    }

    /**获取 diskLruCache对象
     *
     * @param dir
     * @return
     */
    public DiskLruCache getDiskLruCache(String dir){
        File file = new File(dir);
        try {
            return DiskLruCache.open(file, getAppVersion(), 1, DISK_CACHE_MAX_SIZE );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 获取app的版本号
     *
     * @return
     */
    public int getAppVersion(){
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public Bitmap getFilterBitmap(String urlAddress, int requestWidth, int requestHeight){
        String key = getMd5Key(urlAddress);
        if (LRU_CACHE.snapshot().containsKey(key)){
            return LRU_CACHE.snapshot().get(key);
        }else {
            try {
                DiskLruCache.Snapshot snapshot = DISK_LRU_CACHE.get(key);
                if (null != snapshot){
                    FileInputStream inputStream = (FileInputStream)snapshot.getInputStream(0);
                    if (null != inputStream){
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        if (null != bitmap){
                            LRU_CACHE.snapshot().put(key, bitmap);
                            return bitmap;
                        }
                    }else{
                        return getBitmapFromUrl(urlAddress, key, requestWidth, requestHeight);
                    }
                }else{
                    return getBitmapFromUrl(urlAddress, key, requestWidth, requestHeight);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**options.inSampleSize 采样频率一般为2的n次幂，当inSampleSize <= 1的时候图片像素保持不变
     * inSampleSize =2，则width为原来的1/2，height也为原来的1/2，即是图片的像素为原来的1/4
     * 公式为：采用后的图片像素 = 图片原始像素/(inSampleSize * inSampleSize)
     *
     *inJustDecodeBounds从名称就可以看出本值设置为true只是解析边界，换句话说不会输出bitmMap
     *进入源代码是这样说明的，inJustDecodeBounds=true，解码将会返回null,而不会返回bitMap,并且bitmMap的边界值会设定。
     *也就是此时itmapFactory.decodeResource(返回的为null，inJustDecodeBounds =true,decodeResource解析是轻量级操作
     *
     * @param bitmapData
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public Bitmap getSampleBitmap(byte[] bitmapData, int requestWidth, int requestHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length, options);
        options.inSampleSize = calSampleSize(requestWidth, requestHeight, options);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length, options);
    }

    /** 采用频率一般为2的幂次方
     *
     * @param requestWidth
     * @param requestHeight
     * @param options
     * @return
     */
    public int calSampleSize(int requestWidth, int requestHeight, BitmapFactory.Options options){
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int halfWidth = outWidth/2;
        int halfHeight = outHeight/2;
        int sampleSize = 1;
        while (halfHeight > requestHeight && halfWidth > requestWidth){
            halfWidth/=2;
            halfHeight/=2;
            sampleSize *= 2;
        }
        return sampleSize;
    }

    /**将网络经过md5编码生成key
     *
     * @param url
     * @return
     */
    public String getMd5Key(String url){

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            return byteToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
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

    /** 从网络下载图片
     *
     * @param strUrl 图片所在的网络地址
     * @param key 根据网络地址进行MD5编码生成出来的key
     * @return
     */
    public Bitmap getBitmapFromUrl(String strUrl, String key, int requestWidth, int requestHeight){
        InputStream inputStream = null;
        OutputStream outStream = null;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            //网络上下载图片，客户端阻塞似的读取数据
            inputStream = connection.getInputStream();
            byte[] bitMapArray = getBitmapBytes(inputStream);
            //获取采样之后的bitMap（对图片进行一次压缩）
            Bitmap bitmap = getSampleBitmap(bitMapArray, requestWidth, requestHeight);
            //图片保持到内存
            LRU_CACHE.snapshot().put(key, bitmap);
            //图片保持到磁盘
            DiskLruCache.Editor editor = DISK_LRU_CACHE.edit(key);
            outStream = editor.newOutputStream(0);
            outStream.write(bitMapArray, 0 ,bitMapArray.length);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static byte[]  getBitmapBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] tempByte = new byte[8*1024];
        int readIndex = 0;
        while ((readIndex = inputStream.read(tempByte)) != -1){
            byteArrayOutputStream.write(tempByte, 0, tempByte.length);
        }
        return byteArrayOutputStream.toByteArray();
    }


}
