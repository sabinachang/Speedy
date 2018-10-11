package com.enhan.sabina.speedy.study;

import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.List;

public class ReviewWordPresenter implements ReviewWordContract.Presenter {

    private DataRepository mDataRepository;
    private ReviewWordContract.View mView;

    public ReviewWordPresenter(ReviewWordContract.View view){
        mDataRepository = DataRepository.getInstance();
        mView = view;
    }

    @Override
    public List<WordEntity> getWordsInStack(String stackName) {
        return mDataRepository.getWordsInStack(stackName);
    }

    @Override
    public void start() {

    }
}
