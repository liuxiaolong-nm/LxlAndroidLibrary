package com.cyhz.extend.view.glyph.policy;

import com.cyhz.extend.view.glyph.ExtLineConcrere;
import com.lxl.support.util.SupportStringUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liuxiaolong on 17/1/11.
 */
public class ExtScaleXLocationPolicy extends ExtScaleLocationPolicyImp {

    public ExtScaleXLocationPolicy(ExtLineConcrere extXLineConcrere) {
        super(extXLineConcrere);
    }

    @Override
    public float[] scaleLocation() {
        return new float[]{mReference.reference()[1][0],mReference.reference()[1][1],
                mReference.reference()[2][0],mReference.reference()[2][1]};
    }

    @Override
    public Map<String, float[]> separateTextBound() {
        float space = mReference.reference()[1][0];
        int size = mExtLineConcrere.getCalculateXPolicy().separatorCount() - 1;
        LinkedHashMap<String,float[]> linkedHashMap = new LinkedHashMap<>();
        for (int i = 0; i < size; i++){
            String unit = mExtLineConcrere.getDataSource().dataSourceX().get(i);
            float contentWidth = SupportStringUtil.textWidth(unit,mExtLineConcrere.mParameter.getFontSize_x());
            float contentHeight = SupportStringUtil.textSingleHeight(mExtLineConcrere.mParameter.getFontSize_x());
            float left = space + mExtLineConcrere.getCalculateXPolicy().calcullate(mExtLineConcrere.mContentWidth - (space - mExtLineConcrere.mPaddingLeft),unit) + contentWidth/2;
            float top = mReference.reference()[1][1] + mExtLineConcrere.mParameter.getSeparateHeight_x() + mExtLineConcrere.mParameter.getSeparateTopSpace_x();
            float right = left + contentWidth;
            float bottom = top + contentHeight;
            linkedHashMap.put(unit,new float[]{left,top,right,bottom});
        }
        return linkedHashMap;
    }

    @Override
    public float[][] separateLineLocation() {
        float space = mReference.reference()[1][0];
        int size = mExtLineConcrere.getCalculateXPolicy().separatorCount();
        float[][] locations = new float[size][];
        float separatorHeight = (mExtLineConcrere.mContentWidth  - (space - mExtLineConcrere.mPaddingLeft)) / (size-1);
        for (int i = 0; i < size; i++){
            float left = space + separatorHeight * i;
            float top = mReference.reference()[1][1] + mExtLineConcrere.mParameter.getSeparateHeight_x();
            float right = left;
            float bottom = top - mExtLineConcrere.mParameter.getSeparateHeight_x();
            locations[i] = new float[]{left,top,right,bottom};
        }
        return locations;
    }
}
