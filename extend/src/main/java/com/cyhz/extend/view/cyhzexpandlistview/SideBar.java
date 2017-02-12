package com.cyhz.extend.view.cyhzexpandlistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPC on 2015/8/3.
 */
public class SideBar extends View {

    private List<String> letterList ;
    private Paint paint;
    private SideBarListener listener;
    private boolean isHasBg=false;
    private int choosePos=-1;


    public SideBar(Context context) {
        super(context);
        init(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        paint = new Paint();
        letterList = new ArrayList<>();
    }

    public void setLetterList(List<String> letterList){
//        this.letterList.add(0,"#");
        this.letterList.addAll(letterList);
        invalidate();
    }

    public void setListener(SideBarListener listener){
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        int height = getHeight();
        int width = getWidth();
        if(letterList.size()<1){
            return;
        }
        int singleHeight = height/letterList.size();
        if(isHasBg){
            canvas.drawColor(Color.parseColor("#40000000"));
        }
        for(int i=0;i<letterList.size();i++){
            if(choosePos==i){
                paint.setColor(Color.parseColor("#1081e0"));
                paint.setFakeBoldText(true);
            }
            paint.setAntiAlias(true);
            paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,13,getResources().getDisplayMetrics()));
            float xPoint = width/2-paint.measureText(letterList.get(i))/2;
            float fontHeight = Math.abs(paint.getFontMetrics().ascent)+Math.abs(paint.getFontMetrics().descent);
            float yPoint = singleHeight*i+fontHeight;
            canvas.drawText(letterList.get(i),xPoint,yPoint,paint);
            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();

        int pos = (int)((y/getHeight())*letterList.size());
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if(listener!=null){
                    isHasBg = true;
                    choosePos=pos;
                    listener.select(pos,letterList.get(pos));
                }
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                if(listener!=null){
                    listener.select(pos,letterList.get(pos));
                    choosePos=pos;
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:


                if(listener!=null){
                    listener.onComplete();
                }

                choosePos=-1;
                isHasBg=false;
                invalidate();
                break;
        }
        return true;
    }

    public interface SideBarListener{
        void select(int pos, String str);
        void onComplete();
    }
}
