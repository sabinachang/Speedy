package com.enhan.sabina.speedy.detect;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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

import com.crashlytics.android.Crashlytics;
import com.enhan.sabina.speedy.MainActivity;
import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.ChosenWordCallback;
import com.enhan.sabina.speedy.callbacks.ControlBottomSheetCallback;
import com.enhan.sabina.speedy.callbacks.GetDefinitionCallback;
import com.enhan.sabina.speedy.callbacks.UpdateTaglineCallback;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;
import com.google.firebase.FirebaseApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;
import java.util.List;

public class DetectActivity extends AppCompatActivity implements DetectContract.View,UpdateTaglineCallback, ControlBottomSheetCallback,AppBarLayout.OnOffsetChangedListener,GetDefinitionCallback{

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
    private TabLayout mTabLayout;
    private int mSearchComplete;
    private DetectContract.Presenter mPresenter;
    private ViewPagerAdapter mViewPagerAdapter;
    private FloatingActionButton mFab;
    private TextView mPos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_detect);
        FirebaseApp.initializeApp(this);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.secondaryColorDark));
//        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
//            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
//
//        if (Build.VERSION.SDK_INT >= 21) {
//            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        mDataRepository = DataRepository.getInstance();
//        transToDetectPhoto();
        mFragmentList = new ArrayList<>();
        DisplayTextFragment displayTextFragment = DisplayTextFragment.newInstance();
//        new DisplayTextPresenter(displayTextFragment,this,mDataRepository);
        ChosenWordFragment chosenWordFragment = ChosenWordFragment.newInstance();
//        new ChosenWordPresenter(chosenWordFragment);
        mChosenWordCallback = chosenWordFragment;


        mAppBarLayout = findViewById(R.id.display_appbar);
        mAddButtonImageView = findViewById(R.id.add_word);
        mCloseBtn = findViewById(R.id.close_btn);
        mAddStackBtn = findViewById(R.id.add_stack_to_recyclerview);
        mStackUserInput = findViewById(R.id.stack_name_add);
        mDefinitionCard = findViewById(R.id.definition_preview);
        mFab = findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabButtonClicked();
            }
        });
//        mFab.setClickable(false);
        mFab.setBackgroundTintList(ColorStateList.valueOf(SpeedyApplication.getAppContext().getColor(R.color.colorPrimaryLight)));
        mFab.hide();

        mFragmentList.add(displayTextFragment);
        mFragmentList.add(chosenWordFragment);


//        new DisplayTextPresenter(displayTextFragment,this,mDataRepository);
        ViewPager viewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabs);
        mWordTagline = findViewById(R.id.word_tagline);
        mDefinitionCard.setText(R.string.detect_page_hine);
//        mPos = findViewById(R.id.pos);

//        mWordTagline.setTitle("");
//        setSupportActionBar(mWordTagline);

//        mTabLayout.getTabAt(0).setText(SpeedyApplication.getAppContext().getString(R.string.scan_result));
//        mTabLayout.getTabAt(1).setText(SpeedyApplication.getAppContext().getString(R.string.word_found));

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 1) {
//                    mAppBarLayout.setExpanded(false);
                    mFab.show();
                } else {
//                    mAppBarLayout.setExpanded(true);
                    mFab.hide();
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
                    mDataRepository.getWordDefinition(mWordTagline.getText().toString(),DetectActivity.this);
                    mAddButtonImageView.setClickable(false);
//                    mAppBarLayout.setExpanded(true);
                    mDefinitionCard.setText(R.string.getting_definition);

                } else {

                    Toast.makeText(SpeedyApplication.getAppContext(),"add to list",Toast.LENGTH_SHORT).show();
//                    mAppBarLayout.setExpanded(true);
                    mChosenWordCallback.onAddedToChosenFragment(new WordEntity(mWordTagline.getText().toString(),mDefinitionCard.getText().toString()));
                    mDefinitionCard.setText("");
                    mWordTagline.setText("");
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
                    mPresenter.addStackEntityToLocalDatabase(new StackEntity(stackName));
//                    mStackItemAdapter.addStackName(new StackEntity(stackName));
                }
            }
        });


        mStackListRecyclerView = findViewById(R.id.stack_list_recyclerview);
        mStackListRecyclerView.setLayoutManager(new LinearLayoutManager(SpeedyApplication.getAppContext()));
        mStackItemAdapter = new StackItemAdapter(this);
        mStackListRecyclerView.setAdapter(mStackItemAdapter);


        mAppBarLayout.addOnOffsetChangedListener(this);
        initStackList();

        mTabLayout.setupWithViewPager(viewPager);


    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter = new DetectPresenter(this,mStackItemAdapter);
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



    }

    @Override
    public void updateTagline(String word) {
        mAppBarLayout.setExpanded(true,true);
        mWordTagline.setText(word);
        mDefinitionCard.setText("");
        mSearchComplete = 0;
        mAddButtonImageView.setBackgroundResource(R.drawable.ic_loupe);
    }

    @Override
    public void onFabButtonClicked() {
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mFab.hide();
    }

    @Override
    public void onDialogCloseButtonClicked() {
        mChosenWordCallback.onBottomSheetCollapsed(false,null);
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mFab.show();
    }

    @Override
    public void onStackSelected(StackEntity stackEntity) {
        Toast.makeText(SpeedyApplication.getAppContext(),"word added to " + stackEntity.getStackName(),Toast.LENGTH_SHORT).show();


        mChosenWordCallback.onBottomSheetCollapsed(true,stackEntity);
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mFab.show();
    }

    @Override
    public void updateTabCountHint(int num) {
        mViewPagerAdapter.tabTitles[1] = "Words (" + num + ")";
        mViewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void activateFab() {

//        Log.d("detect","in activate");

//        mFab.setImageDrawable(this.getDrawable(R.drawable.ic_file_selected));
        mFab.setBackgroundTintList(ColorStateList.valueOf(SpeedyApplication.getAppContext().getColor(R.color.secondaryColorDark)));


//        mFab.setClickable(true);
    }

    @Override
    public void deactivateFab() {
//        Log.d("detect","de activate");
//        mFab.setImageDrawable(this.getDrawable(R.drawable.ic_file_unselected));

        mFab.setBackgroundTintList(ColorStateList.valueOf(SpeedyApplication.getAppContext().getColor(R.color.colorPrimaryLight)));
//        mFab.setClickable(false);
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

    @Override
    public void setPresenter(DetectContract.Presenter presenter) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unbindListener();
    }

    @Override
    public void onDefinitionGotten(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        mDefinitionCard.setText("");

                    } else {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String pos = jsonObject.getString("partOfSpeech");
                        String definition = jsonObject.getString("text");

                        mDefinitionCard.setText(pos + "\n" +definition);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mSearchComplete = 1;
                mAddButtonImageView.setClickable(true);
            }
        });

    }

    @Override
    public void onFail() {

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


        private String tabTitles[] = new String[] {"Result","Words"};

        private ViewPagerAdapter(FragmentManager fm) {
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
            return tabTitles[position];
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
