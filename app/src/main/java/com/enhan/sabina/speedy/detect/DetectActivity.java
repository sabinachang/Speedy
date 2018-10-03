package com.enhan.sabina.speedy.detect;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.callbacks.UpdateTaglineCallback;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.constants.AndroidData;
import com.enhan.sabina.speedy.data.local.LocalDataRepository;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

public class DetectActivity extends AppCompatActivity implements UpdateTaglineCallback{

    private DataRepository mDataRepository;
    private List<Fragment> mFragmentList;
    private TextView mWordTagline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);
        FirebaseApp.initializeApp(this);
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        mDataRepository = DataRepository.getInstance(AndroidData.getInstance(), LocalDataRepository.getInstance());
//        transToDetectPhoto();
        mFragmentList = new ArrayList<>();
        DisplayTextFragment displayTextFragment = DisplayTextFragment.newInstance();
        new DisplayTextPresenter(displayTextFragment,this,mDataRepository);
        ChosenWordFragment chosenWordFragment = ChosenWordFragment.newInstance();


        mFragmentList.add(displayTextFragment);
        mFragmentList.add(chosenWordFragment);


//        new DisplayTextPresenter(displayTextFragment,this,mDataRepository);
        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        mWordTagline = findViewById(R.id.word_tagline);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void updateTagline(String word) {
        mWordTagline.setText(word);

    }

//    private void transToDetectPhoto() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        DisplayTextFragment displayTextFragment = DisplayTextFragment.newInstance();
//        new DisplayTextPresenter(displayTextFragment,this,mDataRepository);
//        transaction.replace(R.id.detect_fragment_holder,displayTextFragment);
//        transaction.commit();
//    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            switch (i) {
                case 0:
                    return mFragmentList.get(0);
                case 1:
                    return mFragmentList.get(1);
                default:
                   return mFragmentList.get(0);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.scan_result);
                case 1:
                    return getResources().getString(R.string.new_vocab);
                default:
                    return getResources().getString(R.string.scan_result);
            }
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
