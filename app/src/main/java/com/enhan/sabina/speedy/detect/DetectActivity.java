package com.enhan.sabina.speedy.detect;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.enhan.sabina.speedy.MainActivity;
import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.DetectActivityCallback;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;
import com.enhan.sabina.speedy.utils.AddStackBottomDialogFragment;
import com.google.firebase.FirebaseApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;

public class DetectActivity extends AppCompatActivity implements DetectContract.View, DetectActivityCallback,AppBarLayout.OnOffsetChangedListener{
    private TextView mWordTagline;
    private AppBarLayout mAppBarLayout;
    private ImageView mAddButtonImageView;
    private int mMaxScrollView;
    private boolean mButtonHidden;
    private TextView mDefinitionCard;
    private TabLayout mTabLayout;
    private int mSearchComplete;
    private DetectContract.Presenter mPresenter;
    private ViewPagerAdapter mViewPagerAdapter;
    private FloatingActionButton mFab;
    private TextView mPos;
    private ViewPager mViewPager;

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

        mPresenter = new DetectPresenter(this);

        mAppBarLayout = findViewById(R.id.display_appbar);
        mAddButtonImageView = findViewById(R.id.add_word);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabButtonClicked();
            }
        });
        mFab.setClickable(false);
        mFab.setBackgroundTintList(ColorStateList.valueOf(SpeedyApplication.getAppContext().getColor(R.color.colorPrimaryLight)));
        mFab.hide();

        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabs);
        mWordTagline = findViewById(R.id.word_tagline);
        mPos = findViewById(R.id.pos);
        mDefinitionCard = findViewById(R.id.definition_preview);
        mDefinitionCard.setText(R.string.detect_page_hine);

        DisplayTextFragment displayTextFragment = DisplayTextFragment.newInstance();
        ChosenWordFragment chosenWordFragment = ChosenWordFragment.newInstance();
        mPresenter.setChosenWordCallback(chosenWordFragment);

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addToFragmentList(displayTextFragment);
        mViewPagerAdapter.addToFragmentList(chosenWordFragment);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i == 1) {
                    mFab.show();
                } else {
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
                    mAddButtonImageView.setClickable(false);
                    mDefinitionCard.setText(R.string.getting_definition);

                    mPresenter.getWordDefinition(mWordTagline.getText().toString());
                } else {
                    mSearchComplete = 0;

                    mPresenter.onAddedToChosenFragment(new WordEntity(mWordTagline.getText().toString(),mPos.getText().toString()
                            + "\n\n" + mDefinitionCard.getText().toString()));
                    mPos.setText("");
                    mDefinitionCard.setText("");
                    mWordTagline.setText("");

                    mAddButtonImageView.setBackgroundResource(R.drawable.ic_loupe);
                }
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void updateTagline(String word) {
        mSearchComplete = 0;

        mAppBarLayout.setExpanded(true,true);
        mWordTagline.setText(word);
        mPos.setText("");
        mDefinitionCard.setText("");

        mAddButtonImageView.setClickable(true);
        mAddButtonImageView.setBackgroundResource(R.drawable.ic_loupe);
    }

    private void onFabButtonClicked() {
        AddStackBottomDialogFragment.getInstance().show(getSupportFragmentManager(),"custom bottom sheet");
        mFab.hide();
    }

    @Override
    public void onDialogCloseButtonClicked() {
        mPresenter.onBottomSheetCollapsed(false,null);
        mFab.show();
    }

    @Override
    public void onStackSelected(StackEntity stackEntity) {
        Toast.makeText(SpeedyApplication.getAppContext(),"word added to " + stackEntity.getStackName(),Toast.LENGTH_SHORT).show();
        mPresenter.onBottomSheetCollapsed(true,stackEntity);
        mFab.show();
    }

    @Override
    public void updateTabCountHint(int num) {
        mViewPagerAdapter.updateTabTitle(num);
    }

    @Override
    public void activateFab() {
        mFab.setClickable(true);
        mFab.setBackgroundTintList(ColorStateList.valueOf(SpeedyApplication.getAppContext().getColor(R.color.secondaryColorDark)));
    }

    @Override
    public void deactivateFab() {
        mFab.setClickable(false);
        mFab.setBackgroundTintList(ColorStateList.valueOf(SpeedyApplication.getAppContext().getColor(R.color.colorPrimaryLight)));
    }

    @Override
    public void isWordDuplicate(boolean duplicate) {
        if (duplicate) {
            Toast.makeText(SpeedyApplication.getAppContext(),mWordTagline.getText().toString() + " has been selected",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SpeedyApplication.getAppContext(),mWordTagline.getText().toString() + " added",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAddStackButtonClicked(StackEntity stackEntity) {
        mPresenter.addStackEntityToLocalDatabase(stackEntity);
    }

    @Override
    public void addDatabaseListener(StackItemAdapter adapter) {
        mPresenter.bindListener(adapter);
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
            }
        }
        if (currentState < 10) {
            if (mButtonHidden) {
                mButtonHidden = false;
                ViewCompat.animate(mAddButtonImageView).scaleY(1).scaleX(1).start();
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
                        mPos.setText("");
                        mDefinitionCard.setText(R.string.empty_definition);

                    } else {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String pos = jsonObject.getString("partOfSpeech");
                        String definition = jsonObject.getString("text");
                        mPos.setText(pos);
                        mDefinitionCard.setText(definition);
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
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
