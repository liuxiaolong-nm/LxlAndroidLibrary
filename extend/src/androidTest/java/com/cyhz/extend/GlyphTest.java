package com.cyhz.extend;

import android.animation.ValueAnimator;
import android.test.AndroidTestCase;
import android.util.Log;

import com.cyhz.extend.view.glyph.ExtGlyphDataSourceProvider;
import com.cyhz.extend.view.glyph.ExtLineConcrere;
import com.cyhz.extend.view.glyph.display.ExtCircle;
import com.cyhz.extend.view.glyph.display.ExtLine;
import com.cyhz.extend.view.glyph.display.ExtPoint;
import com.cyhz.extend.view.glyph.display.ExtRect;
import com.cyhz.extend.view.glyph.display.ExtTxt;
import com.cyhz.extend.view.glyph.display.ExtWindow;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liuxiaolong on 17/1/9.
 */
public class GlyphTest extends AndroidTestCase {
    private static final String TAG = "GlyphTest";

    public void testExtYLineConcrere(){
        ExtGlyphDataSourceProvider dataSourceProvider = new ExtGlyphDataSourceProvider<String,Float>() {
            @Override
            public List<String> dataSourceX() {
                return Arrays.asList(new String[]{"1","2","3"});
            }

            @Override
            public List<Float> dataSourceY() {
                return Arrays.asList(new Float[]{new Float(10),new Float(20),new Float(30)});
            }

        };

        ExtLineConcrere yLine = new ExtLineConcrere();
        yLine.setDataSource(dataSourceProvider);

        yLine.measure(100,100,0);
        yLine.layout(0,0,100,100);
        yLine.draw(new ExtWindow() {
            @Override
            public void make(ExtPoint point) {
                Log.e(TAG, "make: ExtPoint"+point);
            }

            @Override
            public void make(ExtLine line) {
                Log.e(TAG, "make: ExtLine"+line);
            }

            @Override
            public void make(ExtRect rect) {
                Log.e(TAG, "make: ExtRect"+rect);
            }

            @Override
            public void make(ExtCircle circle) {
                Log.e(TAG, "make: ExtCircle"+circle);
            }

            @Override
            public void make(ExtTxt txt) {
                Log.e(TAG, "make: ExtTxt"+txt);
            }
        });
    }

    public void testExtXLineConcrere() {
        ValueAnimator animator = new ValueAnimator().ofFloat(0,100);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e(TAG, "onAnimationUpdate: "+animation.getAnimatedValue());
            }
        });
        animator.start();
    }
}
