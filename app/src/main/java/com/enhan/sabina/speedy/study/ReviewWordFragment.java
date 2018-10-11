package com.enhan.sabina.speedy.study;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReviewWordFragment extends Fragment implements ReviewWordContract.View {

    public ReviewWordFragment() {

    }

    public static ReviewWordFragment newInstance() {
        return new ReviewWordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setPresenter(ReviewWordContract.Presenter presenter) {

    }


}
