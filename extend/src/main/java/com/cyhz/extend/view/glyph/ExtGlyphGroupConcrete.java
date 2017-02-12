package com.cyhz.extend.view.glyph;

import com.cyhz.extend.view.glyph.display.ExtWindow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liuxiaolong on 16/12/11.
 */
public abstract class ExtGlyphGroupConcrete extends ExtGlyphConcrete implements ExtGlyphGroup {

    private List<ExtGlyph> mChilds = new ArrayList<>();

    @Override
    public int[] measure(int width, int height,int model) {
        Iterator<ExtGlyph> iterator = getIterator();
        while (iterator.hasNext()){
            iterator.next().measure(width,height,model);
        }
        return super.measure(width, height,model);
    }

    @Override
    public void layout(int left, int top, int right, int bottom) {
        super.layout(left, top, right, bottom);
        getCompositer().compose();
    }

    @Override
    public void draw(ExtWindow window) {
        Iterator<ExtGlyph> iterator = getIterator();
        while (iterator.hasNext()){
            iterator.next().draw(window);
        }
    }

    @Override
    public Iterator<ExtGlyph> getIterator() {
        return new Iterator<ExtGlyph>() {
            int index;
            @Override
            public boolean hasNext() {
                return index >= mChilds.size();
            }

            @Override
            public ExtGlyph next() {
                ExtGlyph glyph = mChilds.get(index);
                index++;
                return glyph;
            }

            @Override
            public void remove() {

            }
        };
    }

    @Override
    public ExtGlyphCompositer getCompositer() {
        return new ExtGlyphCompositerDefault();
    }

    @Override
    public void insert(ExtGlyph glyph, int index) {
        mChilds.add(index,glyph);
    }

    @Override
    public void remove(int index) {
        mChilds.remove(index);
    }
}
