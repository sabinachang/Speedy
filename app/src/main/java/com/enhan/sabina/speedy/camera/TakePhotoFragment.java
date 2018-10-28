package com.enhan.sabina.speedy.camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.TakePhotoCallback;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class TakePhotoFragment extends Fragment implements TakePhotoContract.View{

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
