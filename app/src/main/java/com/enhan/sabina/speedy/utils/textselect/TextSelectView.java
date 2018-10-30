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

public class TextSelectView extends android.support.v7.widget.AppCompatTextView
        implements View.OnTouchListener {
    private static final String TAG = "Textselectview";
    private int mParentWidth;
    private ViewGroup mViewGroup;
    private GestureDetector mGestureDetector;
    private int offset = 20;
    private Context mContext;
    private SelectTextCallback mSelectTextCallback;

    String TextData;
    private Canvas mCanvas;
    private ShowChar mLastSelectedText;

    public TextSelectView(Context context, String textData,int width,SelectTextCallback selectTextCallback) {
        super(context);
        TextData = textData;
        mContext = context;
        mParentWidth = width;
        mSelectTextCallback = selectTextCallback;
        init();
    }

    public TextSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
//            init();
    }

    public TextSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//            init();
    }

//    public void setTextData(String textData) {
//        TextData = textData;
//        init();
//    }

    private float Tounch_X = 0, Tounch_Y = 0;
    private float Down_X = -1, Down_Y = -1;
    private Mode mCurrentMode = Mode.Normal;
    private int LinePadding = 25;
    private float LineYPosition = 0;
    private float TextHeight = 0;
    private int mViewHeight;
    private int mFirstTimeFlag = 0;

    List<ShowLine> mLinseData = null;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        initData(mParentWidth);
        setMeasuredDimension(mParentWidth,mViewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        LineYPosition = TextHeight + LinePadding;

        for (ShowLine line : mLinseData) {
            DrawLineText(line, canvas);
        }
        LineYPosition = 0;

        if (mCurrentMode != Mode.Normal) {
            Log.d(TAG,"not normal mode");
            DrawSelectText(canvas);
        }
    }

    private Paint mPaint = null;
    private Paint mTextSelectPaint = null;
    private Paint mBorderPointPaint = null;
    private int TextSelectColor = Color.parseColor("#77fadb08");
    private int TextColor = Color.parseColor("#9e9e9e");
    private int BorderPointColor = Color.RED;

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(52);
        mPaint.setColor(TextColor);

        mTextSelectPaint = new Paint();
        mTextSelectPaint.setAntiAlias(true);
        mTextSelectPaint.setTextSize(32);
        mTextSelectPaint.setColor(TextSelectColor);

        mBorderPointPaint = new Paint();
        mBorderPointPaint.setAntiAlias(true);
        mBorderPointPaint.setTextSize(32);
        mBorderPointPaint.setColor(BorderPointColor);
        setPadding(0,0,0,10);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        TextHeight = Math.abs(fontMetrics.ascent) + Math.abs(fontMetrics.descent);

        mGestureDetector = new GestureDetector(mContext,new gestureListener());
        setOnTouchListener(this);
        setFocusable(true);
        setClickable(true);
        setLongClickable(true);
    }

    private OnLongClickListener mLongClickListener = new OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {

            if (mCurrentMode == Mode.Normal) {
                if (Down_X > 0 && Down_Y > 0) {
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

    private void initData(int viewwidth) {
        if (mLinseData == null) {
            mLinseData = BreakText(viewwidth);
        }
    }

    private void DrawSelectText(Canvas canvas) {

        if (mCurrentMode == Mode.PressSelectText) {
            DrawPressSelectText(canvas);
        }
    }

    private void Release() {
        Down_X = -1;
        Down_Y = -1;
    }

    private void DrawPressSelectText(Canvas canvas) {
        ShowChar p = DetectPressShowChar(Down_X, Down_Y);

        if (p != null) {
            canvas.drawRect(p.TopLeftPosition.x,p.TopLeftPosition.y,p.BottomRightPosition.x,p.BottomRightPosition.y,mTextSelectPaint);

            if (mLastSelectedText != null) {
                boolean tlpx = mLastSelectedText.TopLeftPosition.x == p.TopLeftPosition.x;
                boolean tlpy = mLastSelectedText.TopLeftPosition.y == p.TopLeftPosition.y;
                boolean blpx = mLastSelectedText.BottomLeftPosition.x == p.BottomLeftPosition.x;
                boolean blpy = mLastSelectedText.BottomRightPosition.y == p.BottomRightPosition.y;
                if (! (tlpx && tlpy && blpx && blpy)) {
                    mSelectTextCallback.onWordSelected(p.chardata);
                }
            } else {
                    mSelectTextCallback.onWordSelected(p.chardata);
            }
        }

        mLastSelectedText = p;
    }

    private ShowChar DetectPressShowChar(float down_X2, float down_Y2) {

        for (ShowLine l : mLinseData) {
            for (ShowChar c : l.CharsData) {
                if (down_Y2 > c.BottomLeftPosition.y) {
                    break;
                }
                if (down_X2 >= c.BottomLeftPosition.x && down_X2 <= c.BottomRightPosition.x) {
                    return c;
                }
            }
        }

        return null;
    }

    private List<ShowLine> BreakText(int viewwidth) {
        List<ShowLine> showLines = new ArrayList<ShowLine>();
        while (TextData.length() > 0) {
            BreakResult breakResult = TextBreakUtil.BreakText(TextData, viewwidth, 20, mPaint);

            if (breakResult != null && breakResult.HasData()) {
                ShowLine showLine = new ShowLine();
                showLine.CharsData = breakResult.showChars;

                showLines.add(showLine);
            } else {
                break;
            }
            TextData = TextData.substring(breakResult.ChartNums);
        }
        int index = 0;
        for (ShowLine l : showLines) {
            for (ShowChar c : l.CharsData) {
                c.Index = index++;
            }
        }
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        int theight = (int) (Math.abs(fontMetrics.ascent) + Math.abs(fontMetrics.descent));
        mViewHeight = 30 + (theight + 25) * showLines.size();
        return showLines;
    }



    private void DrawLineText(ShowLine line, Canvas canvas) {

        canvas.drawText(line.getLineData(), offset, LineYPosition, mPaint);
        float leftposition = 20;
        float rightposition = 0;
        float bottomposition = LineYPosition + mPaint.getFontMetrics().descent;

        for (ShowChar c : line.CharsData) {
            rightposition = leftposition + c.charWidth;
            Point tlp = new Point();
            c.TopLeftPosition = tlp;
            tlp.x = (int) leftposition ;
            tlp.y = (int) (bottomposition - TextHeight);

            Point blp = new Point();
            c.BottomLeftPosition = blp;
            blp.x = (int) leftposition;
            blp.y = (int) bottomposition;

            Point trp = new Point();
            c.TopRightPosition = trp;
            trp.x = (int) rightposition;
            trp.y = (int) (bottomposition - TextHeight);

            Point brp = new Point();
            c.BottomRightPosition = brp;
            brp.x = (int) rightposition ;
            brp.y = (int) bottomposition;

            leftposition = rightposition;

        }
        LineYPosition = LineYPosition + TextHeight + LinePadding;
    }


    private class gestureListener implements GestureDetector.OnGestureListener{
        @Override
        public boolean onDown(MotionEvent motionEvent) {

            Down_X = motionEvent.getX();
            Down_Y = motionEvent.getY();
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            mCurrentMode = Mode.Normal;
            Release();

            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            Down_X = motionEvent.getX();
            Down_Y = motionEvent.getY();
            mCurrentMode = Mode.PressSelectText;
            Log.d("Text selct","long pressed");
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
