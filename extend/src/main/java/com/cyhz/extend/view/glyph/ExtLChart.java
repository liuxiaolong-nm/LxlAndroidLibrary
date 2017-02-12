package com.cyhz.extend.view.glyph;

import android.view.ViewGroup;

import com.cyhz.extend.view.glyph.display.ExtWindowImp;
import com.cyhz.extend.view.glyph.policy.ExtLocationCalculatePolicy;
import com.cyhz.extend.view.glyph.policy.ExtPillarLocationReference;
import com.cyhz.extend.view.glyph.policy.ExtScaleXLocationPolicy;
import com.cyhz.extend.view.glyph.policy.ExtScaleYLocationPolicy;

import java.util.Collections;
import java.util.List;

/**
 * Created by liuxiaolong on 17/1/10.
 */
public class ExtLChart {

    public static void pillar(List<String> xs,List<Float> ys, ViewGroup contain){
        ExtWindowImp windowImp = new ExtWindowImp(contain.getContext());
        contain.addView(windowImp,-1,-1);
        ExtDataSourceDefault dataSourceDefault = new ExtDataSourceDefault(xs,ys);
        ExtLocationCalculatePolicyYImp policyYImp = new ExtLocationCalculatePolicyYImp(ys);
        ExtLocationCalculatePolicyXImp policyXImp = new ExtLocationCalculatePolicyXImp(xs);

        ExtLineConcrere yLineConcrere = new ExtLineConcrere();
        yLineConcrere.setDataSource(dataSourceDefault);
        yLineConcrere.setCalculateYPolicy(policyYImp);
        yLineConcrere.setCalculateXPolicy(policyXImp);
        yLineConcrere.setLocationReference(new ExtPillarLocationReference(yLineConcrere));
        yLineConcrere.setScaleYLocationPolicy(new ExtScaleYLocationPolicy(yLineConcrere));
        yLineConcrere.measure(contain.getWidth(),contain.getHeight(),0);
        yLineConcrere.layout(0,0,contain.getWidth(),contain.getHeight());
        yLineConcrere.draw(windowImp);

        ExtLineConcrere xLineConcrere = new ExtLineConcrere();
        xLineConcrere.setDataSource(dataSourceDefault);
        xLineConcrere.setCalculateYPolicy(policyYImp);
        xLineConcrere.setCalculateXPolicy(policyXImp);
        xLineConcrere.setLocationReference(new ExtPillarLocationReference(yLineConcrere));
        xLineConcrere.setScaleYLocationPolicy(new ExtScaleXLocationPolicy(xLineConcrere));
        xLineConcrere.measure(contain.getWidth(),contain.getHeight(),0);
        xLineConcrere.layout(0,0,contain.getWidth(),contain.getHeight());
        xLineConcrere.draw(windowImp);

        ExtRectConcrere extRectConcrere = new ExtRectConcrere();
        extRectConcrere.setDataSource(dataSourceDefault);
        extRectConcrere.setCalculateYPolicy(policyYImp);
        extRectConcrere.setCalculateXPolicy(policyXImp);
        extRectConcrere.setLocationReference(new ExtPillarLocationReference(yLineConcrere));
        extRectConcrere.measure(contain.getWidth(),contain.getHeight(),0);
        extRectConcrere.layout(0,0,contain.getWidth(),contain.getHeight());
        extRectConcrere.draw(windowImp);
    }

    static class ExtDataSourceDefault implements ExtGlyphDataSourceProvider<String,Float>{

        private List<String> mSourceX;
        private List<Float> mSourceY;

        public ExtDataSourceDefault(List<String> sourceX,List<Float> sourceY) {
            mSourceX = sourceX;
            mSourceY = sourceY;
        }

        @Override
        public List<String> dataSourceX() {
            return mSourceX;
        }

        @Override
        public List<Float> dataSourceY() {
            return mSourceY;
        }
    }

    static class ExtLocationCalculatePolicyYImp implements ExtLocationCalculatePolicy<Float> {

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

    static class ExtLocationCalculatePolicyXImp implements ExtLocationCalculatePolicy<String>{

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
            float unit = contain/mDataSource.size();
            return unit * index + unit/2;
        }
    }
}
