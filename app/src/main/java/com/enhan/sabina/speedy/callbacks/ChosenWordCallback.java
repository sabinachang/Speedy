package com.enhan.sabina.speedy.callbacks;

import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.List;

public interface ChosenWordCallback {
    void onSelected(WordEntity wordEntity);
    void onRemoved(WordEntity wordEntity);
    void onBottomSheetCollapsed(boolean isAdded, StackEntity stackEntity);
    void onAddedToChosenFragment(WordEntity wordEntity);
    void onAddedToLocalDatabase(List<WordEntity> wordEntityList);
}
