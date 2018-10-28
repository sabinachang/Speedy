package com.enhan.sabina.speedy.camera;

import android.graphics.Bitmap;

import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.utils.ImageUtils;

public class PreviewPhotoPresenter implements PreviewPhotoContract.Presenter{
    private PreviewPhotoContract.View mPreviewPhotoView;
    private DataRepository mDataRepository;
    private CameraNavigator mCameraNavigator;

    public PreviewPhotoPresenter(PreviewPhotoContract.View previewPhotoView) {
        mPreviewPhotoView = previewPhotoView;
        mDataRepository = DataRepository.getInstance();
        mCameraNavigator = CameraNavigator.getInstance();
    }

    @Override
    public void onPhotoAccepted() {
        mCameraNavigator.onPhotoAcceptedNavigator();
    }

    @Override
    public void onPhotoDenied() {
        mCameraNavigator.onPhotoDeniedNavigator();
    }

    @Override
    public void storeImageForScan(Bitmap bitmap) {
        mDataRepository.storeImage(bitmap);
    }

    @Override
    public void compressImageForDisplay(String imagePath) {
        mDataRepository.storeCompressedImage(ImageUtils.getCompressBitmap(imagePath));
        mPreviewPhotoView.displayCompressedImage(mDataRepository.retrieveCompressedImage());
    }


    @Override
    public void start() {

    }
}
