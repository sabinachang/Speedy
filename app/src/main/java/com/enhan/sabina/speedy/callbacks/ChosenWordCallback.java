package com.enhan.sabina.speedy.callbacks;

import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.List;

public interface ChosenWordCallback {
    void onSelected(WordEntity wordEntity); // adapter

    void onRemoved(WordEntity wordEntity); // adapter

    void onBottomSheetCollapsed(boolean isAdded, StackEntity stackEntity); // activity

    void onAddedToChosenFragment(WordEntity wordEntity); // activity

}
