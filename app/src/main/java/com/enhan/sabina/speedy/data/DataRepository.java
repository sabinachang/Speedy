package com.enhan.sabina.speedy.data;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.enhan.sabina.speedy.data.constants.AndroidData;
import com.enhan.sabina.speedy.data.local.LocalDataRepository;

import java.io.File;

public class DataRepository implements DataSource.Repository{

    private static DataRepository INSTANCE = null;
    private final AndroidData mAndroidDataSource;
    private final LocalDataRepository mLocalDataRepository;

    private DataRepository(AndroidData androidDataSource,LocalDataRepository localDataRepository) {
        mAndroidDataSource = androidDataSource;
        mLocalDataRepository = localDataRepository;
    }

    public static DataRepository getInstance(AndroidData androidDataSource, LocalDataRepository localDataRepository) {
            if (INSTANCE == null) {
                INSTANCE = new DataRepository(androidDataSource,localDataRepository);
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

    @Override
    public void storeImage(Bitmap bitmap) {
        mLocalDataRepository.storeImageForGoogle(bitmap);

    }

    @Override
    public Bitmap retrieveImageForGoogle() {
        return mLocalDataRepository.retrieveImageForGoogle();
    }

}
