package com.cyhz.extend.view.glyph;

import java.util.Iterator;

/**
 * Created by liuxiaolong on 16/12/11.
 */
public interface ExtGlyphGroup extends ExtGlyph {
    Iterator<? extends ExtGlyph> getIterator();

    ExtGlyphCompositer getCompositer();

    void insert(ExtGlyph glyph, int index);

    void remove(int index);
}
