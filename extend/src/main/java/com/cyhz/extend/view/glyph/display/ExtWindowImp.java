package com.cyhz.extend.view.glyph.display;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxiaolong on 17/1/10.
 */
public class ExtWindowImp extends View implements ExtWindow {
    private static final String TAG = "ExtWindowImp";
    Paint mPoint;
    private List<ExtPoint> mExtPoints = new ArrayList<>();
    private List<ExtLine> mExtLines = new ArrayList<>();
    private List<ExtRect> mExtRects = new ArrayList<>();
    private List<ExtCircle> mExtCircles = new ArrayList<>();
    private List<ExtTxt> mExtTxts = new ArrayList<>();

    public ExtWindowImp(Context context) {
        super(context);
        mPoint = new Paint();
    }

     public ExtWindowImp(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPoint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ExtPoint extPoint : mExtPoints){
            mPoint.setColor(extPoint.color);
            canvas.drawPoint(extPoint.x,extPoint.y,mPoint);
            mPoint.reset();
        }
        for (ExtLine line : mExtLines){
            mPoint.setColor(line.color);
            ExtPoint start = line.points.get(0);
            ExtPoint end = line.points.get(1);
            mPoint.setStrokeWidth(line.thickness);
            canvas.drawLine(start.x,start.y,end.x,end.y,mPoint);
            mPoint.reset();
        }
        for (ExtTxt txt : mExtTxts){
            mPoint.setColor(txt.color);
            mPoint.setTextSize(txt.thickness);
            float startY = txt.bound.points.get(0).y + Math.abs(mPoint.ascent());
            float startX = txt.bound.points.get(0).x;
            canvas.drawText(txt.content,startX,startY,mPoint);
            mPoint.reset();
        }

        for (ExtRect rect : mExtRects){
            Log.e(TAG, "onDraw: "+rect.toString());
            mPoint.setColor(rect.color);
            ExtPoint leftTop = rect.points.get(0);
            ExtPoint rightBottom = rect.points.get(1);
            canvas.drawRect(leftTop.x,leftTop.y,rightBottom.x,rightBottom.y,mPoint);
            mPoint.reset();
        }

        for (ExtCircle circle : mExtCircles){
            mPoint.setColor(circle.color);
            canvas.drawCircle(circle.center.x,circle.center.y,circle.radius,mPoint);
            mPoint.reset();
        }
    }

    @Override
    public void make(ExtPoint point) {
        mExtPoints.add(point);
        invalidate();
    }

    @Override
    public void make(ExtLine line) {
        mExtLines.add(line);
        invalidate();
    }

    @Override
    public void make(ExtRect rect) {
        displayRectEffect(rect);
    }

    @Override
    public void make(ExtCircle circle) {
        mExtCircles.add(circle);
        invalidate();
    }

    @Override
    public void make(ExtTxt txt) {
        mExtTxts.add(txt);
        invalidate();
    }

    private void displayRectEffect(final ExtRect rect){
        Log.e(TAG, "displayRectEffect: "+rect.toString());
        final ExtPoint leftTop = rect.points.get(0);
        final ExtPoint rightBottom = rect.points.get(1);
        final ExtPoint drawLeftTop = ExtPoint.point(leftTop.x,rightBottom.y,rect.color,rect.thickness);
        final ExtPoint drawRightBottom = ExtPoint.point(rightBottom.x,rightBottom.y,rect.color,rect.thickness);
        ExtRect drawRect = ExtRect.rect(drawLeftTop,drawRightBottom,rect.color,rect.thickness);
        mExtRects.add(drawRect);
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float)animation.getAnimatedValue();
                drawLeftTop.y = rightBottom.y - (rightBottom.y - leftTop.y) * value;
                invalidate();
            }
        });
        animator.start();
    }

}
