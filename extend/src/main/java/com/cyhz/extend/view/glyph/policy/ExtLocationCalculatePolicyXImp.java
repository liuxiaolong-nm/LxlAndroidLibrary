package com.cyhz.extend.view.glyph.policy;

import java.util.List;

/**
 * Created by liuxiaolong on 17/1/11.
 */
public class ExtLocationCalculatePolicyXImp implements ExtLocationCalculatePolicy<String> {

    private List<String> mDataSource;
    public ExtLocationCalculatePolicyXImp(List<String> dataSource){
        mDataSource = dataSource;
    }

    @Override
    public String maxValue() {
        return mDataSource.get(0);
    }

    @Override
    public int separatorCount() {
        return mDataSource.size() + 1;
    }

    @Override
    public float calcullate(float contain, String origin) {
        int index = mDataSource.indexOf(origin);
        float unit = contain/(separatorCount() - 1);
        return unit * index + unit/2;
    }
}
