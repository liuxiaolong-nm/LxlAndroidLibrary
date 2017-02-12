package com.cyhz.extend.view.glyph.policy;

import com.cyhz.extend.view.glyph.ExtGlyphConcrete;
import com.cyhz.extend.view.glyph.ExtLocationLineParameter;
import com.lxl.support.util.SupportStringUtil;

/**
 * Created by liuxiaolong on 17/1/11.
 */
public class ExtPillarLocationReference implements ExtLocationReference {
    private ExtGlyphConcrete<String,Float> mGlyphConcrete;

    public ExtPillarLocationReference(ExtGlyphConcrete<String,Float> glyphConcrete){
        mGlyphConcrete = glyphConcrete;
    }

    @Override
    public float[][] reference() {
        ExtLocationLineParameter parameter = ExtLocationLineParameter.instance();
        float[][] references = new float[3][];
        float max = mGlyphConcrete.getCalculateYPolicy().maxValue();
        float maxValueWidth = SupportStringUtil.textWidth(max+"",parameter.getFontSize());
        float contentHeightX = SupportStringUtil.textSingleHeight(parameter.getFontSize());
        float contentHeightY = SupportStringUtil.textSingleHeight(parameter.getFontSize_x());
        float x = mGlyphConcrete.mPaddingLeft+maxValueWidth+parameter.getSeparateLeftSpace()+parameter.getSeparateWidth();
        float y = mGlyphConcrete.mPaddingTop - contentHeightX/2;
        float y1 = mGlyphConcrete.mPaddingTop + mGlyphConcrete.mContentHeight - contentHeightY - parameter.getSeparateTopSpace_x() - parameter.getSeparateHeight_x();
        float x2 = mGlyphConcrete.mPaddingLeft + mGlyphConcrete.mContentWidth;
        references[0] = new float[]{x,y};
        references[1] = new float[]{x,y1};
        references[2] = new float[]{x2,y1};
        return references;
    }
}
