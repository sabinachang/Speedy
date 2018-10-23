package com.enhan.sabina.speedy.camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.enhan.sabina.speedy.MainActivity;
import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.PreviewPhotoCallback;
import com.enhan.sabina.speedy.callbacks.TakePhotoCallback;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.constants.AndroidData;
import com.enhan.sabina.speedy.data.local.LocalDataRepository;
import com.enhan.sabina.speedy.detect.DetectActivity;
import com.yalantis.ucrop.UCrop;

import io.fabric.sdk.android.Fabric;

import java.io.File;

import javax.xml.parsers.SAXParser;

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
    private DataRepository mDataRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_camera);

        mDataRepository = DataRepository.getInstance();

        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.secondaryColorDark));

        transToTakePhoto();
    }

    private void transToTakePhoto() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        TakePhotoFragment takePhotoFragment = TakePhotoFragment.newInstance();
        new TakePhotoPresenter(takePhotoFragment,mDataRepository,this);
        transaction.replace(R.id.fragment_holder,takePhotoFragment);
        transaction.commit();
    }

    private void transToPreviewPhoto(Uri path) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        PreviewPhotoFragment fragment = PreviewPhotoFragment.newInstance();
        new PreviewPhotoPresenter(fragment,mDataRepository,this,path.toString());

        transaction.replace(R.id.fragment_holder,fragment,"preview_photo");
        transaction.commit();
    }

    @Override
    public void onPhotoTaken(Uri uri) {
        transToPreviewPhoto(uri);
    }

    @Override
    public void onFailed() {
    }

    @Override
    public void startCroppingActivity(Uri uri) {
        cropRawPhoto(uri);
    }

    @Override
    public void onPhotoAccepted() {
        Intent detectionActivity = new Intent(this, DetectActivity.class);
        startActivity(detectionActivity);

    }


    private void cropRawPhoto(Uri localUri) {
        UCrop.Options options = new UCrop.Options();

        options.setToolbarColor(ContextCompat.getColor(SpeedyApplication.getAppContext(),R.color.secondaryColorDark));
        options.setStatusBarColor(ContextCompat.getColor(SpeedyApplication.getAppContext(),R.color.secondaryColorDark));
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setToolbarTitle("");
        options.setRootViewBackgroundColor(ContextCompat.getColor(SpeedyApplication.getAppContext(),R.color.primaryColor));
        options.setCropFrameColor(ContextCompat.getColor(SpeedyApplication.getAppContext(),R.color.colorAccent));
        options.setToolbarWidgetColor(ContextCompat.getColor(SpeedyApplication.getAppContext(),R.color.colorAccent));
        options.setCompressionQuality(100);
        options.setFreeStyleCropEnabled(true);
        options.setActiveWidgetColor(ContextCompat.getColor(SpeedyApplication.getAppContext(),R.color.secondaryColorDark));
        options.setShowCropGrid(true);

        UCrop.of(localUri,Uri.fromFile(new File(getCacheDir(),"temp_image.png")))
                .withMaxResultSize(1000,1000)
                .withAspectRatio(4,3)
                .withOptions(options)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
           onPhotoTaken(UCrop.getOutput(data));
        }
    }

    @Override
    public void onPhotoDenied() {
        mBitmap = null;
        transToTakePhoto();
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            Log.d(TAG,"back pressed");
            startActivity(new Intent(this,MainActivity.class));
        }
        super.onBackPressed();
    }
}
