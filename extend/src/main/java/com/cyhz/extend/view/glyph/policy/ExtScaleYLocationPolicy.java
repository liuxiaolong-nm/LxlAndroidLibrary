package com.cyhz.extend.view.glyph.policy;

import com.cyhz.extend.view.glyph.ExtLineConcrere;
import com.lxl.support.util.SupportStringUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liuxiaolong on 17/1/11.
 */
public class ExtScaleYLocationPolicy extends ExtScaleLocationPolicyImp{

    public ExtScaleYLocationPolicy(ExtLineConcrere extXLineConcrere) {
        super(extXLineConcrere);
    }

    @Override
    public float[] scaleLocation() {
        return new float[]{mReference.reference()[0][0],mReference.reference()[0][1],
                mReference.reference()[1][0],mReference.reference()[1][1]};
    }

    @Override
    public Map<String,float[]> separateTextBound() {
        LinkedHashMap<String,float[]> linkedHashMap = new LinkedHashMap<>();
        float max = mExtLineConcrere.getCalculateYPolicy().maxValue();
        float maxValueWidth = SupportStringUtil.textWidth(max+"",mExtLineConcrere.mParameter.getFontSize());
        int size = mExtLineConcrere.getCalculateYPolicy().separatorCount();
        float separatorHeight = (mReference.reference()[1][1] - mReference.reference()[0][1]) / (size-1);
        for (int i = 0; i < size; i++){
            float unit = max/(size-1) * i;
            float contentWidth = SupportStringUtil.textWidth(unit+"",mExtLineConcrere.mParameter.getFontSize());
            float contentHeight = SupportStringUtil.textSingleHeight(mExtLineConcrere.mParameter.getFontSize());
            float left = mReference.reference()[1][0] -mExtLineConcrere.mParameter.getSeparateLeftSpace() - mExtLineConcrere.mParameter.getSeparateWidth() -(maxValueWidth-contentWidth)/2 - contentWidth;
            float top = mReference.reference()[1][1] - contentHeight/2 - separatorHeight * i;
            float right = left + contentWidth;
            float bottom = top + contentHeight;
            linkedHashMap.put(unit+"",new float[]{left,top,right,bottom});
        }
        return linkedHashMap;
    }

    @Override
    public float[][] separateLineLocation() {
        int size = mExtLineConcrere.getCalculateYPolicy().separatorCount();
        float[][] locations = new float[size][];
        float separatorHeight = (mReference.reference()[1][1] - mReference.reference()[0][1]) / (size-1);
        for (int i = 0; i < size; i++){
            float left = mReference.reference()[1][0] - mExtLineConcrere.mParameter.getSeparateWidth();
            float top =  mReference.reference()[1][1] - mExtLineConcrere.mParameter.getSeparateHeight()/2 - separatorHeight * i;
            float right = left +mExtLineConcrere.mParameter.getSeparateWidth();
            float bottom = top;
            locations[i] = new float[]{left,top,right,bottom};
        }
        return locations;
    }

}
