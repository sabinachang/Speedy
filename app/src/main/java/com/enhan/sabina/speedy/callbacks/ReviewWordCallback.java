package com.enhan.sabina.speedy.callbacks;

import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.List;

public interface ReviewWordCallback {
    void returnWordsInStack(List<WordEntity> wordEntityList);
}
