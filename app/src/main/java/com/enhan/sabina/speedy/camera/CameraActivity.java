package com.enhan.sabina.speedy.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.callbacks.PreviewPhotoCallback;
import com.enhan.sabina.speedy.callbacks.TakePhotoCallback;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.constants.AndroidData;

public class CameraActivity extends AppCompatActivity implements CameraContract.View,TakePhotoCallback,PreviewPhotoCallback {

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
    private DataRepository mDataRepository;

    public static final int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mDataRepository = DataRepository.getInstance(AndroidData.getInstance());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onresumed");
    }

    private void transToTakePhoto() {
        Log.d(TAG,"transToTakePhoto");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        TakePhotoFragment takePhotoFragment = TakePhotoFragment.newInstance();
        new TakePhotoPresenter(takePhotoFragment,mDataRepository);
//        if (fragmentManager.findFragmentByTag(PREVIEW_PHOTO) != )
//        if (mTakePhotoFragment == null) mTakePhotoFragment = TakePhotoFragment.newInstance();
//        if (mPreviewPhotoFragment != null ) transaction.hide(mPreviewPhotoFragment);
        transaction.replace(R.id.fragment_holder,takePhotoFragment);
        transaction.commit();
    }

    private void transToPreviewPhoto(Uri path) {

        Bundle bundle = new Bundle();
        bundle.putString("image_path",path.toString());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        PreviewPhotoFragment fragment = PreviewPhotoFragment.newInstance();
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragment_holder,fragment);
        transaction.commit();


    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG,"onpause activity");
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
    public void onPhotoTaken(Uri uri) {


        transToPreviewPhoto(uri);
    }

//    private Uri createImageFile(Bitmap bitmap) {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "Speedy_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//
//        File imageDir = new File(storageDir,imageFileName);
//        try {
//            OutputStream stream = null;
//            stream = new FileOutputStream(imageDir);
//            bitmap.compress(Bitmap.CompressFormat.JPEG,90,stream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Log.d(TAG,imageDir.getAbsolutePath());
//        return Uri.fromFile(imageDir);
//    }


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

    @Override
    public void setPresenter(CameraContract.Presenter presenter) {

    }
}
