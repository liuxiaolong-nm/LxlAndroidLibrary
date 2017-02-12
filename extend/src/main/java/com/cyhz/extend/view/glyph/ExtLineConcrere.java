package com.cyhz.extend.view.glyph;

import com.cyhz.extend.view.glyph.display.ExtLine;
import com.cyhz.extend.view.glyph.display.ExtPoint;
import com.cyhz.extend.view.glyph.display.ExtRect;
import com.cyhz.extend.view.glyph.display.ExtTxt;
import com.cyhz.extend.view.glyph.display.ExtWindow;
import com.cyhz.extend.view.glyph.policy.ExtScaleLocationPolicy;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by liuxiaolong on 17/1/9.
 */
public class ExtLineConcrere extends ExtGlyphConcrete<String,Float> {

    public ExtLocationLineParameter mParameter = ExtLocationLineParameter.instance();

    protected ExtScaleLocationPolicy mScaleYLocationPolicy;

    @Override
    public void draw(ExtWindow window) {
        this.drawSeparateText(window);
        this.drawSeparateLine(window);
        this.drawScaleLine(window);
    }

    public void setScaleYLocationPolicy(ExtScaleLocationPolicy mScaleYLocationPolicy) {
        this.mScaleYLocationPolicy = mScaleYLocationPolicy;
    }

    /**
     * 画刻度的文本。
     * @param window
     */
    protected void drawSeparateText(ExtWindow window){
        Map<String,float[]> textMap = mScaleYLocationPolicy.separateTextBound();
        Iterator<Map.Entry<String,float[]>> setIterator = textMap.entrySet().iterator();
        while (setIterator.hasNext()){
            Map.Entry<String,float[]> entry = setIterator.next();
            ExtTxt extTxt = new ExtTxt();
            extTxt.color = mParameter.getFontColor();
            extTxt.thickness = mParameter.getFontSize();
            extTxt.content = entry.getKey();
            float[] value = entry.getValue();
            extTxt.bound = ExtRect.rect(ExtPoint.point(value[0],value[1],0,0),ExtPoint.point(value[2],value[3],0,0),0,0);
            window.make(extTxt);
        }
    }

    /**
     * 画刻度线。
     * @param window
     */
    protected void drawSeparateLine(ExtWindow window){
        float[][] locas = mScaleYLocationPolicy.separateLineLocation();
        for (int i = 0; i < locas.length; i++){
            ExtLine extLine = ExtLine.line(mParameter.getSeparateColor(),mParameter.getSeparateHeight(),0,new ExtPoint[]{
                    ExtPoint.point(locas[i][0],locas[i][1],0,0),ExtPoint.point(locas[i][2],locas[i][3],0,0)
            });
            window.make(extLine);
        }
    }

    /**
     * 画y轴。
     * @param window
     */
    private void drawScaleLine(ExtWindow window){
        float[] locas = mScaleYLocationPolicy.scaleLocation();
        ExtLine extLine = ExtLine.line(mParameter.getScaleColor(),mParameter.getScaleWidth(),0,new ExtPoint[]{
                ExtPoint.point(locas[0],locas[1],0,0),ExtPoint.point(locas[2],locas[3],0,0)
        });
        window.make(extLine);
    }

}
