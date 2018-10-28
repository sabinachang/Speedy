package com.enhan.sabina.speedy.callbacks;

import android.net.Uri;

public interface CameraActivityCallback {
    void onPhotoTaken(Uri uri);

    void startCroppingActivity(Uri uri);

    void onPhotoAccepted();

    void onPhotoDenied();

    void prepareCameraIntent();
}
