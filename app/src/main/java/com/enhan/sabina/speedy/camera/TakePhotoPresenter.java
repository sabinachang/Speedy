package com.enhan.sabina.speedy.camera;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.enhan.sabina.speedy.callbacks.TakePhotoCallback;
import com.enhan.sabina.speedy.data.DataRepository;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePhotoPresenter implements TakePhotoContract.Presenter{
    private final TakePhotoContract.View mTakePhotoView;
    private final DataRepository mDataRepository;
    private final CameraActivity mCameraActivity;
    private static final String FILEPROVIDER_PATH = "com.enhan.sabina.speedy.fileprovider";

    public TakePhotoPresenter(TakePhotoContract.View takePhotoView,CameraActivity activity) {
        mTakePhotoView = takePhotoView;
        mDataRepository = DataRepository.getInstance();
        mCameraActivity = activity;
    }

    @Override
    public void start() {

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mCameraActivity.getExternalFilesDir(mDataRepository.providePictureDirectory());
        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = mDataRepository.getPhotoIntent();
        if (takePictureIntent.resolveActivity(mCameraActivity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }

            if (photoFile != null) {
                Uri photoUri = mDataRepository.getUriFromFileProvider(mCameraActivity,FILEPROVIDER_PATH,photoFile);
                takePictureIntent.putExtra(mDataRepository.getMediaOutputString(), photoUri);
                mTakePhotoView.startCameraIntent(takePictureIntent,photoUri);

            }
        }
    }

    @Override
    public void launchCamera() {
        dispatchTakePictureIntent();
    }
}
