package com.enhan.sabina.speedy.utils.textselect;

import android.graphics.Paint;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class TextBreakUtil {
    private static BreakResult breakText(char[] charsArray, float measuredWidth, float textPadding, Paint paint) {
        if (charsArray == null || charsArray.length == 0) return null;
        BreakResult breakResult = new BreakResult();
        measuredWidth -= 2 * textPadding;
        breakResult.mShowChars = new ArrayList<>();

        float width = 0;
        int i = 0;
        int arrayLength = charsArray.length;
        StringBuilder currentString = new StringBuilder();
        float currentWidth = 0;

        while (i < arrayLength) {
            String currentChar = String.valueOf(charsArray[i]);
            float charWidth = paint.measureText(currentChar);

            if (Pattern.matches("\\p{Punct}",currentChar) || Pattern.matches("\\p{Space}",currentChar)) {
                if ((width + charWidth) > measuredWidth) {
                    ShowChar showChar = new ShowChar();
                    showChar.mChardata = currentChar;
                    showChar.mCharWidth = charWidth;

                    breakResult.mShowChars.add(showChar);
                    breakResult.mCharNum = i;
                    breakResult.isFullLine = true;

                    return breakResult;
                } else {
                    if (currentString.length() != 0) {
                        ShowChar word = new ShowChar();
                        word.mChardata = currentString.toString();
                        word.mCharWidth = currentWidth;

                        breakResult.mShowChars.add(word);

                        currentString = new StringBuilder();
                        currentWidth = 0;
                    }
                    ShowChar showChar = new ShowChar();
                    showChar.mChardata = currentChar;
                    showChar.mCharWidth = charWidth;
                    breakResult.mShowChars.add(showChar);
                    i++;
                    width += charWidth;
                }
            } else {
                if ((width + charWidth) > measuredWidth) {
                    breakResult.mCharNum = i - currentString.length();
                    breakResult.isFullLine = true;
                    return breakResult;
                } else {
                    currentString.append(charsArray[i]);
                    currentWidth += charWidth;
                    width += charWidth;
                    i++;
                }
            }
        }
        breakResult.mCharNum = charsArray.length;
        return breakResult;
    }

    public static BreakResult breakText(String text, float measureWidth, float textPadding, Paint paint) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        return breakText(text.toCharArray(), measureWidth, textPadding, paint);
    }
}