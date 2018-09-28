package com.enhan.sabina.speedy.utils.textselect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TextSelectView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = "Textselectview";
    private ViewGroup mViewGroup;


//    String TextData = "jEh话说天下大势，分久必合，合久必分。周末七国分争，并入于秦。及秦灭之后，楚、汉分争，又并入于汉。汉朝自高祖斩白蛇而起义，一统天下，后来光武中兴，传至献帝，遂分为三国。推其致乱之由，殆始于桓、灵二帝。桓帝禁锢善类，崇信宦官。及桓帝崩，灵帝即位，大将军窦武、太傅陈蕃共相辅佐。时有宦官曹节等弄权，窦武、陈蕃谋诛之，机事不密，反为所害，中涓自此愈横"
//            +
//            "建宁二年四月望日，帝御温德殿。方升座，殿角狂风骤起。只见一条大青蛇，从梁上飞将下来，蟠于椅上。帝惊倒，左右急救入宫，百官俱奔避。须臾，蛇不见了。忽然大雷大雨，加以冰雹，落到半夜方止，坏却房屋无数。建宁四年二月，洛阳地震；又海水泛溢，沿海居民，尽被大浪卷入海中。光和元年，雌鸡化雄。六月朔，黑气十余丈，飞入温德殿中。秋七月，有虹现于玉堂；五原山岸，尽皆崩裂。种种不祥，非止一端。帝下诏问群臣以灾异之由，议郎蔡邕上疏，以为堕鸡化，乃妇寺干政之所致，言颇切直。帝览奏叹息，因起更衣。曹节在后窃视，悉宣告左右；遂以他事陷邕于罪，放归田里。后张让、赵忠、封、段、曹节、侯览、蹇硕、程旷、夏恽、郭胜十人朋比为奸，号为“十常侍”。帝尊信张让，呼为“阿父”。朝政日非，以致天下人心思乱，盗贼蜂起。";


    String TextData;
    public TextSelectView(Context context, String textData) {
        super(context);
        TextData = textData;
        Log.d(TAG,"textviewinit");
//        mViewGroup = vg;

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
    private int LinePadding = 30;
    private float LineYPosition = 0;
    private float TextHeight = 0;

    List<ShowLine> mLinseData = null;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);



        int viewwidth = getMeasuredWidth();
        int viewheight = getMeasuredHeight();
        Log.d(TAG,"view width = " + viewwidth);
        Log.d(TAG,"view height = " + viewheight);

//        setMeasuredDimension(,viewheight);


        initData(viewwidth, viewheight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        Log.d(TAG,"in onDraw");
        LineYPosition = TextHeight + LinePadding;

        Log.d(TAG,"canvas"+ canvas.getHeight());

        Log.d(TAG,"canvas"+ canvas.getWidth());
        for (ShowLine line : mLinseData) {
            DrawLineText(line, canvas);

        }

//        if (mCurrentMode != Mode.Normal) {
//            DrawSelectText(canvas);
//        }
    }

    private Paint mPaint = null;
    private Paint mTextSelectPaint = null;
    private Paint mBorderPointPaint = null;
    private int TextSelectColor = Color.parseColor("#77fadb08");
    private int BorderPointColor = Color.RED;

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(39);

        mTextSelectPaint = new Paint();
        mTextSelectPaint.setAntiAlias(true);
        mTextSelectPaint.setTextSize(19);
        mTextSelectPaint.setColor(TextSelectColor);

        mBorderPointPaint = new Paint();
        mBorderPointPaint.setAntiAlias(true);
        mBorderPointPaint.setTextSize(19);
        mBorderPointPaint.setColor(BorderPointColor);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        TextHeight = Math.abs(fontMetrics.ascent) + Math.abs(fontMetrics.descent);
        Log.d(TAG,"textheight = " + TextHeight);

        setWillNotDraw(false);
        setOnLongClickListener(mLongClickListener);

    }

    private OnLongClickListener mLongClickListener = new OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {

            if (mCurrentMode == Mode.Normal) {
                if (Down_X > 0 && Down_Y > 0) {// 说明还没释放，是长按事件
                    mCurrentMode = Mode.PressSelectText;
                    postInvalidate();
                }
            }
            return false;
        }
    };

    private void initData(int viewwidth, int viewheight) {
        if (mLinseData == null) {
            mLinseData = BreakText(viewwidth, viewheight);
        }
    }

    private Path mSelectTextPath = new Path();
    private ShowChar FirstSelectShowChar = null;
    private ShowChar LastSelectShowChar = null;

//    private void DrawSelectText(Canvas canvas) {
//        if (mCurrentMode == Mode.PressSelectText) {
//            DrawPressSelectText(canvas);
//        } else if (mCurrentMode == Mode.SelectMoveForward) {
//            DrawMoveSelectText(canvas);
//        } else if (mCurrentMode == Mode.SelectMoveBack) {
//            DrawMoveSelectText(canvas);
//        }
//    }
//
//
//    private void DrawMoveSelectText(Canvas canvas) {
//        if (FirstSelectShowChar == null || LastSelectShowChar == null)
//            return;
//        GetSelectData();
//        DrawSeletLines(canvas);
//        DrawBorderPoint(canvas);
//    }

    private List<ShowLine> BreakText(int viewwidth, int viewheight) {
        List<ShowLine> showLines = new ArrayList<ShowLine>();
        while (TextData.length() > 0) {
            BreakResult breakResult = TextBreakUtil.BreakText(TextData, viewwidth, 0, mPaint);

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

        Log.d(TAG,"showline length +" + showLines.size());
        return showLines;
    }



    private void DrawLineText(ShowLine line, Canvas canvas) {
        canvas.drawText(line.getLineData(), 0, LineYPosition, mPaint);
        //canvas.drawLine(0f, LineYPosition, 680f, LineYPosition, mTextSelectPaint);
        Log.d(TAG,"draw line text ");
        float leftposition = 0;
        float rightposition = 0;
        float bottomposition = LineYPosition + mPaint.getFontMetrics().descent;

        for (ShowChar c : line.CharsData) {
            rightposition = leftposition + c.charWidth;
            Point tlp = new Point();
            c.TopLeftPosition = tlp;
            tlp.x = (int) leftposition;
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
            brp.x = (int) rightposition;
            brp.y = (int) bottomposition;

            leftposition = rightposition;

        }
        LineYPosition = LineYPosition + TextHeight + LinePadding;
    }

    private enum Mode {
        Normal, PressSelectText, SelectMoveForward, SelectMoveBack
    }


}
