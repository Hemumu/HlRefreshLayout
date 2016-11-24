package com.helin.hlrefreshlayout.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by helin on 2016/9/8 15:17.
 */
public class SunshineView extends View {
    private int mWidth;
    private int mHeight;

    //太阳光芒长度
    private static final int SUNWITH = 10;
    //太阳光芒距圆距离
    private static final int SUNMARG = 10;
    //太阳光芒旋转角度
    private double offsetSpin = 0;

    private Paint mPaint = new Paint();
    private ValueAnimator valueAnimator;


    public SunshineView(Context context) {
        super(context);
    }

    public SunshineView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public SunshineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        widthMeasureSpec = heightMeasureSpec = w > h ? MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY) : MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        //将画布的原点移到中心
        canvas.translate(mWidth / 2, mHeight / 2);
        mPaint.setColor(Color.parseColor("#FFA500"));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);

        float radius = (float) (mHeight / 2 * 0.5);

        //绘制太阳
        canvas.drawCircle(0, 0, radius, mPaint);

        double startX = Math.cos(Math.toRadians(offsetSpin)) * (radius + SUNMARG);
        double startY = Math.sin(Math.toRadians(offsetSpin)) * (radius + SUNMARG);

        double endX = Math.cos(Math.toRadians(offsetSpin)) * (radius + SUNMARG + SUNWITH);
        double endY = Math.sin(Math.toRadians(offsetSpin)) * (radius + SUNMARG + SUNWITH);


        //绘制太阳光芒
        for (int i = 0; i <= 360; i += 30) {
            canvas.drawLine((float) startX, (float) startY, (float) endX, (float) endY,mPaint);
            canvas.rotate(30);
        }

        canvas.restore();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        startAnim();
    }

    public void startAnim(){
        valueAnimator  = ValueAnimator.ofFloat(0, 360);
        valueAnimator.setDuration(2*1000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offsetSpin= Float.parseFloat( animation.getAnimatedValue().toString());
                invalidate();
            }
        });
        valueAnimator.start();

    }

    public void stopAnim() {
        if(valueAnimator!=null){
            valueAnimator.cancel();
        }
    }
}
