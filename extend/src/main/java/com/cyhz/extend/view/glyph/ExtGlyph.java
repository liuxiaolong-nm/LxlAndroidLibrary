package com.cyhz.extend.view.glyph;

import com.cyhz.extend.view.glyph.display.ExtWindow;

/**
 * Created by liuxiaolong on 16/12/11.
 */
interface ExtGlyph {

    /**
     * 计算确定图元的大小。
     * @param width 父view建议的宽。
     * @param height 父view建议的高。
     * @param model 计算模式（0为父view制定大小，1为自己确定大小）。
     * @return view的大小。
     */
    int[] measure(int width,int height,int model);

    void layout(int left,int top,int right,int bottom);

    void draw(ExtWindow window);

    ExtGlyph parent();
}
