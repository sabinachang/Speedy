package com.enhan.sabina.speedy.study;

import com.enhan.sabina.speedy.data.DataRepository;

public class StudyPresenter implements StudyContract.Presenter {

    private DataRepository mDataRepository;
    private StudyContract.View mView;
    private ChooseStackAdapter mAdapter;

    public StudyPresenter(StudyContract.View view,ChooseStackAdapter adapter) {
//        mView = view;
//        mAdapter = adapter;
//        mDataRepository = DataRepository.getInstance();
//
//        Observer<List<StackEntity>> observer = new Observer<List<StackEntity>>() {
//            @Override
//            public void onChanged(@Nullable List<StackEntity> stackEntities) {
//                mAdapter.addStacks(stackEntities);
//            }
//        };
//
//        mDataRepository.getAllStacks().observe((StudyActivity)mView,observer);
    }
    @Override
    public void start() {
//        populateFakeDB();
    }

    private void populateFakeDB() {



//        StackEntity stackEntity = new StackEntity("First Stack ABC");
//        mDataRepository.insertStack(stackEntity);
//
//        stackEntity = new StackEntity("Janet Paris 1789");
//        mDataRepository.insertStack(stackEntity);
//
//        stackEntity = new StackEntity("Venture deals");
//        mDataRepository.insertStack(stackEntity);
//
//         mDataRepository.getStackInfo("First Stack ABC");
//         mDataRepository.getStackInfo("Janet Paris 1789");
//         mDataRepository.getStackInfo("Venture deals");




//
    }


//    public void returnStackName(StackEntity stackEntity) {
//
//        if ("First Stack ABC".equals(stackEntity.getStackName())) {
//
//            WordEntity wordEntity = new WordEntity("Venture","a risky undertaking or adventure");
//            wordEntity.setStackName(stackEntity.getStackName());
//            wordEntity.setStackId(stackEntity.getId());
//            mDataRepository.insertWord(wordEntity);
//
//
//            wordEntity = new WordEntity("Capital","a risky undertaking or adventure");
//            wordEntity.setStackName(stackEntity.getStackName());
//            wordEntity.setStackId(stackEntity.getId());
//            mDataRepository.insertWord(wordEntity);
//
//            wordEntity = new WordEntity("Lust","a risky undertaking or adventure");
//            wordEntity.setStackName(stackEntity.getStackName());
//            wordEntity.setStackId(stackEntity.getId());
//            mDataRepository.insertWord(wordEntity);
//
//        } else if ("Janet Paris 1789".equals(stackEntity.getStackName())) {
//            WordEntity wordEntity = new WordEntity("Anger","a risky undertaking or adventure");
//            wordEntity.setStackName(stackEntity.getStackName());
//            wordEntity.setStackId(stackEntity.getId());
//            mDataRepository.insertWord(wordEntity);
//
//            wordEntity = new WordEntity("Mungbeans","a risky undertaking or adventure");
//            wordEntity.setStackName(stackEntity.getStackName());
//            wordEntity.setStackId(stackEntity.getId());
//            mDataRepository.insertWord(wordEntity);
//        } else if ("Venture deals".equals(stackEntity.getStackName())) {
//
//            WordEntity wordEntity = new WordEntity("Chickpeas","a risky undertaking or adventure");
//            wordEntity.setStackName(stackEntity.getStackName());
//            wordEntity.setStackId(stackEntity.getId());
//            mDataRepository.insertWord(wordEntity);
//
//            wordEntity = new WordEntity("Nature","a risky undertaking or adventure");
//            wordEntity.setStackName(stackEntity.getStackName());
//            wordEntity.setStackId(stackEntity.getId());
//            mDataRepository.insertWord(wordEntity);
//
//            wordEntity = new WordEntity("Surrender","a risky undertaking or adventure");
//            wordEntity.setStackName(stackEntity.getStackName());
//            wordEntity.setStackId(stackEntity.getId());
//            mDataRepository.insertWord(wordEntity);
//
//        }
//    }
}
