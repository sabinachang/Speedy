package com.enhan.sabina.speedy.study;

import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

public class StudyPresenter implements StudyContract.Presenter {

    private DataRepository mDataRepository;
    private StudyContract.View mView;
    private ChooseStackAdapter mAdapter;

    public StudyPresenter(StudyContract.View view,ChooseStackAdapter adapter) {
    }
    @Override
    public void start() {
    }
    public void returnStackName(StackEntity stackEntity) {

    }
}
