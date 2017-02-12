package com.cyhz.extend.view.cyhzexpandlistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

/*
 *ListView右侧导航面板
 */
public class Side_Bar extends View {

    private boolean showBkg = false;
    private Context context;
    OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 26个字母
//    public static String[] b = { "#", "A", "B", "C", "D", "E", "F", "G", "H",
//            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
//            "V", "W", "X", "Y", "Z" };
    private List<String> charList = new ArrayList<>();
    int choose = -1;
    Paint paint = new Paint();
    private String colorStr ="#333333";

    // 字母的字体大小
    private int sideTextSize = 15;

    public Side_Bar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public Side_Bar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public Side_Bar(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * 设置所显示的字母
     * @param charList
     */
    public void setCharList(List<String> charList){
        this.charList.clear();
        for(String str:charList){
            this.charList.add(str);
        }
        invalidate();
    }

    public void setPaintColor(String colorStr){
        this.colorStr = colorStr;
    }

    /**
     * 设置显示字母的字体大小
     * @param sideTextSize
     */
    public void setSideTextSize(int sideTextSize){
        this.sideTextSize = sideTextSize;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBkg) {
            canvas.drawColor(Color.parseColor("#40000000"));
        }
        //获得Ｖｉｅｗ的高
        int height = getHeight();
        //获得Ｖｉｅｗ的宽
        int width = getWidth();
        //计算得出每一个字体大概的高度
        int singleHeight;
        if(charList.size() == 0){
            singleHeight = 0;
        }else{
            singleHeight = height / charList.size();
        }
        for (int i = 0; i < charList.size(); i++) {
            //设置锯齿
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor(colorStr));
            //设置字体大小
            paint.setTextSize(sideTextSize);
            //点击的字体和26个字母中的任意一个相等就
            if (i == choose) {
                //绘制点击的字体的颜色为蓝色
                paint.setColor(Color.parseColor(colorStr));
                paint.setFakeBoldText(true);
            }
            //得到字体的X坐标
            float xPos = width / 2 - paint.measureText(charList.get(i)) / 2;
            //得到字体的Y坐标
            float yPos = singleHeight * i + singleHeight+3;
            //将字体绘制到面板上
            canvas.drawText(charList.get(i), xPos, yPos, paint);
            //还原画布
            paint.reset();
        }

    }

    /**
     * 点击事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //得到点击的状态
        final int action = event.getAction();
        //点击的Y坐标
        final float y = event.getY();

        final int oldChoose = choose;
        //监听
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        //得到当前的值
        final int c = (int) (y / getHeight() * charList.size());
        //根据点击的状态不同做出不同的处理
        switch (action) {
            //按下已经开始
            case MotionEvent.ACTION_DOWN:
                //将开关设置为true
                showBkg = true;
                if (oldChoose != c && listener != null) {
                    if (c > 0 && c < charList.size()) {
                        //当当前点击的值绑定监听  这个监听在本页面中做的是接口。实际调用是在MainActiv中。也就是说我们调用这个接口会执行MainActivtiy的方法
                        listener.onTouchingLetterChanged(c,charList.get(c));
                        choose = c;
                        //刷新界面
                        invalidate();
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < charList.size()) {
                        listener.onTouchingLetterChanged(c,charList.get(c));
                        choose = c;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBkg = false;
                choose = -1;
                invalidate();
                listener.onComplete();
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     *
     * @author coder
     *
     */
    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(int position, String s);
        public void onComplete();
    }

}