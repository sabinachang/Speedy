package com.enhan.sabina.speedy.utils.textselect;

import android.graphics.Point;

public class ShowChar {
    public String mChardata;
    public Boolean isSelected = false;
    public Point mTopLeftPosition = null;
    public Point mTopRightPosition = null;
    public Point mBottomLeftPosition = null;
    public Point mBottomRightPosition = null;
    public float mCharWidth = 0;
    public int mIndex = 0;

    @Override
    public String toString() {
        return "ShowChar [chardata=" + mChardata + ", Selected=" + isSelected + ", TopLeftPosition=" + mTopLeftPosition
                + ", TopRightPosition=" + mTopRightPosition + ", BottomLeftPosition=" + mBottomLeftPosition
                + ", BottomRightPosition=" + mBottomRightPosition + ", charWidth=" + mCharWidth + ", Index=" + mIndex
                + "]";
    }
}
