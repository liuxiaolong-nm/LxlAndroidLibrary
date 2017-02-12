package com.cyhz.extend.view.glyph.display;

/**
 * Created by liuxiaolong on 16/12/29.
 */
public class ExtPoint extends ExtElement {
    public float x,y;

    public static ExtPoint point(float x,float y,int color,float thickness){
        ExtPoint point = new ExtPoint();
        point.x = x;
        point.y = y;
        point.color = color;
        point.thickness = thickness;
        return point;
    }

    @Override
    public String toString() {
        return "{" +
                " \"x\":" + "\"+" +x+ "\"" +
                ", \"y\":" + "\"+" +y+ "\"" +
                "}";
    }
}
