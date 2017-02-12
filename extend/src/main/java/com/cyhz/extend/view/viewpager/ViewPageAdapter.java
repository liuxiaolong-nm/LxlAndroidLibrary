package com.cyhz.extend.view.viewpager;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxiaolong on 14-12-30.
 */
public class ViewPageAdapter extends PagerAdapter {

    private List<View> list = new ArrayList<View>();
    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT);

    public void setShowView(List<? extends View> list) {
        this.list.addAll(list);
    }

    public void setShowView(View view) {
        this.list.add(view);
    }

    public List<View> getDatas() {
        return list;
    }

    public void clear() {
        list.clear();
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        if (arg1 < list.size()) {
            ((ViewPager) arg0).removeView(list.get(arg1));
        }
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(list.get(arg1), params);
        return list.get(arg1);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }
}
