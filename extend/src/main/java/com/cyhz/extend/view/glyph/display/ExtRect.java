package com.cyhz.extend.view.glyph.display;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxiaolong on 16/12/29.
 */
public class ExtRect extends ExtElement{
    //矩形是通过左上角和右下角的两个点组成的。
    protected List<ExtPoint> points = new ArrayList<>();

    public static ExtRect rect(ExtPoint leftTop,ExtPoint rightBottom,int color,float thickness){
        ExtRect extRect = new ExtRect();
        extRect.points.add(leftTop);
        extRect.points.add(rightBottom);
        extRect.color = color;
        extRect.thickness = thickness;
        return extRect;
    }

    @Override
    public String toString() {
        return "{" +
                " \"points\":" + "\"+"+ points+ "\"" +
                "}";
    }
}
