package com.cyhz.extend.view.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cyhz.extend.R;

/**
 * Created by liuxiaolong on 16/12/12.
 */
public class RotateTextView extends TextView {

    private float mAngle;

    public RotateTextView(Context context) {
        super(context);
    }

    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotateTextView);
        float angle = typedArray.getFloat(R.styleable.RotateTextView_angle,0);
        mAngle = angle;
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(mAngle, getMeasuredWidth()/2, getMeasuredHeight()/2);
        super.onDraw(canvas);
    }
}
