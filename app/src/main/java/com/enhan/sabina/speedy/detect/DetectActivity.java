package com.enhan.sabina.speedy.detect;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.ChosenWordCallback;
import com.enhan.sabina.speedy.callbacks.ControlBottomSheetCallback;
import com.enhan.sabina.speedy.callbacks.UpdateTaglineCallback;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

public class DetectActivity extends AppCompatActivity implements UpdateTaglineCallback, ControlBottomSheetCallback,AppBarLayout.OnOffsetChangedListener{

    private DataRepository mDataRepository;
    private String mFakeString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
    private List<Fragment> mFragmentList;
    private TextView mWordTagline;
    private AppBarLayout mAppBarLayout;
    private BottomSheetBehavior mBehavior;
    private RecyclerView mStackListRecyclerView;
    private List<StackEntity> mStackEntityList = new ArrayList<>();
    private StackItemAdapter mStackItemAdapter;
    private ImageView mAddButtonImageView;
    private int mMaxScrollView;
    private boolean mButtonHidden;
    private ImageView mCloseBtn;
    private ImageView mAddStackBtn;
    private EditText mStackUserInput;
    private TextView mDefinitionCard;
    private ChosenWordCallback mChosenWordCallback;
    private int mSearchComplete;


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
        mDataRepository = DataRepository.getInstance();
//        transToDetectPhoto();
        mFragmentList = new ArrayList<>();
        DisplayTextFragment displayTextFragment = DisplayTextFragment.newInstance();
        new DisplayTextPresenter(displayTextFragment,this,mDataRepository);
        ChosenWordFragment chosenWordFragment = ChosenWordFragment.newInstance();
        mChosenWordCallback = chosenWordFragment;

        mAppBarLayout = findViewById(R.id.display_appbar);
        mAddButtonImageView = findViewById(R.id.add_word);
        mCloseBtn = findViewById(R.id.close_btn);
        mAddStackBtn = findViewById(R.id.add_stack_to_recyclerview);
        mStackUserInput = findViewById(R.id.stack_name_add);
        mDefinitionCard = findViewById(R.id.definition_preview);

        mFragmentList.add(displayTextFragment);
        mFragmentList.add(chosenWordFragment);


//        new DisplayTextPresenter(displayTextFragment,this,mDataRepository);
        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        mWordTagline = findViewById(R.id.word_tagline);
//        mWordTagline.setTitle("");
//        setSupportActionBar(mWordTagline);


        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 1) {
                    mAppBarLayout.setExpanded(false);
                } else {
                    mAppBarLayout.setExpanded(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mAddButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSearchComplete == 0) {
                    mAddButtonImageView.setBackgroundResource(R.drawable.ic_add_flashcard_preview);
                    mDefinitionCard.setText(mFakeString);
                    mSearchComplete = 1;
                } else {
                    mDefinitionCard.setText("");
                    Toast.makeText(SpeedyApplication.getAppContext(),"add to list",Toast.LENGTH_SHORT).show();

                    mChosenWordCallback.onNewWordAdded(new WordEntity(mWordTagline.getText().toString(),mDefinitionCard.getText().toString()));
                    mAddButtonImageView.setBackgroundResource(R.drawable.ic_loupe);
                    mSearchComplete = 0;
                }


            }
        });

        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottomSheet);

        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onDialogCloseButtonClicked();
            }
        });

        mAddStackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stackName = mStackUserInput.getText().toString();
                if (stackName.isEmpty()) {
                    Toast.makeText(SpeedyApplication.getAppContext(),"Enter a name",Toast.LENGTH_SHORT).show();
                } else {
                    mStackUserInput.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(SpeedyApplication.getAppContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mStackUserInput.getWindowToken(), 0);
                    mStackItemAdapter.addStackName(new StackEntity(stackName));
                }
            }
        });


        mStackListRecyclerView = findViewById(R.id.stack_list_recyclerview);
        mStackListRecyclerView.setLayoutManager(new LinearLayoutManager(SpeedyApplication.getAppContext()));
        mAppBarLayout.addOnOffsetChangedListener(this);
        initStackList();

        tabLayout.setupWithViewPager(viewPager);


    }

    private void initStackList() {
//
//        StackEntity stackEntity = new StackEntity("first Stack");
//        mStackEntityList.add(stackEntity);
//
//        stackEntity = new StackEntity("Second Stack");
//        mStackEntityList.add(stackEntity);
//
//        stackEntity = new StackEntity("Third Stack");
//        mStackEntityList.add(stackEntity);
//
//        stackEntity = new StackEntity("Fourth Stack");
//        mStackEntityList.add(stackEntity);

        mStackItemAdapter = new StackItemAdapter(mStackEntityList,this);
        mStackListRecyclerView.setAdapter(mStackItemAdapter);

    }

    @Override
    public void updateTagline(String word) {
        Log.d("detect","update");
//        mWordTagline.se("");

        mWordTagline.setText(word);
        mDefinitionCard.setText("");
        mSearchComplete = 0;
        mAddButtonImageView.setBackgroundResource(R.drawable.ic_loupe);
    }

    @Override
    public void onFabButtonClicked(List<WordEntity> wordEntityList) {


        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onDialogCloseButtonClicked() {
        mChosenWordCallback.onBottomSheetCollapsed(false);
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


    }

    @Override
    public void onStackSelected(String stackName) {
        Toast.makeText(SpeedyApplication.getAppContext(),"word added to " + stackName,Toast.LENGTH_SHORT).show();
        mChosenWordCallback.onBottomSheetCollapsed(true);
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollView == 0) {
            mMaxScrollView = mAppBarLayout.getTotalScrollRange();

        }

        int currentState = (Math.abs(i)) * 100 / mMaxScrollView;

        if (currentState >= 10) {

            if (!mButtonHidden) {
                mButtonHidden = true;

                ViewCompat.animate(mAddButtonImageView).scaleY(0).scaleX(0).start();
//                mWordTagline.setTextSize(10);
//                LinearLayout.LayoutParams params =  (LinearLayout.LayoutParams) mWordTagline.getLayoutParams();
//                params.setMargins(params.leftMargin - 20, params.topMargin, params.rightMargin, params.bottomMargin - 20); // left, top, right, bottom
//                mWordTagline.setLayoutParams(params);
//                mWord

//                AnimatorSet animSetXY = new AnimatorSet();
//
//                ObjectAnimator y = ObjectAnimator.ofFloat(mWordTagline,
//                        "translationY",mWordTagline.getY(), mWordTagline.getY() - 300);
//
//                ObjectAnimator x = ObjectAnimator.ofFloat(mWordTagline,
//                        "translationX", mWordTagline.getX(), mWordTagline.getX() + 300);
//
//                animSetXY.playTogether(x, y);
//                animSetXY.setInterpolator(new LinearInterpolator());
//                animSetXY.setDuration(300);
//                animSetXY.start();
            }
        }

        if (currentState < 10) {
            if (mButtonHidden) {
                mButtonHidden = false;
                ViewCompat.animate(mAddButtonImageView).scaleY(1).scaleX(1).start();

//                Path path = new Path();
//                path.arcTo((float)mWordTagline.getLeft(), (float)mWordTagline.getTop(), 1000f, 1000f, 270f, -180f, true);
//                ObjectAnimator animator = ObjectAnimator.ofFloat(mWordTagline, View.X, View.Y, path);
//                LinearLayout.LayoutParams params =  (LinearLayout.LayoutParams) mWordTagline.getLayoutParams();
//                params.setMargins(params.leftMargin + 20, params.topMargin, params.rightMargin, params.bottomMargin + 20); // left, top, right, bottom
//                mWordTagline.setLayoutParams(params);
//                mWordTagline.setTextSize(30);
//                mWordTagline.setPadding(0,0,0,20);\

//                Path path = new Path();
//                path.arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true);
//                ObjectAnimator animator = ObjectAnimator.ofFloat(mWordTagline, View.X, View.Y, path);
//                animator.setDuration(2000);
//                animator.start();

//
//                AnimatorSet animSetXY = new AnimatorSet();
//
//                ObjectAnimator y = ObjectAnimator.ofFloat(mWordTagline,
//                        "translationY",mWordTagline.getY(), mWordTagline.getY() + 300);
//
//                ObjectAnimator x = ObjectAnimator.ofFloat(mWordTagline,
//                        "translationX", mWordTagline.getX(), mWordTagline.getX() - 300);
//
//                animSetXY.playTogether(x, y);
//                animSetXY.setInterpolator(new LinearInterpolator());
//                animSetXY.setDuration(300);
//                animSetXY.start();
            }
        }

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
