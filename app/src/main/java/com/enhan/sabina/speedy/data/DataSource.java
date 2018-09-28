package com.enhan.sabina.speedy.data;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.enhan.sabina.speedy.callbacks.DetectTextCallback;
import com.enhan.sabina.speedy.callbacks.ProcessTextCallback;

import java.io.File;

public interface DataSource {
    interface Constants {
        Intent getTakePhotoIntent();
        Uri getUriFromFileProvider(Activity activity, String authority, File file);
        String getMediaOutputString();
        String providePictureDirectory();
    }

    interface Repository {
        Intent getPhotoIntent();
        Uri getUriFromFileProvider(Activity activity, String authority, File file);
        String getMediaOutputString();
        String providePictureDirectory();
        void storeImage(Bitmap bitmap);
        Bitmap retrieveImageForGoogle();
        void startTextDetection(DetectTextCallback callback, Bitmap bitmap);
    }

    interface Local {
        void storeImageForGoogle(Bitmap bitmap);
        Bitmap retrieveImageForGoogle();
    }
}
