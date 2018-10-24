package com.enhan.sabina.speedy.detect;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

public interface DetectContract {
    interface View extends BaseView<Presenter> {

    }
    interface Presenter extends BasePresenter {
        void addStackEntityToLocalDatabase(StackEntity stackEntity);
        void unbindListener();
        void bindListener(StackItemAdapter adapter);
    }
}
