package com.enhan.sabina.speedy.data;

import com.enhan.sabina.speedy.data.constants.AndroidData;

public class DataRepository{

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

}
