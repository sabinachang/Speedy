package com.enhan.sabina.speedy.detect;

import android.arch.lifecycle.Observer;
import android.arch.persistence.room.InvalidationTracker;
import android.support.annotation.Nullable;

import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

import java.util.List;

public class DetectPresenter implements DetectContract.Presenter {

    private DetectContract.View mView;
    private DataRepository mDataRepository;
    private StackItemAdapter mAdapter;
    private Observer<List<StackEntity>> mObserver;

    public DetectPresenter(DetectContract.View view,StackItemAdapter adpater) {
        mView = view;
        mDataRepository = DataRepository.getInstance();
        mAdapter = adpater;
        mObserver = new Observer<List<StackEntity>>() {
            @Override
            public void onChanged(@Nullable List<StackEntity> stackEntities) {
                mAdapter.addStackNames(stackEntities);
            }
        };

        mDataRepository.getAllStacks().observe((DetectActivity)mView,mObserver);
    }

    @Override
    public void addStackEntityToLocalDatabase(StackEntity stackEntity) {
        mDataRepository.insertStack(stackEntity);


    }

    @Override
    public void unbindListener() {
        mDataRepository.getAllStacks().removeObservers((DetectActivity)mView);
    }

    @Override
    public void start() {

    }
}
