package com.enhan.sabina.speedy.data.constants;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.enhan.sabina.speedy.data.DataSource;

import java.io.File;

public class AndroidData implements DataSource.Constants{
    private static AndroidData INSTANCE = null;

    private AndroidData() {

    }
    public static AndroidData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AndroidData();
            return INSTANCE;
        }
        return INSTANCE;
    }
    @Override
    public Intent getTakePhotoIntent() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    @Override
    public Uri getUriFromFileProvider(Activity activity, String authority, File file) {
        return FileProvider.getUriForFile(activity,authority,file);
    }

    @Override
    public String getMediaOutputString() {
        return MediaStore.EXTRA_OUTPUT;
    }

    @Override
    public String providePictureDirectory() {
        return Environment.DIRECTORY_PICTURES;
    }

}
