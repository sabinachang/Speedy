package com.enhan.sabina.speedy.detect;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.callbacks.SelectTextCallback;
import com.enhan.sabina.speedy.utils.textselect.TextSelectView;

public class DisplayTextFragment extends Fragment implements DisplayTextContract.View, SelectTextCallback {

    private DisplayTextContract.Presenter mPresenter;
    private LinearLayout mLinearLayout;
    private NestedScrollView mScrollView;
    private DetectActivity mTagCallbackListener;
    private SelectTextCallback mTextSelectCallback;

    public DisplayTextFragment() {

    }

    public static DisplayTextFragment newInstance() {
        return  new DisplayTextFragment();
    }

    @Override
    public void setPresenter(DisplayTextContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_text,container,false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            mTagCallbackListener = (DetectActivity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        mPresenter = new DisplayTextPresenter(this,mTagCallbackListener);
        ((DetectActivity)getActivity()).setSupportActionBar(toolbar);
        mLinearLayout = view.findViewById(R.id.layout_text);
        mScrollView = view.findViewById(R.id.scroll_view);
        mTextSelectCallback = this;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void displayDetectedText(final String detectedText) {
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                int width = mScrollView.getWidth();
                mLinearLayout.addView(new TextSelectView(getActivity(),detectedText,width,mTextSelectCallback));
            }
        });
    }

    @Override
    public void onWordSelected(String word) {
        mTagCallbackListener.updateTagline(word);
    }
}
