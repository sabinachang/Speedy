package com.enhan.sabina.speedy.camera;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.enhan.sabina.speedy.SpeedyApplication;

import java.io.IOException;
import java.util.List;

public class CameraPreview extends SurfaceView{
    private final double ASPECT_RATIO = 3.0 / 4.0;

    public CameraPreview(Context context) {
        super(context);

    }

}
