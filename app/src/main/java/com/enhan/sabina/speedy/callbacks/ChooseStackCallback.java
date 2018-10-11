package com.enhan.sabina.speedy.callbacks;

import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

public interface ChooseStackCallback {
    void returnStackEntity(StackEntity stackEntity);
    void onStackSelected(StackEntity stackEntity);
}
