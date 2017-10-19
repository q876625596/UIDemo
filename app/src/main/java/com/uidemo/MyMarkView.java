package com.uidemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2017/7/10 0010.
 */

public class MyMarkView extends View {

    private int viewTextColor;//文字颜色
    private int viewBackColor;//三角标颜色
    private int viewTextSize;//文字大小
    private CharSequence viewText;//文字
    private String viewPosition = "top_right";//三角标位置 top_left，top_right,bottom_left,bottom_right
    private Context mContext;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;


    public MyMarkView(Context context) {
        super(context, null);
        mContext = context;
    }

    public MyMarkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        mContext = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyMarkView, 0, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.MyMarkView_textColor:
                    viewTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyMarkView_backColor:
                    viewBackColor = a.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.MyMarkView_markTextSize:
                    viewTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MyMarkView_text:
                    viewText = a.getString(attr);
                    break;
                case R.styleable.MyMarkView_position:
                    viewPosition = a.getString(attr);
                    break;
            }
        }
        a.recycle();

        /*
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(viewTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(viewText.toString(), 0, viewText.length(), mBound);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mPaint != null) {
            mPaint.setAntiAlias(true);
            //设置三角形的颜色
            mPaint.setColor(viewBackColor);
            //可能会用到px->dp或dp->px
            float density = mContext.getResources().getDisplayMetrics().density;
            Path path = new Path();
            float angle;//文字旋转角度
            switch (viewPosition) {
                case "top_left":
                    //根据四个不同的角来画三角形
                    path.moveTo(0, 0);
                    path.lineTo(getMeasuredWidth(), 0);
                    path.lineTo(0, getMeasuredHeight());
                    path.close();
                    canvas.drawPath(path, mPaint);
                    //设置文字颜色
                    mPaint.setColor(viewTextColor);
                    //根据宽高来计算文字的倾斜角度
                    if (getMeasuredHeight() != getMeasuredWidth()) {
                        double r = Math.atan((double) getMeasuredHeight() / getMeasuredWidth());
                        angle = (float) (r * 180 / Math.PI);
                    } else {
                        angle = 45;
                    }
                    //旋转文字
                    canvas.rotate(-angle, getWidth() / 2, getHeight() / 2);
                    //根据宽高来调整文字位置
                    float moveY_tl = getMeasuredWidth() * (float) Math.sin(angle * Math.PI / 180);
                    float moveX_tl = getMeasuredHeight() * ((float) Math.sin(angle * Math.PI / 180) - (float) Math.sin(45 * Math.PI / 180));
                    canvas.translate(moveX_tl, -moveY_tl / 2);
                    canvas.drawText(viewText.toString(), getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2 + viewTextSize / 3, mPaint);
                    break;
                case "top_right":
                    path.moveTo(0, 0);
                    path.lineTo(getMeasuredWidth(), 0);
                    path.lineTo(getMeasuredWidth(), getMeasuredHeight());
                    path.close();
                    canvas.drawPath(path, mPaint);
                    mPaint.setColor(viewTextColor);
                    //根据宽高来计算文字的倾斜角度
                    if (getMeasuredHeight() != getMeasuredWidth()) {
                        double r = Math.atan((double) getMeasuredHeight() / getMeasuredWidth());

                        angle = (float) (r * 180 / Math.PI);
                    } else {
                        angle = 45;
                    }

                    canvas.rotate(angle, getWidth() / 2, getHeight() / 2);
                    float moveY_tr = getMeasuredWidth() * (float) Math.sin(angle * Math.PI / 180);
                    float moveX_tr = getMeasuredHeight() * ((float) Math.sin(angle * Math.PI / 180) - (float) Math.sin(45 * Math.PI / 180));
                    canvas.translate(-moveX_tr, -moveY_tr / 2);
                    canvas.drawText(viewText.toString(), getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2 + viewTextSize / 3, mPaint);
                    break;
                case "bottom_left":
                    path.moveTo(0, 0);
                    path.lineTo(0, getMeasuredHeight());
                    path.lineTo(getMeasuredWidth(), getMeasuredHeight());
                    path.close();
                    canvas.drawPath(path, mPaint);

                    mPaint.setColor(viewTextColor);
                    //根据宽高来计算文字的倾斜角度
                    if (getMeasuredHeight() != getMeasuredWidth()) {
                        double r = Math.atan((double) getMeasuredHeight() / getMeasuredWidth());
                        angle = (float) (r * 180 / Math.PI);
                    } else {
                        angle = 45;
                    }

                    canvas.rotate(angle, getWidth() / 2, getHeight() / 2);
                    float moveY_bl = getMeasuredWidth() * (float) Math.sin(angle * Math.PI / 180);
                    float moveX_bl = getMeasuredHeight() * ((float) Math.sin(angle * Math.PI / 180) - (float) Math.sin(45 * Math.PI / 180));
                    canvas.translate(moveX_bl, moveY_bl / 2);
                    canvas.drawText(viewText.toString(), getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2 - viewTextSize, mPaint);

                    break;
                case "bottom_right":
                    path.moveTo(getMeasuredWidth(), 0);
                    path.lineTo(0, getMeasuredHeight());
                    path.lineTo(getMeasuredWidth(), getMeasuredHeight());
                    path.close();
                    canvas.drawPath(path, mPaint);

                    mPaint.setColor(viewTextColor);
                    //根据宽高来计算文字的倾斜角度
                    if (getMeasuredHeight() != getMeasuredWidth()) {
                        double r = Math.atan((double) getMeasuredHeight() / getMeasuredWidth());
                        angle = (float) (r * 180 / Math.PI);
                    } else {
                        angle = 45;
                    }

                    canvas.rotate(-angle, getWidth() / 2, getHeight() / 2);
                    float moveY_br = getMeasuredWidth() * (float) Math.sin(angle * Math.PI / 180);
                    float moveX_br = getMeasuredHeight() * ((float) Math.sin(angle * Math.PI / 180) - (float) Math.sin(45 * Math.PI / 180));
                    canvas.translate(-moveX_br, moveY_br / 2);
                    canvas.drawText(viewText.toString(), getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2 - viewTextSize, mPaint);

                    break;
                default:
                    path.moveTo(0, 0);
                    path.lineTo(getMeasuredWidth(), 0);
                    path.lineTo(getMeasuredWidth(), getMeasuredHeight());
                    path.close();
                    canvas.drawPath(path, mPaint);
                    mPaint.setColor(viewTextColor);
                    //根据宽高来计算文字的倾斜角度
                    if (getMeasuredHeight() != getMeasuredWidth()) {
                        double r = Math.atan((double) getMeasuredHeight() / getMeasuredWidth());
                        angle = (float) (r * 180 / Math.PI);
                    } else {
                        angle = 45;
                    }

                    canvas.rotate(angle, getWidth() / 2, getHeight() / 2);
                    float moveY_de = getMeasuredWidth() * (float) Math.sin(angle * Math.PI / 180);
                    float moveX_de = getMeasuredHeight() * ((float) Math.sin(angle * Math.PI / 180) - (float) Math.sin(45 * Math.PI / 180));
                    canvas.translate(-moveX_de, -moveY_de / 2);
                    canvas.drawText(viewText.toString(), getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2 + viewTextSize / 3, mPaint);
                    break;
            }

        } else {
            super.onDraw(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
