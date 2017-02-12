package com.cyhz.extend.view.picclip;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by liuxiaolong on 16/12/17.
 */
public class ExtPicClipBean {
    private float clipWidth,clipHeight;
    private int clipDecorateImage;
    private Context context;
    private Bitmap img;

    public Context getContext() {
        if (context == null){
            throw new RuntimeException("ExPicClipBean中缺少context！");
        }
        return context;
    }

    public ExtPicClipBean setContext(Context context) {
        this.context = context;
        return this;
    }

    public Bitmap getImg() {
        return img;
    }

    public ExtPicClipBean setImg(Bitmap img) {
        this.img = img;
        return this;
    }

    public float getClipWidth() {
        return clipWidth;
    }

    public ExtPicClipBean setClipWidth(float clipWidth) {
        this.clipWidth = clipWidth;
        return this;
    }

    public float getClipHeight() {
        return clipHeight;
    }

    public ExtPicClipBean setClipHeight(float clipHeight) {
        this.clipHeight = clipHeight;
        return this;
    }

    public int getClipDecorateImage() {
        return clipDecorateImage;
    }

    public ExtPicClipBean setClipDecorateImage(int clipDecorateImage) {
        this.clipDecorateImage = clipDecorateImage;
        return this;
    }

    public static ExtPicClipBean bean(){
        ExtPicClipBean clipBean = new ExtPicClipBean();
        return clipBean;
    }

}
