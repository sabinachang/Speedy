package com.enhan.sabina.speedy.study;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;

public interface ChooseStackContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void addAdapter(StudyStackAdapter adapter);
    }
}
