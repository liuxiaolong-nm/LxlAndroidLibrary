package com.cyhz.extend.activity.album;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by liuxiaolong on 16/3/11.
 */
public class ExtSimpleBitmapZip {
    /**
     * 获取图片并压缩压缩成100*100.
     * @param path
     * @return
     */
    public static Bitmap setBit(String path){
        BitmapFactory.Options newOpts =  new  BitmapFactory.Options();
        newOpts.inJustDecodeBounds =  true ;
        Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);
        int  w = newOpts.outWidth;
        int  h = newOpts.outHeight;
        //计算出取样率
        newOpts.inSampleSize = computeSampleSize(newOpts, 100, 100);
        newOpts.inJustDecodeBounds =  false ;
        bitmap = BitmapFactory.decodeFile(path, newOpts);
        return bitmap;
    }


    /**
     * 获取图片并压缩压缩成100*100.
     * @param path
     * @return
     */
    public static Bitmap setBit(String path,int width,int height){
        BitmapFactory.Options newOpts =  new  BitmapFactory.Options();
        newOpts.inJustDecodeBounds =  true ;
        Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);
        int  w = newOpts.outWidth;
        int  h = newOpts.outHeight;
        //计算出取样率
        newOpts.inSampleSize = computeSampleSize(newOpts, width, height);
        newOpts.inJustDecodeBounds =  false ;
        bitmap = BitmapFactory.decodeFile(path, newOpts);
        return bitmap;
    }

    /**
     * 根据要压缩成的宽和高来计算采样率。
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // 源图片的高度和宽度
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;
            if (height > reqHeight || width > reqWidth) {
                // 计算出实际宽高和目标宽高的比率
                final int heightRatio = Math.round((float) height / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);
                // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
                // 一定都会大于等于目标的宽和高。
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
            return inSampleSize;
    }

}
