package com.enhan.sabina.speedy.camera;

import android.content.Intent;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;

public interface TakePhotoContract {
    interface View extends BaseView<Presenter> {
        void startCameraIntent(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void prepareCroppingActivity();

        void launchCamera();
    }
}
