package com.enhan.sabina.speedy.study;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.enhan.sabina.speedy.callbacks.ChooseStackCallback;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.List;

public class ChooseStackPresenter implements ChooseStackContract.Presenter {


    private DataRepository mDataRepository;
    private ChooseStackContract.View mView;
    private ChooseStackAdapter mAdapter;

    public ChooseStackPresenter(ChooseStackContract.View view) {
        mView = view;

        mDataRepository = DataRepository.getInstance();


//        mView.setPresenter(this);



    }


    @Override
    public void start() {
        Observer<List<StackEntity>> observer = new Observer<List<StackEntity>>() {
            @Override
            public void onChanged(@Nullable List<StackEntity> stackEntities) {
                mAdapter.addStacks(stackEntities);
            }
        };

        mDataRepository.getAllStacks().observe((ChooseStackFragment)mView,observer);
//        populateFakeDB();
    }

    private void populateFakeDB() {



        StackEntity stackEntity = new StackEntity("First Stack ABC");
        mDataRepository.insertStack(stackEntity);

        stackEntity = new StackEntity("Janet Paris 1789");
        mDataRepository.insertStack(stackEntity);

        stackEntity = new StackEntity("Venture deals");
        mDataRepository.insertStack(stackEntity);

        mDataRepository.getStackInfo("First Stack ABC",(ChooseStackCallback) mView);
        mDataRepository.getStackInfo("Janet Paris 1789",(ChooseStackCallback) mView);
        mDataRepository.getStackInfo("Venture deals",(ChooseStackCallback) mView);
//
    }

    @Override
    public void returnStackName(StackEntity stackEntity) {

        if ("First Stack ABC".equals(stackEntity.getStackName())) {

            WordEntity wordEntity = new WordEntity("Venture","a risky undertaking or adventure");
            wordEntity.setStackName(stackEntity.getStackName());
            wordEntity.setStackId(stackEntity.getId());
            mDataRepository.insertWord(wordEntity);


            wordEntity = new WordEntity("Capital","a risky undertaking or adventure");
            wordEntity.setStackName(stackEntity.getStackName());
            wordEntity.setStackId(stackEntity.getId());
            mDataRepository.insertWord(wordEntity);

            wordEntity = new WordEntity("Lust","a risky undertaking or adventure");
            wordEntity.setStackName(stackEntity.getStackName());
            wordEntity.setStackId(stackEntity.getId());
            mDataRepository.insertWord(wordEntity);

        } else if ("Janet Paris 1789".equals(stackEntity.getStackName())) {
            WordEntity wordEntity = new WordEntity("Anger","a risky undertaking or adventure");
            wordEntity.setStackName(stackEntity.getStackName());
            wordEntity.setStackId(stackEntity.getId());
            mDataRepository.insertWord(wordEntity);

            wordEntity = new WordEntity("Mungbeans","a risky undertaking or adventure");
            wordEntity.setStackName(stackEntity.getStackName());
            wordEntity.setStackId(stackEntity.getId());
            mDataRepository.insertWord(wordEntity);
        } else if ("Venture deals".equals(stackEntity.getStackName())) {

            WordEntity wordEntity = new WordEntity("Chickpeas","a risky undertaking or adventure");
            wordEntity.setStackName(stackEntity.getStackName());
            wordEntity.setStackId(stackEntity.getId());
            mDataRepository.insertWord(wordEntity);

            wordEntity = new WordEntity("Nature","a risky undertaking or adventure");
            wordEntity.setStackName(stackEntity.getStackName());
            wordEntity.setStackId(stackEntity.getId());
            mDataRepository.insertWord(wordEntity);

            wordEntity = new WordEntity("Surrender","a risky undertaking or adventure");
            wordEntity.setStackName(stackEntity.getStackName());
            wordEntity.setStackId(stackEntity.getId());
            mDataRepository.insertWord(wordEntity);

        }
    }

    @Override
    public void addAdapter(ChooseStackAdapter adapter) {
        mAdapter = adapter;
    }
}
