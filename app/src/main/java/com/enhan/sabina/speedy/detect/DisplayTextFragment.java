package com.enhan.sabina.speedy.detect;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.camera.TakePhotoFragment;

public class DisplayTextFragment extends Fragment implements DisplayTextConstract.View {

    private DisplayTextConstract.Presenter mPresenter;
    private TextView mDisplayTextView;

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
        mDisplayTextView = view.findViewById(R.id.detected_text);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void displayDetectedText(String detectedText) {
        Log.d("DisplayText","text = " + detectedText);
        mDisplayTextView.setText(detectedText);
    }
}
