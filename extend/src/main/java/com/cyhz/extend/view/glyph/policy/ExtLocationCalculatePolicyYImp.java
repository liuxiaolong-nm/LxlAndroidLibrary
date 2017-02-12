package com.cyhz.extend.view.glyph.policy;

import java.util.Collections;
import java.util.List;

/**
 * Created by liuxiaolong on 17/1/11.
 */
public class ExtLocationCalculatePolicyYImp implements ExtLocationCalculatePolicy<Float> {
    private List<Float> mDataSource;
    public ExtLocationCalculatePolicyYImp(List<Float> dataSource){
        mDataSource = dataSource;
    }

    @Override
    public Float maxValue() {
        return Collections.max(mDataSource);
    }

    @Override
    public int separatorCount() {
        return 5;
    }

    @Override
    public float calcullate(float contain, Float origin) {
        return origin / maxValue() * contain;
    }
}
