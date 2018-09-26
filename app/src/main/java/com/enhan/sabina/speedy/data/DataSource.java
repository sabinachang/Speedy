package com.enhan.sabina.speedy.data;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;

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
    }
}
