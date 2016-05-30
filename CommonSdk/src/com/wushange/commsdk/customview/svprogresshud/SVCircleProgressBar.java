package com.wushange.commsdk.customview.svprogresshud;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wushange.commsdk.R;

public class SVCircleProgressBar extends View {
    private Context mContext;
    private Paint paint;
    private int roundColor;
    private int roundProgressColor;
    private float roundWidth;
    private int max;
    private int progress;
    private int style;
    public static final int STROKE = 0;
    public static final int FILL = 1;

    public SVCircleProgressBar(Context context) {
        this(context, null);
        this.mContext = context;

    }

    public SVCircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public SVCircleProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;

        paint = new Paint();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SVCircleProgressBar);

        roundColor = mTypedArray.getColor(R.styleable.SVCircleProgressBar_roundColor, Color.BLUE);
        roundProgressColor = mTypedArray.getColor(R.styleable.SVCircleProgressBar_roundProgressColor,
                Color.GRAY);
        roundWidth = mTypedArray.getDimension(R.styleable.SVCircleProgressBar_roundWidth, 5);
        max = mTypedArray.getInteger(R.styleable.SVCircleProgressBar_max, 100);
        style = mTypedArray.getInt(R.styleable.SVCircleProgressBar_style, 0);

        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centre = getWidth() / 2;
        int radius = (int) (centre - roundWidth / 2);
        paint.setAntiAlias(true);
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        canvas.drawCircle(centre, centre, radius, paint);


        paint.setStrokeWidth(roundWidth);
        paint.setColor(roundProgressColor);
        RectF oval = new RectF(centre - radius, centre - radius, centre
                + radius, centre + radius);

        switch (style) {
            case STROKE: {
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, 270, 360 * progress / max, false, paint);
                break;
            }
            case FILL: {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (progress != 0)
                    canvas.drawArc(oval, 270, 360 * progress / max, true, paint);
                break;
            }
        }

    }

    public synchronized int getMax() {
        return max;
    }

    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    public synchronized int getProgress() {
        return progress;
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }

    }

    public int getCircleColor() {
        return roundColor;
    }

    public void setCircleColor(int circleColor) {
        this.roundColor = circleColor;
    }

    public int getCircleProgressColor() {
        return roundProgressColor;
    }

    public void setCircleProgressColor(int circleProgressColor) {
        this.roundProgressColor = circleProgressColor;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

}