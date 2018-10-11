package com.enhan.sabina.speedy.callbacks;

import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

public interface DataRepositoryCallback {
    void onReceiveStackInfo(StackEntity stackEntity);
}
