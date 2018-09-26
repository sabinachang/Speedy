package com.enhan.sabina.speedy.camera;

import com.enhan.sabina.speedy.callbacks.PreviewPhotoCallback;

public class PreviewPhotoPresenter implements PreviewPhotoContract.Presenter{

    private PreviewPhotoContract.View mPreviewPhotoView;
    private PreviewPhotoCallback mPreviewPhotoCallback;
    private String mImagePath;

    public PreviewPhotoPresenter(PreviewPhotoContract.View prieviewPhotoView, CameraActivity activity, String path) {
        mPreviewPhotoView = prieviewPhotoView;
        mPreviewPhotoView.setPresenter(this);
        mPreviewPhotoCallback = (PreviewPhotoCallback) activity;
        mImagePath = path;
//        mDataRepository = dataRepository;
//        mCameraActivity = activity;
//        mTakePhotoCallback = (TakePhotoCallback) activity;
    }

    @Override
    public void onPhotoAccepted() {
        mPreviewPhotoCallback.onPhotoAccepted();
    }

    @Override
    public void onPhotoDenied() {
        mPreviewPhotoCallback.onPhotoDenied();
    }

    @Override
    public String providePath() {
        return mImagePath;
    }

    @Override
    public void start() {

    }


}
