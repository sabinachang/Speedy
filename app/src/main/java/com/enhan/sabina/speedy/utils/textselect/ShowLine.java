package com.enhan.sabina.speedy.utils.textselect;

import java.util.List;

public class ShowLine {
    public List<ShowChar> mCharsData = null;

    @Override
    public String toString() {
        return "ShowLine [Linedata=" + getLineData() + "]";
    }

    public String getLineData() {
        String linedata = "";
        if (mCharsData == null || mCharsData.size() == 0) return linedata;
        for (ShowChar c : mCharsData) {
            linedata = linedata + c.mChardata;
        }
        return linedata;
    }
}
