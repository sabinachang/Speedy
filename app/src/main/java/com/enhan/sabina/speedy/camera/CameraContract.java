package com.enhan.sabina.speedy.camera;

import android.content.Intent;
import android.net.Uri;

public interface CameraContract {
    interface Navigator {
        void onPhotoAcceptedNavigator();

        void onPhotoDeniedNavigator();

        void startCroppingActivityNavigator(Uri uri);

        void createCameraIntent();
    }

}
