package com.enhan.sabina.speedy.detect;

import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChosenWordPresenter implements ChosenWordContract.Presenter {

    private ChosenWordContract.View mView;
    private DataRepository mDataRepository;
    private DetectNavigator mDetectNavigator;
    private List<WordEntity> mChosenWords = new ArrayList<>();
    private Set<String> mDuplicateCheck = new HashSet<>();

    public ChosenWordPresenter(ChosenWordContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mDataRepository = DataRepository.getInstance();
        mDetectNavigator = DetectNavigator.getInstance();
    }
    @Override
    public void start() {

    }

    @Override
    public void wordSelected(WordEntity wordEntity) {
        if (mChosenWords.isEmpty()) {
            mDetectNavigator.activateFabNavigator();
        }
        mChosenWords.add(wordEntity);
    }

    @Override
    public void wordRemoved(WordEntity wordEntity) {
        Iterator<WordEntity> iterator = mChosenWords.iterator();
        while (iterator.hasNext()) {
            WordEntity word = iterator.next();
            if (word.getWord().equals(wordEntity.getWord())) {
                iterator.remove();
                break;
            }
        }
        if (mChosenWords.isEmpty()) {
            mDetectNavigator.deactivateFabNavigator();
        }
    }

    @Override
    public void updateWordEntityList(StackEntity stackEntity,List<WordEntity> wordEntityList) {
        Iterator<WordEntity> iterator = wordEntityList.iterator();

        while (iterator.hasNext()) {
            WordEntity current = iterator.next();
            if (current.isSelected()) {
                current.setStackId(stackEntity.getId());
                current.setStackName(stackEntity.getStackName());
                mDataRepository.insertWord(current);
                iterator.remove();
            }
        }
        mDetectNavigator.deactivateFabNavigator();
        mDetectNavigator.updateTabCountHintNavigator(mView.getAdapterDatasetCount());
        mChosenWords.clear();
        mView.notifyAdapterDatasetChange();
    }

    @Override
    public void checkIfDuplicated(WordEntity wordEntity) {
        if (mDuplicateCheck.contains(wordEntity.getWord())) {
           mDetectNavigator.isWordDuplicateNavigator(true);
        } else {
            mDuplicateCheck.add(wordEntity.getWord());
            mDetectNavigator.isWordDuplicateNavigator(false);
            mView.addToAdapter(wordEntity);
            mDetectNavigator.updateTabCountHintNavigator(mView.getAdapterDatasetCount());
        }
    }

}
