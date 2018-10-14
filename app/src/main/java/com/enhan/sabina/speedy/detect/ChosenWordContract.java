package com.enhan.sabina.speedy.detect;

import android.support.design.widget.FloatingActionButton;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

public interface ChosenWordContract {
    interface View extends BaseView<Presenter>{

        void setFab(FloatingActionButton fab);
    }

    interface Presenter extends BasePresenter {
        void addWord(WordEntity wordEntity);
    }
}
