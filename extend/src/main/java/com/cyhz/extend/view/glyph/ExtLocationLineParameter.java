package com.cyhz.extend.view.glyph;

import android.graphics.Color;

/**
 * Created by liuxiaolong on 17/1/10.
 */
public class ExtLocationLineParameter {
    private int mFontColor = Color.BLACK;
    private int mFontSize = 16;
    private float mSeparateWidth = 3;//Y轴刻度线的宽度。
    private float mSeparateHeight = 1;//Y轴刻度线的高度。
    private float mSeparateLeftSpace = 5;//Y轴刻度线和文本的间距。
    private int mSeparateColor = Color.BLACK;//Y轴刻度线的颜色。
    private int mScaleColor = Color.BLACK;//Y轴坐标线的颜色。
    private float mScaleWidth = 1;//Y轴坐标轴的宽度。

    private int mFontColor_x = Color.BLACK;
    private int mFontSize_x = 16;
    private float mSeparateWidth_x = 1;//X轴刻度线的宽度。
    private float mSeparateHeight_x = 3;//X轴刻度线的高度。
    private float mSeparateTopSpace_x = 5;//X轴刻度线和文本的间距。
    private int mSeparateColor_x = Color.BLACK;//X轴刻度线的颜色。
    private int mScaleColor_x = Color.BLACK;//X轴坐标线的颜色。
    private float mScaleHeight_x = 1;//X轴坐标轴的宽度。


    public ExtLocationLineParameter(){

    }

    public static ExtLocationLineParameter instance(){
        ExtLocationLineParameter parameter = new ExtLocationLineParameter();
        return parameter;
    }

    public ExtLocationLineParameter(int mFontColor, int mFontSize, float mSeparateWidth, float mSeparateHeight, float mSeparateLeftSpace, int mSeparateColor, int mScaleColor, float mScaleWidth) {
        this.mFontColor = mFontColor;
        this.mFontSize = mFontSize;
        this.mSeparateWidth = mSeparateWidth;
        this.mSeparateHeight = mSeparateHeight;
        this.mSeparateLeftSpace = mSeparateLeftSpace;
        this.mSeparateColor = mSeparateColor;
        this.mScaleColor = mScaleColor;
        this.mScaleWidth = mScaleWidth;
    }

    public int getFontColor() {
        return mFontColor;
    }

    public void setFontColor(int mFontColor) {
        this.mFontColor = mFontColor;
    }

    public int getFontSize() {
        return mFontSize;
    }

    public void setFontSize(int mFontSize) {
        this.mFontSize = mFontSize;
    }

    public float getSeparateWidth() {
        return mSeparateWidth;
    }

    public void setSeparateWidth(float mSeparateWidth) {
        this.mSeparateWidth = mSeparateWidth;
    }

    public float getSeparateHeight() {
        return mSeparateHeight;
    }

    public void setSeparateHeight(float mSeparateHeight) {
        this.mSeparateHeight = mSeparateHeight;
    }

    public float getSeparateLeftSpace() {
        return mSeparateLeftSpace;
    }

    public void setSeparateLeftSpace(float mSeparateLeftSpace) {
        this.mSeparateLeftSpace = mSeparateLeftSpace;
    }

    public int getSeparateColor() {
        return mSeparateColor;
    }

    public void setSeparateColor(int mSeparateColor) {
        this.mSeparateColor = mSeparateColor;
    }

    public int getScaleColor() {
        return mScaleColor;
    }

    public void setScaleColor(int mScaleColor) {
        this.mScaleColor = mScaleColor;
    }

    public float getScaleWidth() {
        return mScaleWidth;
    }

    public void setScaleWidth(float mScaleWidth) {
        this.mScaleWidth = mScaleWidth;
    }



    public float getSeparateWidth_x() {
        return mSeparateWidth_x;
    }

    public void setSeparateWidth_x(float mSeparateWidth_x) {
        this.mSeparateWidth_x = mSeparateWidth_x;
    }

    public float getSeparateHeight_x() {
        return mSeparateHeight_x;
    }

    public void setSeparateHeight_x(float mSeparateHeight_x) {
        this.mSeparateHeight_x = mSeparateHeight_x;
    }

    public float getSeparateTopSpace_x() {
        return mSeparateTopSpace_x;
    }

    public void setSeparateTopSpace_x(float mSeparateTopSpace_x) {
        this.mSeparateTopSpace_x = mSeparateTopSpace_x;
    }

    public int getSeparateColor_x() {
        return mSeparateColor_x;
    }

    public void setSeparateColor_x(int mSeparateColor_x) {
        this.mSeparateColor_x = mSeparateColor_x;
    }

    public int getScaleColor_x() {
        return mScaleColor_x;
    }

    public void setScaleColor_x(int mScaleColor_x) {
        this.mScaleColor_x = mScaleColor_x;
    }

    public float getScaleHeight_x() {
        return mScaleHeight_x;
    }

    public void setScaleHeight_x(float mScaleHeight_x) {
        this.mScaleHeight_x = mScaleHeight_x;
    }

    public int getFontColor_x() {
        return mFontColor_x;
    }

    public void setFontColor_x(int mFontColor_x) {
        this.mFontColor_x = mFontColor_x;
    }

    public int getFontSize_x() {
        return mFontSize_x;
    }

    public void setFontSize_x(int mFontSize_x) {
        this.mFontSize_x = mFontSize_x;
    }
}
