package com.enhan.sabina.speedy.callbacks;

import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

public interface ChosenWordCallback {
    void onAdded(WordEntity wordEntity);
    void onRemoved(WordEntity wordEntity);
}
