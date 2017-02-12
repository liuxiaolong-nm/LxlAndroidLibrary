package com.cyhz.extend.view.glyph;

import java.util.Iterator;

/**
 * Created by liuxiaolong on 16/12/11.
 */
public class ExtGlyphCompositerDefault implements ExtGlyphCompositer {
    private ExtGlyphGroup mGlyphGroup;

    @Override
    public void compose() {
        Iterator<? extends ExtGlyph> iterator = mGlyphGroup.getIterator();
        ExtGlyphGroupConcrete glyphGroupConcrete = (ExtGlyphGroupConcrete)mGlyphGroup;
        while (iterator.hasNext()){
            int[] wh = iterator.next().measure(0,0,0);
            iterator.next().layout(glyphGroupConcrete.mLeft,
                    glyphGroupConcrete.mTop,
                    Math.min(glyphGroupConcrete.mWidth,wh[0])+glyphGroupConcrete.mLeft,
                    Math.min(glyphGroupConcrete.mHeight,wh[1])+glyphGroupConcrete.mTop);
        }
    }

    @Override
    public void setComposition(ExtGlyphGroup glyph) {
        mGlyphGroup = glyph;
    }
}
