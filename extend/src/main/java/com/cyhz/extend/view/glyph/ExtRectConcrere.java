package com.cyhz.extend.view.glyph;

import android.graphics.Color;

import com.cyhz.extend.view.glyph.display.ExtPoint;
import com.cyhz.extend.view.glyph.display.ExtRect;
import com.cyhz.extend.view.glyph.display.ExtWindow;

import java.util.List;

/**
 * Created by liuxiaolong on 17/1/15.
 */
public class ExtRectConcrere extends ExtGlyphConcrete<String,Float> {

    @Override
    public void draw(ExtWindow window) {
        List<Float> ys = getDataSource().dataSourceY();
        float containY = getLocationReference().reference()[1][1] - getLocationReference().reference()[0][1];
        float containX = getLocationReference().reference()[2][0] - getLocationReference().reference()[1][0];
        float unitX = containX / ys.size();
        for (int i = 0; i < ys.size() ; i ++){
            float calculateY = getCalculateYPolicy().calcullate(containY,ys.get(i));
            float startX = getLocationReference().reference()[1][0] + unitX * i + unitX * 0.25f;
            float startY = getLocationReference().reference()[1][1] - calculateY;
            float endX = startX + unitX * 0.5f;
            float endY = getLocationReference().reference()[1][1];
            ExtPoint satrtPoint = ExtPoint.point(startX,startY,0,0);
            ExtPoint endPoint = ExtPoint.point(endX,endY,0,0);
            ExtRect extRect = ExtRect.rect(satrtPoint,endPoint, Color.BLUE,1);
            window.make(extRect);
        }
    }

}
