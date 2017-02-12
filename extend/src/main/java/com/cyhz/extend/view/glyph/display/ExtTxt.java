package com.cyhz.extend.view.glyph.display;

/**
 * Created by liuxiaolong on 17/1/6.
 */
public class ExtTxt extends ExtElement {
    public String content;
    public ExtRect bound;

    @Override
    public String toString() {
        return "{" +
                " \"content\":" + "\"" + content + "\"" +
                ", \"bound\":" + "\"+" +bound+ "\"" +
                "}";
    }
}
