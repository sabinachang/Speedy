package com.enhan.sabina.speedy.detect;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

public interface ChosenWordContract {
    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter {
        void addWord(WordEntity wordEntity);
    }
}
