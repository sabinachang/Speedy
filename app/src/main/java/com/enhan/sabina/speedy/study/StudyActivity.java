package com.enhan.sabina.speedy.study;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.callbacks.StudyCallback;

public class StudyActivity extends AppCompatActivity implements StudyContract.View,StudyCallback{


    private StudyContract.Presenter mPresenter;
    private final String CHOOSE_STACK = "choose_stack";
    private final String REVIEW_WORDS = "review_words";

    private Toolbar mToolbar;
    private FrameLayout mFrameLayout;
    private ChooseStackFragment mChooseStackFragment;
    private ChooseStackPresenter mChooseStackPresenter;
    private ReviewWordFragment mReviewWordFragment;
    private ReviewWordPresenter mReviewWordPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mFrameLayout = findViewById(R.id.study_fragment_holder);

//        mPresenter = new StudyPresenter(this,mAdapter);
//        mPresenter.start();
        startChooseStack();
    }

    private void startChooseStack() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (mChooseStackFragment == null){
            mChooseStackFragment = ChooseStackFragment.newInstance();
//            mChooseStackPresenter = new ChooseStackPresenter(mChooseStackFragment);
        }

        if (mReviewWordFragment != null) transaction.hide(mReviewWordFragment);

        if (!mChooseStackFragment.isAdded()) {
            transaction.add(R.id.study_fragment_holder,mChooseStackFragment,CHOOSE_STACK);
        } else {
            transaction.show(mChooseStackFragment);
        }

        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void startReviewWords(String stackName) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (mReviewWordFragment == null) {
            mReviewWordFragment = ReviewWordFragment.newInstance();

        }

        Bundle bundle = new Bundle();
        bundle.putString("stackName",stackName);
        mReviewWordFragment.setArguments(bundle);

        if (mChooseStackFragment != null) transaction.hide(mChooseStackFragment);

        if (!mReviewWordFragment.isAdded()) {
            transaction.add(R.id.study_fragment_holder,mReviewWordFragment,REVIEW_WORDS);
        } else {
            transaction.show(mReviewWordFragment);
        }

        transaction.addToBackStack(null);
        transaction.commit();
    }



    @Override
    public void setPresenter(StudyContract.Presenter presenter) {

    }


    @Override
    public void transToReviewWords(String stackName) {
        startReviewWords(stackName);
    }
}
