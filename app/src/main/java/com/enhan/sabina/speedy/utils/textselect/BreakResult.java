package com.enhan.sabina.speedy.utils.textselect;

import java.util.List;

public class BreakResult {
    public int mCharNum = 0;
    public Boolean isFullLine = false;
    public List<ShowChar> mShowChars = null;

    public Boolean hasData() {
        return (mShowChars != null && mShowChars.size() > 0);
    }
}
