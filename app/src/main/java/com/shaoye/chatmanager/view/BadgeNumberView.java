package com.shaoye.chatmanager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.shaoye.chatmanager.R;

/**
 * @date: 开元4719年5月2日
 * @author: shaoye
 * @description: 消息通知那个红点，随便写了个，凑活用
 */
public class BadgeNumberView extends View {
    private static final String TAG = "BadgeView";

    private static final float PADDING_X = 12;
    private static final float PADDING_Y = 10;

    private float mTextSize;
    private String mText;

    private Rect mTextRect;
    private Paint mTextPaint;
    private Paint mBackPaint;

    public BadgeNumberView(Context context) {
        this(context, null);
    }

    public BadgeNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BadgeView, defStyleAttr, 0);
        mTextSize = array.getDimensionPixelOffset(R.styleable.BadgeView_textSize,
                context.getResources().getDimensionPixelOffset(R.dimen.default_text_size));
        mText = array.getString(R.styleable.BadgeView_text);
        array.recycle();

        mTextRect = new Rect();
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        Log.e(TAG, "BadgeNumberView:1 " + mTextSize + "  " + mText);
        if (!TextUtils.isEmpty(mText)) {
            mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
        }
        Log.e(TAG, "BadgeNumberView:2 " + mTextSize + "  " + mText);
        mTextPaint.setColor(Color.WHITE);
        mBackPaint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (TextUtils.isEmpty(mText)) {
            return;
        }
        if (mText.length() == 1) {
            int max = Math.max(mTextRect.width(), mTextRect.height());
            float offset = max / 2f + PADDING_X;   //圆心的（x,y）和 半径大小
            setMeasuredDimension((int) (offset * 2), (int) (offset * 2));
        } else {
            setMeasuredDimension((int) (mTextRect.width() + PADDING_X * 2), (int) (mTextRect.height() + PADDING_Y * 2));
        }
    }

    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mText.length() == 1) {
            int max = Math.max(mTextRect.width(), mTextRect.height());
            float offset = max / 2f + PADDING_X;   //圆心的（x,y）和 半径大小
            float textX = offset - mTextRect.width() / 2f;
            float textY = offset + mTextRect.height() / 2f - PADDING_X / 4f;    //y方向来点偏移，不然看起来不太居中
            canvas.drawCircle(offset, offset, offset, mBackPaint);
            canvas.drawText(mText, textX, textY, mTextPaint);
        } else {
            canvas.drawRoundRect(0, 0, mTextRect.width() + PADDING_X * 2, mTextRect.height() + PADDING_Y * 2,
                    mTextRect.height() / 2.0f + PADDING_X, mTextRect.height() / 2.0f + PADDING_Y, mBackPaint);
            canvas.drawText(mText, PADDING_X, PADDING_Y + mTextRect.height(), mTextPaint);
        }
    }

    public int getNumber() {
        return Integer.parseInt(mText);
    }

    public void setNumber(int number) {
        mText = String.valueOf(number);
        Log.e(TAG, "setNumber: " + mTextSize + "  " + mText);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
        invalidate();
    }
}
