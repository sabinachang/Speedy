package com.enhan.sabina.speedy.detect;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.ChosenWordCallback;
import com.enhan.sabina.speedy.callbacks.DetectActivityCallback;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChosenWordFragment extends Fragment implements ChosenWordCallback, ChosenWordContract.View{

    private RecyclerView mRecyclerView;
    private ChosenWordAdapter mAdapter;
    private FloatingActionButton mFab;
    private List<WordEntity> mWordEntityList = new ArrayList<>();
    private List<WordEntity> mChosenWords = new ArrayList<>();
    private DetectActivityCallback mDetectActivityCallback;
    private ChosenWordContract.Presenter mPresenter;
    private Set<String> mDuplicateCheck = new HashSet<>();


    public ChosenWordFragment() {

    }



    public static ChosenWordFragment newInstance() {
        return  new ChosenWordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            mDetectActivityCallback = (DetectActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chosen_word,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.chosenword_recyclerview);
        mPresenter = new ChosenWordPresenter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SpeedyApplication.getAppContext()));
//        ViewCompat.setNestedScrollingEnabled(mRecyclerView,false);
        mAdapter = new ChosenWordAdapter(mWordEntityList,this);
        mRecyclerView.setAdapter(mAdapter);

//        mFab = view.findViewById(R.id.fab);
//        mFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDetectActivityCallback.onFabButtonClicked(mChosenWords);
//            }
//        });

//        mFab.setClickable(false);
        initWordList();

    }

    private void startBottomSheet() {

    }

    private void initWordList() {

//        Log.d("chosen word","in Init");

//        WordEntity wordEntity = new WordEntity("Venture","a risky undertaking or adventure");
//        mWordEntityList.add(wordEntity);
//
//        wordEntity = new WordEntity("enhance","to improve the quality, amount, or strength of something");
//        mWordEntityList.add(wordEntity);
//
//        wordEntity = new WordEntity("strength","the ability to do something");
//        mWordEntityList.add(wordEntity);
//
//        wordEntity = new WordEntity("weakness","to improve the quality, amount, or strength of something");
//        mWordEntityList.add(wordEntity);
//
//        wordEntity = new WordEntity("accept","the ability to do something");
//        mWordEntityList.add(wordEntity);
//
//        wordEntity = new WordEntity("drawing","to improve the quality, amount, or strength of something");
//        mWordEntityList.add(wordEntity);
//
//        wordEntity = new WordEntity("character","the ability to do something");
//        mWordEntityList.add(wordEntity);
//
//        wordEntity = new WordEntity("drawing","to improve the quality, amount, or strength of something");
//        mWordEntityList.add(wordEntity);
//
//        wordEntity = new WordEntity("character","the ability to do something");
//        mWordEntityList.add(wordEntity);
//        wordEntity = new WordEntity("drawing","to improve the quality, amount, or strength of something");
//        mWordEntityList.add(wordEntity);
//
//        wordEntity = new WordEntity("character","the ability to do something");
//        mWordEntityList.add(wordEntity);


    }

    @Override
    public void onSelected(WordEntity wordEntity) {
        if (mChosenWords.isEmpty()) {

            mDetectActivityCallback.activateFab();

//            mFab.setBackgroundTintList(ColorStateList.valueOf(SpeedyApplication.getAppContext().getResources().getColor(R.color.secondaryColorDark)));
//            mFab.setImageDrawable(SpeedyApplication.getAppContext().getResources().getDrawable(R.drawable.ic_file_selected));
//            mFab.setClickable(true);
        }
        mChosenWords.add(wordEntity);
//        Log.d("Chosen word","add:" + wordEntity.getWord());

//        for (WordEntity w : mChosenWords) {
//            Log.d("Chosen word","add  = " + w.getWord());
//        }



    }

    @Override
    public void onRemoved(WordEntity wordEntity) {
        Iterator<WordEntity> iterator = mChosenWords.iterator();
        while (iterator.hasNext()) {
            WordEntity word = iterator.next();
            if (word.getWord().equals(wordEntity.getWord())) {

//                    Log.d("chosen word"," size " + word.getWord());

                iterator.remove();
                break;
            }
        }

        for (WordEntity w : mChosenWords) {
//            Log.d("Chosen word"," = " + w.getWord());
        }

        if (mChosenWords.isEmpty()) {

            mDetectActivityCallback.deactivateFab();
//            mFab.setBackgroundTintList(ColorStateList.valueOf(SpeedyApplication.getAppContext().getResources().getColor(R.color.colorAccent)));
//            mFab.setImageDrawable(SpeedyApplication.getAppContext().getResources().getDrawable(R.drawable.ic_file_unselected));
//            mFab.setClickable(false);
        }
    }

    @Override
    public void onBottomSheetCollapsed(boolean isAdded, StackEntity stackEntity) {
        if (isAdded) {


            Iterator<WordEntity> iterator = mWordEntityList.iterator();

            while (iterator.hasNext()) {
                WordEntity current = iterator.next();
                if (current.isSelected()) {

                    current.setStackId(stackEntity.getId());
                    current.setStackName(stackEntity.getStackName());

                    mPresenter.addWord(current);
                    iterator.remove();
                }
            }

            mDetectActivityCallback.deactivateFab();

//            mFab.setBackgroundTintList(ColorStateList.valueOf(SpeedyApplication.getAppContext().getResources().getColor(R.color.colorAccent)));
//            mFab.setImageDrawable(SpeedyApplication.getAppContext().getDrawable(R.drawable.ic_file_unselected));
//            mFab.setClickable(false);
            mDetectActivityCallback.updateTabCountHint(mAdapter.getItemCount());
            mChosenWords.clear();
            mAdapter.notifyDataSetChanged();
        } else {
//            mAdapter.resetChosenWordsColor();

        }
    }

    @Override
    public void onAddedToChosenFragment(WordEntity wordEntity) {

        if (mDuplicateCheck.contains(wordEntity.getWord())) {
            mDetectActivityCallback.isWordDuplicate(true);
        } else {
            mDuplicateCheck.add(wordEntity.getWord());
            mDetectActivityCallback.isWordDuplicate(false);
            mAdapter.addWord(wordEntity);
            mDetectActivityCallback.updateTabCountHint(mAdapter.getItemCount());
        }

    }

    @Override
    public void onAddedToLocalDatabase(List<WordEntity> wordEntityList) {

    }

    @Override
    public void setPresenter(ChosenWordContract.Presenter presenter) {
        mPresenter = presenter;

    }

    @Override
    public void setFab(FloatingActionButton fab) {
//        mFab = fab;
    }
}
