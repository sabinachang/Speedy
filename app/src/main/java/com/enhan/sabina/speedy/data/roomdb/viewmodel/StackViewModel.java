package com.enhan.sabina.speedy.data.roomdb.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

public class StackViewModel extends AndroidViewModel{

    private DataRepository mDataRepository;
    private LiveData<StackEntity> mAllStacks;

    public StackViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance();

//        mAllStacks = mDataRepository.getAllStacks();
    }
}
