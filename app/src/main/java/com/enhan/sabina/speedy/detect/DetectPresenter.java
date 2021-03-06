package com.enhan.sabina.speedy.detect;

import android.arch.lifecycle.Observer;
import android.arch.persistence.room.InvalidationTracker;
import android.support.annotation.Nullable;

import com.enhan.sabina.speedy.callbacks.ChosenWordCallback;
import com.enhan.sabina.speedy.callbacks.DetectActivityCallback;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.List;

public class DetectPresenter implements DetectContract.Presenter {

    private DetectContract.View mView;
    private DataRepository mDataRepository;
    private StackItemAdapter mAdapter;
    private Observer<List<StackEntity>> mObserver;
    private DetectNavigator mDetectNavigator;
    private ChosenWordCallback mChosenWordCallback;

    public DetectPresenter(DetectContract.View view) {
        mView = view;
        mDataRepository = DataRepository.getInstance();
        mDetectNavigator = DetectNavigator.getInstance();
        mDetectNavigator.setDetectActivityCallback((DetectActivityCallback) mView);
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
    public void bindListener(StackItemAdapter adapter) {
        mAdapter = adapter;
        mObserver = new Observer<List<StackEntity>>() {
            @Override
            public void onChanged(@Nullable List<StackEntity> stackEntities) {
                mAdapter.addStackNames(stackEntities);
            }
        };

        mDataRepository.getAllStacks().observe((DetectActivity)mView,mObserver);
    }

    @Override
    public void getWordDefinition(String word) {
        mDataRepository.getWordDefinition(word,(DetectActivityCallback)mView);
    }

    @Override
    public void setChosenWordCallback(ChosenWordCallback callback) {
        mChosenWordCallback = callback;
    }

    @Override
    public void onAddedToChosenFragment(WordEntity wordEntity) {
        mChosenWordCallback.onAddedToChosenFragment(wordEntity);
    }

    @Override
    public void onBottomSheetCollapsed(boolean added, StackEntity stackEntity) {
        mChosenWordCallback.onBottomSheetCollapsed(added,stackEntity);
    }

    @Override
    public void start() {

    }
}
