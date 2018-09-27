package com.enhan.sabina.speedy.data.local;

import android.graphics.Bitmap;
import android.util.Log;

import com.enhan.sabina.speedy.data.DataSource;
import com.enhan.sabina.speedy.data.constants.AndroidData;

public class LocalDataRepository implements DataSource.Local{

    private static LocalDataRepository INSTANCE = null;
    private Bitmap mGoogleBitmap;
    private LocalDataRepository() {

    }
    public static LocalDataRepository getInstance() {
        if (INSTANCE == null) {
            return new LocalDataRepository();
        }
        return INSTANCE;
    }

    @Override
    public void storeImageForGoogle(Bitmap bitmap) {
        Log.d("LocalDataStorage","bitmap size = " + bitmap.getDensity());
        mGoogleBitmap = bitmap;
    }

    @Override
    public Bitmap retrieveImageForGoogle() {

        Log.d("LocalDataStorage","returning bitmap");
        return mGoogleBitmap;
    }
}
