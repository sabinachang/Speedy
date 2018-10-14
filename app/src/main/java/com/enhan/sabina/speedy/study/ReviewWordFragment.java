package com.enhan.sabina.speedy.study;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.callbacks.ReviewWordCallback;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

public class ReviewWordFragment extends Fragment implements ReviewWordContract.View,ReviewWordCallback {

    private ReviewWordContract.Presenter mPresenter;
    private DiscreteScrollView mDiscreteScrollView;
    private String mStackName;

    public ReviewWordFragment() {

    }

    public static ReviewWordFragment newInstance() {
        return new ReviewWordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_words,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mDiscreteScrollView = view.findViewById(R.id.flashcards);
        mPresenter = new ReviewWordPresenter(this);
        mStackName = getArguments().getString("stackName");
        Log.d("Review","stack name = " + mStackName);

        mPresenter.getWordsInStack(mStackName);


    }

    @Override
    public void setPresenter(ReviewWordContract.Presenter presenter) {
//        mPresenter = presenter;
//        mStackName = getArguments().getString("stackName");
//        mPresenter.getWordsInStack(mStackName);
    }


    @Override
    public void returnWordsInStack(List<WordEntity> wordEntityList) {

        Log.d("Review","return words" );
        mDiscreteScrollView.setOffscreenItems(2);
        mDiscreteScrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.BOTTOM)
                .build());
        mDiscreteScrollView.setAdapter(new ReviewWordAdapter(wordEntityList));
    }


}
