package com.enhan.sabina.speedy.callbacks;

import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.List;

public interface ChosenWordCallback {
    void onSelected(WordEntity wordEntity);
    void onRemoved(WordEntity wordEntity);
    void onBottomSheetCollapsed(boolean isAdded);
    void onNewWordAdded(WordEntity wordEntity);
}
