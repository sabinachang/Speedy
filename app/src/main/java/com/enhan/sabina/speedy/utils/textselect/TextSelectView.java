package com.enhan.sabina.speedy.utils.textselect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.SelectTextCallback;

import java.util.ArrayList;
import java.util.List;

public class TextSelectView extends android.support.v7.widget.AppCompatTextView implements View.OnTouchListener {
    private int mParentWidth;
    private GestureDetector mGestureDetector;
    private Context mContext;
    private SelectTextCallback mSelectTextCallback;

    private String mTextData;
    private ShowChar mLastSelectedText;

    public TextSelectView(Context context, String textData,int width,SelectTextCallback selectTextCallback) {
        super(context);
        mTextData = textData;
        mContext = context;
        mParentWidth = width;
        mSelectTextCallback = selectTextCallback;
        init();
    }

    public TextSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float mDownX = -1;
    private float mDownY = -1;
    private Mode mCurrentMode = Mode.Normal;
    private int mLinePadding = 25;
    private float mLineYPosition = 0;
    private float mTextHeight = 0;
    private int mViewHeight;
    private List<ShowLine> mLineData = null;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        initData(mParentWidth);
        setMeasuredDimension(mParentWidth,mViewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mLineYPosition = mTextHeight + mLinePadding;

        for (ShowLine line : mLineData) {
            drawLineText(line, canvas);
        }
        mLineYPosition = 0;

        if (mCurrentMode != Mode.Normal) {
            drawSelectText(canvas);
        }
    }

    private Paint mPaint = null;
    private Paint mTextSelectPaint = null;
    private int mTextSelectColor = Color.parseColor("#77fadb08");
    private int mTextColor = Color.parseColor("#9e9e9e");

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(52);
        mPaint.setColor(mTextColor);

        mTextSelectPaint = new Paint();
        mTextSelectPaint.setAntiAlias(true);
        mTextSelectPaint.setTextSize(32);
        mTextSelectPaint.setColor(mTextSelectColor);

        Paint borderPointPaint = new Paint();
        borderPointPaint.setAntiAlias(true);
        borderPointPaint.setTextSize(32);
        int borderPointColor = Color.RED;
        borderPointPaint.setColor(borderPointColor);
        setPadding(0,0,0,10);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        mTextHeight = Math.abs(fontMetrics.ascent) + Math.abs(fontMetrics.descent);

        mGestureDetector = new GestureDetector(mContext,new GestureListener());
        setOnTouchListener(this);
        setFocusable(true);
        setClickable(true);
        setLongClickable(true);
    }

    private OnLongClickListener mLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mCurrentMode == Mode.Normal) {
                if (mDownX > 0 && mDownY > 0) {
                    mCurrentMode = Mode.PressSelectText;
                    postInvalidate();
                }
            }
            return false;
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private void initData(int viewWidth) {
        if (mLineData == null) {
            mLineData = breakText(viewWidth);
        }
    }

    private void drawSelectText(Canvas canvas) {
        if (mCurrentMode == Mode.PressSelectText) {
            drawPressSelectText(canvas);
        }
    }

    private void release() {
        mDownX = -1;
        mDownY = -1;
    }

    private void drawPressSelectText(Canvas canvas) {
        ShowChar p = detectPressShowChar(mDownX, mDownY);

        if (p != null) {
            canvas.drawRect(p.mTopLeftPosition.x,p.mTopLeftPosition.y,p.mBottomRightPosition.x,p.mBottomRightPosition.y,mTextSelectPaint);

            if (mLastSelectedText != null) {
                boolean topLeftPointX = mLastSelectedText.mTopLeftPosition.x == p.mTopLeftPosition.x;
                boolean topLeftPointY = mLastSelectedText.mTopLeftPosition.y == p.mTopLeftPosition.y;
                boolean bottomLeftPointX = mLastSelectedText.mBottomLeftPosition.x == p.mBottomLeftPosition.x;
                boolean bottomLeftPointY = mLastSelectedText.mBottomRightPosition.y == p.mBottomRightPosition.y;
                if (! (topLeftPointX && topLeftPointY && bottomLeftPointX && bottomLeftPointY)) {
                    mSelectTextCallback.onWordSelected(p.mChardata);
                }
            } else {
                mSelectTextCallback.onWordSelected(p.mChardata);
            }
        }

        mLastSelectedText = p;
    }

    private ShowChar detectPressShowChar(float downX, float downY) {
        for (ShowLine l : mLineData) {
            for (ShowChar c : l.mCharsData) {
                if (downY > c.mBottomLeftPosition.y) {
                    break;
                }
                if (downX >= c.mBottomLeftPosition.x && downX <= c.mBottomRightPosition.x) {
                    return c;
                }
            }
        }
        return null;
    }

    private List<ShowLine> breakText(int viewWidth) {
        List<ShowLine> showLines = new ArrayList<>();

        while (mTextData.length() > 0) {
            BreakResult breakResult = TextBreakUtil.breakText(mTextData, viewWidth, 20, mPaint);
            if (breakResult != null && breakResult.hasData()) {
                ShowLine showLine = new ShowLine();
                showLine.mCharsData = breakResult.mShowChars;
                showLines.add(showLine);
            } else {
                break;
            }
            mTextData = mTextData.substring(breakResult.mCharNum);
        }

        int index = 0;
        for (ShowLine l : showLines) {
            for (ShowChar c : l.mCharsData) {
                c.mIndex = index++;
            }
        }
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        int textHeight = (int) (Math.abs(fontMetrics.ascent) + Math.abs(fontMetrics.descent));
        mViewHeight = 30 + (textHeight + 25) * showLines.size();
        return showLines;
    }

    private void drawLineText(ShowLine line, Canvas canvas) {
        int offset = 20;
        canvas.drawText(line.getLineData(), offset, mLineYPosition, mPaint);
        float leftPosition = 20;
        float rightPosition = 0;
        float bottomPosition = mLineYPosition + mPaint.getFontMetrics().descent;

        for (ShowChar c : line.mCharsData) {
            rightPosition = leftPosition + c.mCharWidth;
            Point topLeftPoint = new Point();
            c.mTopLeftPosition = topLeftPoint;
            topLeftPoint.x = (int) leftPosition;
            topLeftPoint.y = (int) (bottomPosition - mTextHeight);

            Point bottomLeftPoint = new Point();
            c.mBottomLeftPosition = bottomLeftPoint;
            bottomLeftPoint.x = (int) leftPosition;
            bottomLeftPoint.y = (int) bottomPosition;

            Point topRightPoint = new Point();
            c.mTopRightPosition = topRightPoint;
            topRightPoint.x = (int) rightPosition;
            topRightPoint.y = (int) (bottomPosition - mTextHeight);

            Point bottomRightPoint = new Point();
            c.mBottomRightPosition = bottomRightPoint;
            bottomRightPoint.x = (int) rightPosition;
            bottomRightPoint.y = (int) bottomPosition;

            leftPosition = rightPosition;
        }
        mLineYPosition = mLineYPosition + mTextHeight + mLinePadding;
    }

    private class GestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            mDownX = motionEvent.getX();
            mDownY = motionEvent.getY();
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            mCurrentMode = Mode.Normal;
            release();
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            mDownX = motionEvent.getX();
            mDownY = motionEvent.getY();
            mCurrentMode = Mode.PressSelectText;
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
    }

    private enum Mode {
        Normal, PressSelectText, SelectMoveForward, SelectMoveBack, SecondTouch
    }
}
