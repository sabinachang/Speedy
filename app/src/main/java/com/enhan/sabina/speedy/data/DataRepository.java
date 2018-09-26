package com.enhan.sabina.speedy.data;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.enhan.sabina.speedy.data.constants.AndroidData;

import java.io.File;

public class DataRepository implements DataSource.Repository{

    private static DataRepository INSTANCE = null;
    private final AndroidData mAndroidDataSource;

    private DataRepository(AndroidData androidDataSource) {
        mAndroidDataSource = androidDataSource;
    }

    public static DataRepository getInstance(AndroidData androidDataSource) {
            if (INSTANCE == null) {
                INSTANCE = new DataRepository(androidDataSource);
            }
            return INSTANCE;
    }

    @Override
    public Intent getPhotoIntent() {
        return mAndroidDataSource.getTakePhotoIntent();
    }

    @Override
    public Uri getUriFromFileProvider(Activity activity, String authority, File file) {
        return mAndroidDataSource.getUriFromFileProvider(activity,authority,file);
    }

    @Override
    public String getMediaOutputString() {
        return mAndroidDataSource.getMediaOutputString();
    }

    @Override
    public String providePictureDirectory() {
        return Environment.DIRECTORY_PICTURES;
    }

}
