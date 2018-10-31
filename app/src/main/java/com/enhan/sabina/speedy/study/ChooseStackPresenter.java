package com.enhan.sabina.speedy.study;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.enhan.sabina.speedy.callbacks.ChooseStackCallback;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;
import com.enhan.sabina.speedy.detect.DetectActivity;

import java.util.List;

public class ChooseStackPresenter implements ChooseStackContract.Presenter {


    private DataRepository mDataRepository;
    private ChooseStackContract.View mView;
    private ChooseStackAdapter mAdapter;

    public ChooseStackPresenter(ChooseStackContract.View view) {
        mView = view;
        mDataRepository = DataRepository.getInstance();
    }

    @Override
    public void start() {
        Observer<List<StackEntity>> observer = new Observer<List<StackEntity>>() {
            @Override
            public void onChanged(@Nullable List<StackEntity> stackEntities) {
                mAdapter.addStacks(stackEntities);
                if (stackEntities.size() != 0) {
                    mView.setStartButtonVisible();
                }
            }
        };

        mDataRepository.getAllStacks().observe((ChooseStackFragment)mView,observer);
    }

    @Override
    public void returnStackName(StackEntity stackEntity) {
    }

    @Override
    public void addAdapter(ChooseStackAdapter adapter) {
        mAdapter = adapter;
    }
}
