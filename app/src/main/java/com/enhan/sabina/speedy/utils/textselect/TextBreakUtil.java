package com.enhan.sabina.speedy.utils.textselect;


import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class TextBreakUtil {

    /**
     *@param cs
     *@param measuredWidth
     *@param textpadding
     *@param paint
     *@return 如果cs为空或者长度为0，返回null
     *--------------------

     *--------------------
     * author: huangwei
     * 2017年5月4日下午3:31:03
     */
    public static BreakResult BreakText(char[] cs, float measuredWidth, float textpadding, Paint paint) {
        if(cs==null||cs.length==0){return null;}
//        measuredWidth -= 2*textpadding;
//        measuredWidth *= 2;
        BreakResult breakResult = new BreakResult();
        measuredWidth -= 2 * textpadding;
        Log.d("TB","measuredwidth" + measuredWidth);
        breakResult.showChars = new ArrayList<ShowChar>();
        float width = 0;
        int i = 0;
        int arrayLength = cs.length;
        StringBuilder currentString = new StringBuilder();
        float currentWidth = 0;

        while (i < arrayLength) {

            String currentchar = String.valueOf(cs[i]);
            float charWidth = paint.measureText(currentchar);
            Log.d("TB","char = " + currentchar + " width = " + charWidth);

            if (Pattern.matches("\\p{Punct}",currentchar) || Pattern.matches("\\p{Space}",currentchar)) {
                if ((width + charWidth) > measuredWidth ) {
                    ShowChar showChar = new ShowChar();
                    showChar.chardata = currentchar;
                    showChar.charWidth = charWidth;
                    breakResult.showChars.add(showChar);
                    breakResult.ChartNums = i;
                    breakResult.IsFullLine = true;
                    Log.d("leaving width"," " +width);
                    return breakResult;
                } else {
                    if (currentString.length() == 0) {
//                        currentString = new StringBuilder();
//                        currentWidth = 0;
                        Log.d("TB","continue");

                    } else {
                        ShowChar word = new ShowChar();
                        word.chardata = currentString.toString();
                        word.charWidth = currentWidth;
//                        width += currentWidth;
                        breakResult.showChars.add(word);

                        currentString = new StringBuilder();
                        currentWidth = 0;
                    }
                    ShowChar showChar = new ShowChar();
                    showChar.chardata = currentchar;
                    showChar.charWidth = charWidth;
                    breakResult.showChars.add(showChar);
                    i ++;
                    width += charWidth;
                    Log.d("TB","width +" + width);
                }

            } else {
                if ((width + charWidth) > measuredWidth) {
                    breakResult.ChartNums = i - currentString.length();
                    breakResult.IsFullLine = true;
                    Log.d("leaving width","2 " +width);

                    return breakResult;
                } else {
                    currentString.append(cs[i]);
                    currentWidth += charWidth;
                    width += charWidth;
                    i ++;
                }
            }

        }
        breakResult.ChartNums = cs.length;


//        for (int i = 0, size = cs.length; i < size; i++) {
//            String measurestr = String.valueOf(cs[i]);
//            float charwidth = paint.measureText(measurestr);
//
//            if (width <= measuredWidth && (width + textpadding + charwidth) > measuredWidth) {
//                breakResult.ChartNums = i;
//                breakResult.IsFullLine = true;
//                return breakResult;
//            }
//
//            ShowChar showChar = new ShowChar();
//            showChar.chardata = cs[i];
//            showChar.charWidth = charwidth;
//            breakResult.showChars.add(showChar);
//            width += charwidth + textpadding;
//        }

//        breakResult.ChartNums = cs.length;
        return breakResult;
    }

    /**
     *@param text
     *@param medsurewidth
     *@param textpadding
     *@param paint
     *@return 如果text为空，返回null
     *--------------------
     *TODO
     *--------------------
     * author: huangwei
     * 2017年7月3日下午3:12:12
     */
    public static BreakResult BreakText(String text, float medsurewidth, float textpadding, Paint paint) {
        if (TextUtils.isEmpty(text)) {
            int[] is = new int[2];
            is[0] = 0;
            is[1] = 0;
            return null;
        }

        return BreakText(text.toCharArray(), medsurewidth, textpadding, paint);

    }

    public static float MeasureText(String text, float textpadding, Paint paint) {
        if (TextUtils.isEmpty(text))
            return 0;
        char[] cs = text.toCharArray();
        float width = 0;
        for (int i = 0, size = cs.length; i < size; i++) {
            String mesasrustr = String.valueOf(cs[i]);
            float charwidth = paint.measureText(mesasrustr);
            width += charwidth + textpadding;
        }

        return width;
    }


}