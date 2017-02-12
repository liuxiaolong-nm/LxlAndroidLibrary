package com.cyhz.extend.view.glyph.display;

/**
 * Created by liuxiaolong on 16/12/11.
 */
public interface ExtWindow {

    void make(ExtPoint point);

    void make(ExtLine line);

    void make(ExtRect rect);

    void make(ExtCircle circle);

    void make(ExtTxt txt);
}
