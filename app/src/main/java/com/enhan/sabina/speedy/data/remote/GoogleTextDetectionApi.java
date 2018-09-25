package com.enhan.sabina.speedy.data.remote;

import android.graphics.Bitmap;

import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.DetectTextCallback;
import com.enhan.sabina.speedy.tasks.DetectTextTask;
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

    public String detectText(Bitmap bitmap) {
        final String[] detectedText = new String[1];
         new DetectTextTask(new DetectTextCallback() {
             @Override
             public void onDetectSuccessful(String result) {
                 detectedText[0] = result;
             }

             @Override
             public void onDetectFailed() {

             }
         }).execute(bitmap);

         return detectedText[0];
    }


}
