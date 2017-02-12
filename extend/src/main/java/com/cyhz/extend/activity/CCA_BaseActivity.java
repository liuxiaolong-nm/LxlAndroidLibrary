package com.cyhz.extend.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;

public abstract class CCA_BaseActivity extends FragmentActivity
{
    public static final int CAMERA_TYPE=1;
    public static final int ALBUM_TYPE=2;

    public static final int OPEN_CAMERA=10001;

    private String mImgPath;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void setContentView(int layoutResID)
    {
//        FrameLayout layout = (FrameLayout)getWindow().getDecorView();
        FrameLayout layout = new FrameLayout(this);
        layout.setLayoutParams(new FrameLayout.LayoutParams(-1,-1));
        FrameLayout.LayoutParams params0 = new FrameLayout.LayoutParams(-1, headViewHeight());
        params0.topMargin = (hasStatusBar() ? getStatusBarHeight() : 0);
        if (headView() != null) {
            layout.addView(headView(), params0);
        }

        View body = getLayoutInflater().inflate(layoutResID, new FrameLayout(this), false);
//        int bodyHeight = getResources().getDisplayMetrics().heightPixels-headViewHeight() -params0.topMargin;

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -1);
        params.topMargin = (headViewHeight() + params0.topMargin);
        layout.addView(body, params);
        setContentView(layout);
    }

    public abstract boolean hasStatusBar();

    public abstract int headViewHeight();

    public abstract View headView();

    public int getStatusBarHeight()
    {
        int result = 0;

        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = getResources().getDimensionPixelOffset(resourceId);
        }
        return result;
    }
}