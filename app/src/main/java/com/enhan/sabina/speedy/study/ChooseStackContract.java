package com.enhan.sabina.speedy.study;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

public interface ChooseStackContract {

    interface View extends BaseView<Presenter> {
        void setStartButtonVisible();
    }

    interface Presenter extends BasePresenter {
        void addAdapter(ChooseStackAdapter adapter);

        void returnStackName(StackEntity stackEntity);
    }
}
