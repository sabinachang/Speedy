package com.enhan.sabina.speedy.camera;

import android.graphics.Bitmap;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;
import com.enhan.sabina.speedy.data.DataRepository;

public interface PreviewPhotoContract {
    interface View extends BaseView<Presenter>{
        void displayCompressedImage(Bitmap compressedImage);
    }

    interface Presenter extends BasePresenter {
        void onPhotoAccepted();

        void onPhotoDenied();

        void storeImageForScan(Bitmap bitmap);

        void compressImageForDisplay(String imagePath);
    }
}
