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
import com.enhan.sabina.speedy.utils.UcropInitializeUtil;
import com.yalantis.ucrop.UCrop;

import io.fabric.sdk.android.Fabric;

import java.io.File;

import javax.xml.parsers.SAXParser;

public class CameraActivity extends AppCompatActivity implements TakePhotoCallback,PreviewPhotoCallback {

    private static final String TAKE_PHOTO = "TAKE_PHOTO";
    private static final String PREVIEW_PHOTO = "PREVIEW_PHOTO";
    private static final String IMAGE_PATH = "temp_image.png";
    private static final String PATH_KEY = "path_key";
    private static final int MAX_DIMENSION = 1000;
    private static final int RATIO_X = 4;
    private static final int RATIO_Y = 3;

    private TakePhotoFragment mTakePhotoFragment;
    private PreviewPhotoFragment mPreviewPhotoFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_camera);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.secondaryColorDark));

        CameraNavigator.getInstance().setCameraActivity(this);

        transToTakePhoto();
    }

    private void transToTakePhoto() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        TakePhotoFragment takePhotoFragment = TakePhotoFragment.newInstance();
        transaction.replace(R.id.fragment_holder,takePhotoFragment,TAKE_PHOTO);
        transaction.commit();
    }

    private void transToPreviewPhoto(Uri path) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        PreviewPhotoFragment fragment = PreviewPhotoFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString(PATH_KEY,path.toString());
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragment_holder,fragment,PREVIEW_PHOTO);
        transaction.commit();
    }

    @Override
    public void startCroppingActivity(Uri uri) {
        UCrop.Options options = UcropInitializeUtil.initializeOptions();
        UCrop.of(uri,Uri.fromFile(new File(getCacheDir(),IMAGE_PATH)))
                .withMaxResultSize(MAX_DIMENSION,MAX_DIMENSION)
                .withAspectRatio(RATIO_X,RATIO_Y)
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
    public void onPhotoTaken(Uri uri) {
        transToPreviewPhoto(uri);
    }

    @Override
    public void onPhotoAccepted() {
        Intent detectionActivity = new Intent(this, DetectActivity.class);
        startActivity(detectionActivity);
    }

    @Override
    public void onPhotoDenied() {
        transToTakePhoto();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            startActivity(new Intent(this,MainActivity.class));
        }
        super.onBackPressed();
    }
}
