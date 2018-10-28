package com.enhan.sabina.speedy.camera;

import android.net.Uri;

import com.enhan.sabina.speedy.callbacks.CameraActivityCallback;

public class CameraNavigator implements CameraContract.Navigator {

    private CameraActivityCallback mCameraActivityCallback;
    private static CameraNavigator INSTANCE;

    private CameraNavigator() {

    }

    public static CameraNavigator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CameraNavigator();
        }
        return INSTANCE;
    }

    @Override
    public void onPhotoAcceptedNavigator() {
        if (mCameraActivityCallback != null) {
            mCameraActivityCallback.onPhotoAccepted();
        }
    }

    @Override
    public void onPhotoDeniedNavigator() {
        if (mCameraActivityCallback != null) {
            mCameraActivityCallback.onPhotoDenied();
        }
    }

    @Override
    public void startCroppingActivityNavigator(Uri uri) {
        if (mCameraActivityCallback != null) {
            mCameraActivityCallback.startCroppingActivity(uri);
        }
    }

    @Override
    public void createCameraIntent() {
        if (mCameraActivityCallback != null) {
            mCameraActivityCallback.prepareCameraIntent();
        }
    }

    public void setCameraActivity(CameraActivityCallback callback) {
        mCameraActivityCallback = callback;
    }

}
