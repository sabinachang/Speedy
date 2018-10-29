package com.enhan.sabina.speedy.detect;

import android.support.design.widget.FloatingActionButton;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.List;

public interface ChosenWordContract {
    interface View extends BaseView<Presenter> {
        void notifyAdapterDatasetChange();

        void addToAdapter(WordEntity wordEntity);

        int getAdapterDatasetCount();
    }

    interface Presenter extends BasePresenter {
        void wordSelected(WordEntity wordEntity);

        void wordRemoved(WordEntity wordEntity);

        void updateWordEntityList(StackEntity stackEntity, List<WordEntity> wordEntityList);

        void checkIfDuplicated(WordEntity wordEntity);
    }
}
