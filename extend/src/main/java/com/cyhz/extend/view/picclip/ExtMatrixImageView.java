package com.cyhz.extend.view.picclip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by liuxiaolong on 16/12/18.
 */
public class ExtMatrixImageView extends ImageView {

    private Bitmap bitmap;
    public ExtMatrixImageView(Context context) {
        super(context);
    }

    public ExtMatrixImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        //super.setImageBitmap(bm);
        bitmap = bm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
        //画经过Matrix变化后的图
        canvas.drawBitmap(bitmap, getImageMatrix(), null);
        //super.onDraw(canvas);

    }
}
