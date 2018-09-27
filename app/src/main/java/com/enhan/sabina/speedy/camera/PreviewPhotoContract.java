package com.enhan.sabina.speedy.camera;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;
import com.enhan.sabina.speedy.data.DataRepository;

public interface PreviewPhotoContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter {
        void onPhotoAccepted();
        void onPhotoDenied();
        String providePath();
        DataRepository provideDataRepository();
    }
}
