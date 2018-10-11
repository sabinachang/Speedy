package com.enhan.sabina.speedy.study;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.List;

public interface ReviewWordContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter{
        List<WordEntity> getWordsInStack(String stackName);
    }
}
