package com.cyhz.extend.view.viewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by MyPC on 2015/5/11.
 * 对ViewPager 封装
 */
public class ExViewPager extends FrameLayout {

    private ViewPager mViewPager ;
    private LinearLayout mIndicatorLayout;
    private ViewPageAdapter mViewPageAdapter;
    Builder mBuilder;
    int mTimerIndex;
    private boolean isAutoPlay=false;
    private ScheduledExecutorService scheduledExecutorService;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mViewPager!=null){
                mViewPager.setCurrentItem(mTimerIndex);
            }
        }
    };


    public ExViewPager(Context context) {
        super(context);
        init(context);
    }

    public ExViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mViewPageAdapter = new ViewPageAdapter();
        mViewPager = new ViewPager(context);
        mIndicatorLayout = new LinearLayout(context);
        LayoutParams params = new LayoutParams(-2, -2);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources().getDisplayMetrics());;
        addView(mViewPager, -1, -1);
        addView(mIndicatorLayout, params);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                mTimerIndex = i;
                for (int k = 0; k < mIndicatorLayout.getChildCount(); k++) {
                    mIndicatorLayout.getChildAt(k).setBackgroundResource(mBuilder.getIndicationConversionPic()[0]);
                }
                mIndicatorLayout.getChildAt(i).setBackgroundResource(mBuilder.getIndicationConversionPic()[1]);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void setAutoPlay(boolean isAutoPlay){
        this.isAutoPlay = isAutoPlay;
    }

    private void startTimer(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                mTimerIndex =(mTimerIndex+1)%(mBuilder.getAdsModels().size());
                handler.obtainMessage().sendToTarget();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    /**
     * 关闭计时器
     */
    public void stopTimer(){
        if(scheduledExecutorService!=null){
            scheduledExecutorService.shutdown();
        }
    }

    public void setBuilder(Builder builder) {
        mBuilder = builder;
    }

    public void execute() {
        mViewPageAdapter.setShowView(mBuilder.getAdsModels());
        mViewPager.setAdapter(mViewPageAdapter);
        int value =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(value, value);
        params.rightMargin = value;
        for (View view : mBuilder.getAdsModels()) {
            ImageView indicator = new ImageView(mViewPager.getContext());
            indicator.setBackgroundResource(mBuilder.getIndicationConversionPic()[0]);
            mIndicatorLayout.addView(indicator, params);
        }
        mIndicatorLayout.getChildAt(0).setBackgroundResource(mBuilder.getIndicationConversionPic()[1]);
        if(isAutoPlay){
            startTimer();
        }
        // startTimer();
    }



    public static class Builder {
        List<? extends View> adsModels;
        int[] indicationConversionPic;

        public List<? extends View> getAdsModels() {
            return adsModels;
        }

        public Builder setAdsModels(List<? extends View> adsModels) {
            this.adsModels = adsModels;
            return this;
        }

        public int[] getIndicationConversionPic() {
            return indicationConversionPic;
        }

        public Builder setIndicationConversionPic(int[] indicationConversionPic) {
            this.indicationConversionPic = indicationConversionPic;
            return this;
        }
    }
}
