package com.enhan.sabina.speedy.camera;

public interface CameraContract {
    interface Navigator {
        void onPhotoAcceptedNavigator();

        void onPhotoDeniedNavigator();
    }

}
