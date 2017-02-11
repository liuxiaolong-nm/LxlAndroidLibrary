package com.cyhz.support.util;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by liuxiaolong on 17/1/9.
 */
public class SupportStringUtil {

    /**
     * 计算文字宽度。
     *
     * @param target 文本。
     * @param size   字号。
     * @return
     */
    public static int textWidth(String target, int size) {
        Rect rect = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(size);
        paint.getTextBounds(target, 0, target.length(), rect);
        return rect.width();
    }

    /**
     * 计算单个文字的高度。。
     *
     * @param size 字号。
     * @return
     */
    public static int textSingleHeight(int size) {
        Paint paint = new Paint();
        paint.setTextSize(size);
        return Math.round(Math.abs(paint.getFontMetrics().top) + Math.abs(paint.getFontMetrics().bottom));
    }

}
