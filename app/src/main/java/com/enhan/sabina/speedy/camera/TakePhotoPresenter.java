package com.enhan.sabina.speedy.camera;

import com.enhan.sabina.speedy.data.DataRepository;

public class TakePhotoPresenter implements TakePhotoContract.Presenter{
    private final TakePhotoContract.View mTakePhotoView;
    private final DataRepository mDataRepository;
    private final CameraNavigator mCameraNavigator;

    public TakePhotoPresenter(TakePhotoContract.View takePhotoView) {
        mTakePhotoView = takePhotoView;
        mDataRepository = DataRepository.getInstance();
        mCameraNavigator = CameraNavigator.getInstance();
    }

    @Override
    public void start() {

    }

    @Override
    public void prepareCroppingActivity() {
        mCameraNavigator.startCroppingActivityNavigator(mDataRepository.getPhotoUri());
    }

    @Override
    public void launchCamera() {
        mCameraNavigator.createCameraIntent();
        mTakePhotoView.startCameraIntent(mDataRepository.getPhotoIntent());
    }
}
