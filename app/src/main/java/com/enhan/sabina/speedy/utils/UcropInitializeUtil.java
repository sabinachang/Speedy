package com.enhan.sabina.speedy.utils;

import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.yalantis.ucrop.UCrop;

public class UcropInitializeUtil {

    public static UCrop.Options initializeOptions(){
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(SpeedyApplication.getAppContext(), R.color.secondaryColorDark));
        options.setStatusBarColor(ContextCompat.getColor(SpeedyApplication.getAppContext(),R.color.secondaryColorDark));
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setToolbarTitle("");
        options.setRootViewBackgroundColor(ContextCompat.getColor(SpeedyApplication.getAppContext(),R.color.primaryColor));
        options.setCropFrameColor(ContextCompat.getColor(SpeedyApplication.getAppContext(),R.color.colorAccent));
        options.setToolbarWidgetColor(ContextCompat.getColor(SpeedyApplication.getAppContext(),R.color.colorAccent));
        options.setCompressionQuality(100);
        options.setFreeStyleCropEnabled(true);
        options.setActiveWidgetColor(ContextCompat.getColor(SpeedyApplication.getAppContext(),R.color.secondaryColorDark));
        options.setShowCropGrid(true);
        return options;
    }
}
