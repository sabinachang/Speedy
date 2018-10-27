package com.enhan.sabina.speedy.callbacks;

import android.graphics.Bitmap;
import android.net.Uri;

public interface TakePhotoCallback {
    void onPhotoTaken(Uri uri);
    void startCroppingActivity(Uri uri);
}

