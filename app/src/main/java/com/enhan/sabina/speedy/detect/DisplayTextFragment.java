package com.enhan.sabina.speedy.detect;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.camera.TakePhotoFragment;
import com.enhan.sabina.speedy.utils.textselect.TextSelectView;

public class DisplayTextFragment extends Fragment implements DisplayTextConstract.View {

    private DisplayTextConstract.Presenter mPresenter;
    private TextView mDisplayTextView;
    private LinearLayout mLinearLayout;
    private ScrollView mScrollView;
    private TextSelectView mTextSelectView;
    private View view;

    public DisplayTextFragment() {

    }

    public static DisplayTextFragment newInstance() {
        return  new DisplayTextFragment();
    }

    @Override
    public void setPresenter(DisplayTextConstract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_text,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        mDisplayTextView = view.findViewById(R.id.detected_text);
        mLinearLayout = view.findViewById(R.id.layout_text);
        mScrollView = view.findViewById(R.id.scroll_view);
//        mLinearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.primaryColor));
//        mTextSelectView = view.findViewById(R.id.text_detect);
//        this.view = view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void displayDetectedText(final String detectedText) {
        Log.d("DisplayText","text = " + detectedText);
//        ((TextSelectView)view.findViewById(R.id.text_detect)).setTextData(detectedText);

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mLinearLayout.setLayoutParams(params);
//        ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
//        Log.d("Display text", "w ="+ layoutParams.width);
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                int width = mScrollView.getWidth();
                int height = mScrollView.getHeight();
                mLinearLayout.addView(new TextSelectView(getActivity(),detectedText,width));
            }
        });

//                int width = mLinearLayout.getWidth();
//                int height = mLinearLayout.getHeight();
//                Log.d("displayText","w+" + width);
//                mLinearLayout.addView(new TextSelectView(getActivity(),detectedText),width,height);
//
//            }



//        mDisplayTextView.setText(detectedText);
    }
}
