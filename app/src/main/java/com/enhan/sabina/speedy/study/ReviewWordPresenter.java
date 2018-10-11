package com.enhan.sabina.speedy.study;

import com.enhan.sabina.speedy.callbacks.ReviewWordCallback;
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
    public void getWordsInStack(String stackName) {
        mDataRepository.getWordsInStack(stackName,(ReviewWordCallback)mView);
    }

    @Override
    public void start() {

    }
}
