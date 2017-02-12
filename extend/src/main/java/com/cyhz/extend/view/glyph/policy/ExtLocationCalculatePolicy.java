package com.cyhz.extend.view.glyph.policy;

/**
 * 图元绘制图形的计算Y轴坐标的策略。
 *
 * liuxiaolong 201601091002.
 *
 */
public interface ExtLocationCalculatePolicy<T>{
    //根据图元的数据源获取最大值。
     T maxValue();

    //设置分隔段总数。
    int separatorCount();

    //计算业务数据在在屏幕中显示的数值。
    float calcullate(float contain,T origin);
}
