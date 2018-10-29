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
        mAdapter = new ChosenWordAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSelected(WordEntity wordEntity) {
       mPresenter.wordSelected(wordEntity);
    }

    @Override
    public void onRemoved(WordEntity wordEntity) {
        mPresenter.wordRemoved(wordEntity);
    }

    @Override
    public void onBottomSheetCollapsed(boolean isAdded, StackEntity stackEntity) {
        if (isAdded) {
            mPresenter.updateWordEntityList(stackEntity,mAdapter.getWordEntityList());
        }
    }

    @Override
    public void onAddedToChosenFragment(WordEntity wordEntity) {
        mPresenter.checkIfDuplicated(wordEntity);
    }

    @Override
    public void setPresenter(ChosenWordContract.Presenter presenter) {

    }

    @Override
    public void notifyAdapterDatasetChange() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addToAdapter(WordEntity wordEntity) {
        mAdapter.addWord(wordEntity);
    }

    @Override
    public int getAdapterDatasetCount() {
        return mAdapter.getItemCount();
    }


}
