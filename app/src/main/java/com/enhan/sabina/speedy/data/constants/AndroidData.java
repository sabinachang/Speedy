package com.enhan.sabina.speedy.data.constants;

import android.content.Intent;
import android.provider.MediaStore;

import com.enhan.sabina.speedy.data.DataSource;

public class AndroidData implements DataSource.Constants{
    private static AndroidData INSTANCE = null;

    public static AndroidData getInstance() {
        if (INSTANCE == null) {
            return new AndroidData();
        }
        return INSTANCE;
    }
    @Override
    public Intent getTakePhotoIntent() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }
}
