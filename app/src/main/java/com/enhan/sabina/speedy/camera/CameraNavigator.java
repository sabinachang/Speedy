package com.enhan.sabina.speedy.camera;

import com.enhan.sabina.speedy.callbacks.PreviewPhotoCallback;

public class CameraNavigator implements CameraContract.Navigator {

    private PreviewPhotoCallback mPreviewPhotoCallback;
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
        if (mPreviewPhotoCallback != null) {
            mPreviewPhotoCallback.onPhotoAccepted();
        }
    }

    @Override
    public void onPhotoDeniedNavigator() {
        if (mPreviewPhotoCallback != null) {
            mPreviewPhotoCallback.onPhotoDenied();
        }
    }


    public void setCameraActivity(PreviewPhotoCallback callback) {
        mPreviewPhotoCallback = callback;
    }

}
