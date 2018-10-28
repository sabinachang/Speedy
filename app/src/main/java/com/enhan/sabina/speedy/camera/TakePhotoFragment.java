package com.enhan.sabina.speedy.camera;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.enhan.sabina.speedy.R;

public class TakePhotoFragment extends Fragment implements TakePhotoContract.View {

    private static final int REQUEST_TAKE_PHOTO = 102;
    private TakePhotoContract.Presenter mPresenter;
    private ImageView mOk;

    public TakePhotoFragment() {

    }

    public static TakePhotoFragment newInstance() {
        return  new TakePhotoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_take_photo,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPresenter = new TakePhotoPresenter(this);
        mOk = view.findViewById(R.id.btn_capture);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.launchCamera();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_TAKE_PHOTO) {
            mPresenter.prepareCroppingActivity();
        }
    }

    @Override
    public void setPresenter(TakePhotoContract.Presenter presenter) {
    }

    @Override
    public void startCameraIntent(Intent intent) {
        if (intent != null) {
            startActivityForResult(intent,REQUEST_TAKE_PHOTO);
        }
    }
}
