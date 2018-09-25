package com.enhan.sabina.speedy.callbacks;

import android.graphics.Bitmap;

public interface TakePhotoCallback {
    void onPhotoTaken(Bitmap bitmap);
    void onFailed();
}

