package com.zwl.animation.view.bubble;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.nineoldandroids.animation.ValueAnimator;
import com.zwl.animation.App;

/**
 * Created by weilongzhang on 17/3/14.
 */

public class BubbleModel {

    public static final int BUBBLE_SCALE = 0x1;
    public static final int BUBBLE_UP = 0x2;
    private final int w, h;

    private int bubbleType;

    private float radius;
    private float height;
    private int index;
    private int mInnerColor = -1;
    private int mOuterColor = -1;
    private int alpha;

    private boolean end;
    private ValueAnimator valueAnimator;
    private ValueAnimator upAnimator;
    private ValueAnimator alphaAnimator;

    public BubbleModel(int measuredWidth, int measuredHeight, int type, int index) {
        this.bubbleType = type;
        this.w = measuredWidth;
        this.h = measuredHeight;
        this.index = index;

        init();

    }

    private void init() {
        if (bubbleType == BUBBLE_SCALE) {
            valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(1000);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);
            valueAnimator.setStartDelay((long) (Math.random() * 2000));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    radius = (float) valueAnimator.getAnimatedValue();
                }
            });
            valueAnimator.start();
        } else if (bubbleType == BUBBLE_UP) {
            long delay = (long) (Math.random() * 2000);
            upAnimator = ValueAnimator.ofFloat(h, 0);
            upAnimator.setDuration(1000);
            upAnimator.setRepeatCount(ValueAnimator.INFINITE);
            upAnimator.setRepeatMode(ValueAnimator.RESTART);
            upAnimator.setStartDelay(delay);
            upAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    height = (float) valueAnimator.getAnimatedValue();
                }
            });
            upAnimator.start();
            alphaAnimator = ValueAnimator.ofInt(255, 0);
            alphaAnimator.setDuration(1000);
            alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
            alphaAnimator.setRepeatMode(ValueAnimator.RESTART);
            alphaAnimator.setStartDelay(delay);
            alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    alpha = (int) valueAnimator.getAnimatedValue();
                }
            });
            alphaAnimator.start();
        }
    }

    public void stopDraw() {
        this.end = true;
    }


    public void setColors(int mOuterColor, int mInnerColor) {
        this.mInnerColor = mInnerColor;
        this.mOuterColor = mOuterColor;
    }

    public void draw(final Canvas canvas, final Paint mPaint) {

        if (end || mInnerColor == -1 || mOuterColor == -1) {
            if (alphaAnimator != null) {
                alphaAnimator.cancel();
            }
            if (upAnimator != null) {
                upAnimator.cancel();
            }
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            return;
        }

        final int x = w / 10 * index;
        final int y = h;

        if (bubbleType == BUBBLE_SCALE) {
            // outer circle
            mPaint.setColor(App.getConText().getResources().getColor(mOuterColor));
            canvas.drawCircle(x, y, radius * 10 * 2, mPaint);
            // inner circle
            mPaint.setColor(App.getConText().getResources().getColor(mInnerColor));
            canvas.drawCircle(x, y, radius * 10, mPaint);
        } else if (bubbleType == BUBBLE_UP) {
            // outer circle
            mPaint.setColor(App.getConText().getResources().getColor(mOuterColor));
            mPaint.setAlpha(alpha);
            canvas.drawCircle(x, height, 8 * 2, mPaint);
            // inner circle
            mPaint.setColor(App.getConText().getResources().getColor(mInnerColor));
            mPaint.setAlpha(alpha);
            canvas.drawCircle(x, height, 8, mPaint);
        }

    }
}
