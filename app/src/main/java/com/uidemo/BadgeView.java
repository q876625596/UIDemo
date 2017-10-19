package com.uidemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class BadgeView extends View {

    private Context mContext;
    private int badgeBackColor;
    private int badgeTextColor;
    private int badgeTextSize;
    private int badgeTextPadding;
    private CharSequence badgeText = "";
    private String badgePosition = "center"; //top_left,top_right,bottom_left,bottom_right,center

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    public BadgeView(Context context) {
        super(context);
    }

    public BadgeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BadgeView, 0, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.BadgeView_badgeBackColor:
                    badgeBackColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.BadgeView_badgeTextColor:
                    badgeTextColor = a.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.BadgeView_badgeTextSize:
                    badgeTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10f, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.BadgeView_badgeTextPadding:
                    badgeTextPadding = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 2f, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.BadgeView_badgeText:
                    badgeText = a.getString(attr);
                    break;
                case R.styleable.BadgeView_badgePosition:
                    badgePosition = a.getString(attr);
                    break;
            }
        }
        a.recycle();

        /*
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(badgeTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(badgeText.toString(), 0, badgeText.length(), mBound);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPaint != null) {
            mPaint.setAntiAlias(true);
            mPaint.setColor(badgeBackColor);
            canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, badgeTextSize / 2 + badgeTextPadding, mPaint);
            switch (badgePosition) {
                case "center":
                    mPaint.setColor(badgeTextColor);
                    canvas.drawText(badgeText.toString(), getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
                    break;
                case "top_left":
                    break;
                case "top_right":
                    break;
                case "bottom_left":
                    break;
                case "bottom_right":
                    break;
                default:
                    mPaint.setColor(badgeTextColor);
                    canvas.drawText(badgeText.toString(), getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
                    break;
            }

        } else {
            super.onDraw(canvas);
        }
    }
}
