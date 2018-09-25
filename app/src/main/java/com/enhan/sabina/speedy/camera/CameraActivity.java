package com.enhan.sabina.speedy.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.PreviewPhotoCallback;
import com.enhan.sabina.speedy.callbacks.TakePhotoCallback;

public class CameraActivity extends AppCompatActivity implements TakePhotoCallback,PreviewPhotoCallback {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 101;
    private static final String TAKE_PHOTO = "TAKE_PHOTO";
    private static final String PREVIEW_PHOTO = "PREVIEW_PHOTO";
    private static final String TAG = "Camera activity" ;
    private CameraPreview mCameraPreview;
    private Camera mCamera;
    private Button mButton;
    private Bitmap mBitmap;

    private FrameLayout mCameraPreviewLayout;
    private ImageView mCapturedImageView;

    private TakePhotoFragment mTakePhotoFragment;
    private PreviewPhotoFragment mPreviewPhotoFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (ContextCompat.checkSelfPermission(CameraActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(CameraActivity.this, Manifest.permission.CAMERA)) {


            } else {
                ActivityCompat.requestPermissions(CameraActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }

        } else {
            transToTakePhoto();
        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    transToTakePhoto();
                }
            }
        }
    }

    private void transToTakePhoto() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        if (fragmentManager.findFragmentByTag(PREVIEW_PHOTO) != )
//        if (mTakePhotoFragment == null) mTakePhotoFragment = TakePhotoFragment.newInstance();
//        if (mPreviewPhotoFragment != null ) transaction.hide(mPreviewPhotoFragment);
        transaction.replace(R.id.fragment_holder,TakePhotoFragment.newInstance());
        transaction.commit();
    }

    private void transToPreviewPhoto(Bitmap bitmap) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("bitmap_image",bitmap);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        PreviewPhotoFragment fragment = PreviewPhotoFragment.newInstance();
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragment_holder,fragment);
        transaction.commit();


    }



//    private void prepareCamera() {
//        mCamera = checkCameraHardware(SpeedyApplication.getAppContext());
//        mCameraPreview = new CameraPreview(CameraActivity.this,mCamera,mButton,mCapturedImageView);
//
//        mCameraPreviewLayout.addView(mCameraPreview);
//    }

//    private Camera checkCameraHardware(Context context) {
//        Camera camera = null;
//        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
//            try {
//                camera = Camera.open();
//
//            } catch (Exception e) {
//                Log.d("Camera Activity","Sth wrong");
//                e.printStackTrace();
//            }
//
//        }
//        return camera;
//    }

    @Override
    public void onPhotoTaken(Bitmap bitmap) {
        mBitmap = bitmap;
        transToPreviewPhoto(bitmap);
    }



    @Override
    public void onFailed() {
        Log.d(TAG,"take photo failed");
    }

    @Override
    public void onPhotoAccepted() {
        // call appropriate activity
        Log.d(TAG,"photo accepted");
    }

    @Override
    public void onPhotoDenied() {
        mBitmap = null;
        Log.d(TAG,"photo denied");
        transToTakePhoto();
    }
}
