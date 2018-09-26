package com.enhan.sabina.speedy.camera;

import android.content.Context;
import android.os.Environment;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;

public interface TakePhotoContract {
    interface View extends BaseView<Presenter> {
        Context provideActivity();
        String providePictureDirectory();
    }
    interface Presenter extends BasePresenter {
        void takePhoto();
        void launchCamera();
    }
}
