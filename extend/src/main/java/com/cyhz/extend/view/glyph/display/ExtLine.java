package com.cyhz.extend.view.glyph.display;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liuxiaolong on 16/12/29.
 */
public class ExtLine extends ExtElement {
     List<ExtPoint> points;
     float space; //间隔（虚线的间隔）。

     public static ExtLine line(int color,float thickness,float space,ExtPoint[] points){
          ExtLine extLine = new ExtLine();
          extLine.color = color;
          extLine.thickness = thickness;
          extLine.space = space;
          extLine.points = Arrays.asList(points);
          return extLine;
     }

     @Override
     public String toString() {
          return "{" +
                  " \"points\":" + "\"+" +points+ "\"" +
                  ", \"space\":" + "\"+" +space+ "\"" +
                  "}";
     }
}
