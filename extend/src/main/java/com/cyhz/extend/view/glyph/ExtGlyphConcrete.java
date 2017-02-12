package com.cyhz.extend.view.glyph;

import com.cyhz.extend.view.glyph.policy.ExtLocationCalculatePolicy;
import com.cyhz.extend.view.glyph.policy.ExtLocationReference;

/**
 * Created by liuxiaolong on 16/12/11.
 */
public abstract class ExtGlyphConcrete<T,E> implements ExtGlyph {

    public int mWidth,mHeight;
    public int mLeft,mTop,mRight,mBottom;
    public float mPaddingLeft,mPaddingTop,mPaddingRight,mPaddingBottom,mContentHeight,mContentWidth;
    protected ExtGlyph mParent;
    int mColor;
    private ExtGlyphDataSourceProvider<T,E> mDataSource;

    private ExtLocationCalculatePolicy<T> mCalculateXPolicy;

    private ExtLocationCalculatePolicy<E> mCalculateYPolicy;

    private ExtLocationReference mLocationReference;

    public void setColor(int color){
        mColor = color;
    }

    @Override
    public int[] measure(int width, int height, int model)
    {
        if (model == 0){
            mWidth = width;
            mHeight = height;
            mPaddingLeft = mWidth * 0.05f;
            mPaddingTop = mHeight * 0.05f;
            mPaddingRight = mWidth * 0.05f;
            mPaddingBottom = mHeight * 0.05f;
            mContentHeight = mHeight - mPaddingTop - mPaddingBottom;
            mContentWidth = mWidth - mPaddingLeft - mPaddingRight;
        }
        return new int[]{mWidth,mHeight};
    }

    @Override
    public void layout(int left, int top, int right, int bottom) {
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
    }

    @Override
    public ExtGlyph parent() {
        return mParent;
    }

    public ExtGlyphDataSourceProvider<T,E> getDataSource() {
        return mDataSource;
    }

    public void setDataSource(ExtGlyphDataSourceProvider<T,E> mDataSource) {
        this.mDataSource = mDataSource;
    }

    public ExtLocationCalculatePolicy<T> getCalculateXPolicy() {
        return mCalculateXPolicy;
    }

    public void setCalculateXPolicy(ExtLocationCalculatePolicy<T> mCalculateXPolicy) {
        this.mCalculateXPolicy = mCalculateXPolicy;
    }

    public ExtLocationCalculatePolicy<E> getCalculateYPolicy() {
        return mCalculateYPolicy;
    }

    public void setCalculateYPolicy(ExtLocationCalculatePolicy<E> mCalculatePolicy) {
        this.mCalculateYPolicy = mCalculatePolicy;
    }

    public ExtLocationReference getLocationReference() {
        return mLocationReference;
    }

    public void setLocationReference(ExtLocationReference mLocationReference) {
        this.mLocationReference = mLocationReference;
    }

}
