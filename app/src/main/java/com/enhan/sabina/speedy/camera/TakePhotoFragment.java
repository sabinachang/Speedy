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

    private static final String TAG = "Take photo fragment" ;
    private static final int REQUEST_TAKE_PHOTO = 102;
    private CameraPreview mCameraPreview;
    private FrameLayout mCameraPreviewLayout;
    private TakePhotoCallback mTakePhotoCallback;
    private Camera mCamera;
    private Button mButton;
    private final int PICTURE_SIZE_MAX_WIDTH = 1280;
    private final int PREVIEW_SIZE_MAX_WIDTH = 640;
    private int mDisplayOrientation;
    private int mLayoutOrientation;
    private SurfaceHolder mHolder;
    private Uri mPhotoUri;
    private Uri mLocalUri;
    private TakePhotoContract.Presenter mPresenter;
    private CameraActivity mActivity;
    private ImageView mOk;

    public TakePhotoFragment() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CameraActivity) {
            mTakePhotoCallback = (TakePhotoCallback) context;
        }
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
        mOk = view.findViewById(R.id.btn_capture);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.launchCamera();
            }
        });
//        mPresenter.launchCamera();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

//        Log.d(TAG,"in onactivityResult");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO :
                    if (null != mPhotoUri) {
                        mLocalUri = mPhotoUri;
//                        Log.d(TAG,"" + mLocalUri);
                        mTakePhotoCallback.startCroppingActivity(mLocalUri);

//                        mPresenter.onPhotoReceived(mLocalUri);

                    }
                    break;

            }
        }
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.d(TAG,"on stop");
    }

    @Override
    public void setPresenter(TakePhotoContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void startCameraIntent(Intent intent,Uri photoUri) {
        mPhotoUri = photoUri;
//        Log.d(TAG,"starting take photo ");

        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }
}
