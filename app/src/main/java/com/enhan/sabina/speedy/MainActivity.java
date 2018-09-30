package com.enhan.sabina.speedy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.enhan.sabina.speedy.camera.CameraActivity;
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

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGreetingTextView = findViewById(R.id.greetings);

        checkPermissionStatus();
    }

    private void checkPermissionStatus() {
        if (! hasPermission(SpeedyApplication.getAppContext(), ConstantVariable.PERMISSIONS)) {
            ActivityCompat.requestPermissions(MainActivity.this,
                        ConstantVariable.PERMISSIONS,ConstantVariable.PERMISSION_ALL_REQUEST_CODE);

        } else {
//            transToTakePhoto();

            determineTime();
        }
    }

    private void determineTime() {

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            mGreetingTextView.setText(R.string.morning_greeting);
        }else if(timeOfDay >= 12 && timeOfDay < 16){
           mGreetingTextView.setText(R.string.afternoon_greeting);
        }else if(timeOfDay >= 16 && timeOfDay < 24) {
            mGreetingTextView.setText(R.string.evening_greeting);
        }
//        else if(timeOfDay >= 21 && timeOfDay < 24){
//            Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
//        }
    }

    private void transToTakePhoto() {

        Intent takePhotoIntent = new Intent(this,CameraActivity.class);
        startActivity(takePhotoIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ConstantVariable.PERMISSION_ALL_REQUEST_CODE: {
                if (grantResults.length > 0 ) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
//                        transToTakePhoto();
                        determineTime();
                    }

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


}
