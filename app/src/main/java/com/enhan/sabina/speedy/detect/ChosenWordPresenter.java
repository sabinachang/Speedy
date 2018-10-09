package com.enhan.sabina.speedy.detect;

import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

public class ChosenWordPresenter implements ChosenWordContract.Presenter {

    private ChosenWordContract.View mView;
    private DataRepository mDataRepository;

    public ChosenWordPresenter(ChosenWordContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mDataRepository = DataRepository.getInstance();
    }
    @Override
    public void start() {

    }

    @Override
    public void addWord(WordEntity wordEntity) {
        mDataRepository.insertWord(wordEntity);
    }
}
