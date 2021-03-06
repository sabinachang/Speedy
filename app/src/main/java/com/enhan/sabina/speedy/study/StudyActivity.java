package com.enhan.sabina.speedy.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.crashlytics.android.Crashlytics;
import com.enhan.sabina.speedy.MainActivity;
import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.callbacks.StudyCallback;

import io.fabric.sdk.android.Fabric;

public class StudyActivity extends AppCompatActivity implements StudyContract.View,StudyCallback{
    private StudyContract.Presenter mPresenter;
    private final String CHOOSE_STACK = "choose_stack";
    private final String REVIEW_WORDS = "review_words";

    private Toolbar mToolbar;
    private ChooseStackFragment mChooseStackFragment;
    private ReviewWordFragment mReviewWordFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_study);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.secondaryColorDark));

        startChooseStack();
    }

    private void startChooseStack() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (mChooseStackFragment == null){
            mChooseStackFragment = ChooseStackFragment.newInstance();
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

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();
            startActivity(new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
