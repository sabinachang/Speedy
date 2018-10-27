package com.enhan.sabina.speedy.camera;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;

public interface TakePhotoContract {
    interface View extends BaseView<Presenter> {
        void startCameraIntent(Intent intent,Uri photoUri);
    }
    interface Presenter extends BasePresenter {
        void launchCamera();
    }
}
