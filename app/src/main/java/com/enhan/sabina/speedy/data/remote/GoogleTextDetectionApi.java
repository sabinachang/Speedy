package com.enhan.sabina.speedy.data.remote;

import android.graphics.Bitmap;

import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.DetectTextCallback;
import com.enhan.sabina.speedy.callbacks.ProcessTextCallback;
import com.enhan.sabina.speedy.tasks.DetectTextTask;
import com.enhan.sabina.speedy.utils.ProcessTextUtil;
import com.google.firebase.FirebaseApp;

public class GoogleTextDetectionApi {

    private static GoogleTextDetectionApi INSTANCE;

    private GoogleTextDetectionApi() {
        FirebaseApp.initializeApp(SpeedyApplication.getAppContext());
    }

    public static GoogleTextDetectionApi getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GoogleTextDetectionApi();
        }
        return INSTANCE;
    }

    public void detectText(DetectTextCallback callback,Bitmap bitmap) {
        new DetectTextTask(callback).execute(bitmap);
    }



}
