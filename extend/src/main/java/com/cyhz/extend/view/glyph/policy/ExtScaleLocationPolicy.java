package com.cyhz.extend.view.glyph.policy;

import java.util.Map;

/**
 * Created by liuxiaolong on 17/1/11.
 */
public interface ExtScaleLocationPolicy {

    /**
     * float[]==>{startX,starY,endX,endY};
     * @return
     */
    float[] scaleLocation();

    /**
     * float[][]==>{
     *{left,top,right,bottom},
     *{left,top,right,bottom}
     *}
     * @return
     */
    Map<String,float[]> separateTextBound();

    /**
     * float[][]==>{
     * {startX,starY,endX,endY},
     * {startX,starY,endX,endY}
     * };
     * @return
     */
    float[][] separateLineLocation();

}
