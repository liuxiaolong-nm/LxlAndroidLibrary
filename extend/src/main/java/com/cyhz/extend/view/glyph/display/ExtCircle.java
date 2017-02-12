package com.cyhz.extend.view.glyph.display;

/**
 * Created by liuxiaolong on 16/12/29.
 */
public class ExtCircle extends ExtElement{
    ExtPoint center;
    float radius;

    @Override
    public String toString() {
        return "{" +
                " \"center\":" + "\"+" +center+ "\"" +
                ", \"radius\":" + "\"+" +radius+ "\"" +
                "}";
    }
}
