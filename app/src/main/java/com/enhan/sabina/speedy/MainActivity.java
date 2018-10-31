package com.enhan.sabina.speedy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.enhan.sabina.speedy.camera.CameraActivity;
import com.enhan.sabina.speedy.detect.DetectActivity;
import com.enhan.sabina.speedy.study.StudyActivity;
import com.enhan.sabina.speedy.utils.ConstantVariable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;


import io.fabric.sdk.android.Fabric;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity {
    private TextView mCameraButton;
    private TextView mReviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        mCameraButton = findViewById(R.id.camera_page);
        mReviewButton = findViewById(R.id.preview_page);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.secondaryColorDark));
        checkPermissionStatus();
    }

    private void checkPermissionStatus() {
        if (! hasPermission(SpeedyApplication.getAppContext(), ConstantVariable.PERMISSIONS)) {
            ActivityCompat.requestPermissions(MainActivity.this,
                        ConstantVariable.PERMISSIONS,ConstantVariable.PERMISSION_ALL_REQUEST_CODE);
        } else {
            mCameraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    transToTakePhoto();
                }
            });
            mReviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    transToReview();
                }
            });
        }
    }

    private void transToReview() {
        Intent reviewIntent = new Intent(this,StudyActivity.class);
        startActivity(reviewIntent);
    }

    private void transToTakePhoto() {
        Intent takePhotoIntent = new Intent(this,CameraActivity.class);
        startActivity(takePhotoIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ConstantVariable.PERMISSION_ALL_REQUEST_CODE) {
            if (grantResults.length > 0 ) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    mCameraButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            transToTakePhoto();
                        }
                    });

                    mReviewButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            transToReview();
                        }
                    });
                }
            }
        }
    }

    public boolean hasPermission(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        finish();
        super.onBackPressed();
    }
}
