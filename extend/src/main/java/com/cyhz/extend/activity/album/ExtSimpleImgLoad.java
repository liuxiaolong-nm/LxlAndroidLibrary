package com.cyhz.extend.activity.album;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.cyhz.extend.R;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by liuxiaolong on 16/3/11.
 */
public class ExtSimpleImgLoad {

    private static ExtSimpleImgLoad mSimpleImgLoad = new ExtSimpleImgLoad();
    private ExecutorService mTaskPool;
    private ConcurrentLinkedQueue<TaskInfo> mTaskInfoQ = new ConcurrentLinkedQueue();
    private LruCache<String, Bitmap> mMemoryCache;

    public static ExtSimpleImgLoad get(){
        return mSimpleImgLoad;
    }

    /**
     * 释放内存。
     */
    public void dump(){
        mTaskPool.shutdownNow();
        mMemoryCache.evictAll();
        mTaskInfoQ.clear();
    }

    private ExtSimpleImgLoad(){
        mTaskPool = Executors.newFixedThreadPool(3);
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 6;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return bitmap.getByteCount() / 1024;
            }
        };
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(mTaskInfoQ,mMemoryCache);
        mTaskPool.execute(bitmapWorkerTask);
    }

    /**
     * 加载图片，如果有加载内存的，没有则加载sdk中的。
     * @param pathSdk
     * @param imageView
     */
    public void loadBitmap(String pathSdk, ImageView imageView) {
        imageView.setImageResource(R.drawable.ext_system_photo_wu_tu);
        TaskInfo taskInfo = new TaskInfo(pathSdk,imageView);
        mTaskInfoQ.remove(taskInfo);
        mTaskInfoQ.offer(taskInfo);
    }

    class BitmapWorkerTask implements Runnable {
        private ConcurrentLinkedQueue<TaskInfo> qu;
        private LruCache<String, Bitmap> memoryCache;

        public BitmapWorkerTask(ConcurrentLinkedQueue queue,LruCache<String, Bitmap> cache){
            qu = queue;
            memoryCache = cache;
        }

        @Override
        public void run() {
             while (true) {
                 final TaskInfo info = qu.poll();
                 if (info != null) {
                     Bitmap bit = getBitmapFromMemCache(info.path);
                     if (bit == null){
                         bit = ExtSimpleBitmapZip.setBit(info.path);
                     }
                     if (bit == null){
                         continue;
                     }
                     final Bitmap bitmap = bit;
                     if (mTaskInfoQ.contains(info)){
                         continue;
                     }
                     addBitmapToMemoryCache(info.path, bitmap);
                         new Handler(Looper.getMainLooper()).post(new Runnable() {
                             @Override
                             public void run() {
                                 info.target.setImageBitmap(bitmap);
                             }
                         });
                 }
             }
        }

        /**
         * 添加到缓存当中。
         * @param key
         * @param bitmap
         */
        private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
            if (getBitmapFromMemCache(key) == null) {
                memoryCache.put(key, bitmap);
            }
        }

        /**
         * 从缓存中提取图片。
         * @param key
         * @return
         */
        private Bitmap getBitmapFromMemCache(String key) {
            return memoryCache.get(key);
        }
    }

    public static class TaskInfo{
        String path;
        ImageView target;

        public TaskInfo(String p,ImageView img){
            path = p;
            target = img;
        }

        @Override
        public int hashCode() {
            return target.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o){
                return true;
            }
            if (o == null){
                return false;
            }
            if (!(o instanceof TaskInfo)){
                return false;
            }
            TaskInfo info = (TaskInfo)o;

            if (this.target != info.target){
                return false;
            }
            return true;
        }
    }
}
