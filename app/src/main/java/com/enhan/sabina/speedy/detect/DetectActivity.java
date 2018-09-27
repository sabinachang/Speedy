package com.enhan.sabina.speedy.detect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.constants.AndroidData;
import com.enhan.sabina.speedy.data.local.LocalDataRepository;
import com.google.firebase.FirebaseApp;

public class DetectActivity extends AppCompatActivity{

    private DataRepository mDataRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);
        FirebaseApp.initializeApp(this);
        mDataRepository = DataRepository.getInstance(AndroidData.getInstance(), LocalDataRepository.getInstance());
        transToDetectPhoto();
    }

    private void transToDetectPhoto() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        DisplayTextFragment displayTextFragment = DisplayTextFragment.newInstance();
        new DisplayTextPresenter(displayTextFragment,this,mDataRepository);
        transaction.replace(R.id.detect_fragment_holder,displayTextFragment);
        transaction.commit();
    }
}
