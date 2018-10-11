package com.enhan.sabina.speedy.study;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.StudyCallback;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

public class StudyActivity extends AppCompatActivity implements StudyContract.View,StudyCallback{

    private TextView mStudyStackName;
    private TextView mViewCount;
    private StudyContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private StudyStackAdapter mAdapter;
    private ImageView mStart;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mStudyStackName = findViewById(R.id.study_stack_name);
        mViewCount = findViewById(R.id.view_count);
        mStart = findViewById(R.id.start_study);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SpeedyApplication.getAppContext(),"start studying",Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView = findViewById(R.id.study_stack_recyclerview);
        mAdapter = new StudyStackAdapter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(SpeedyApplication.getAppContext()));
        mRecyclerView.setAdapter(mAdapter);


        mPresenter = new StudyPresenter(this,mAdapter);
        mPresenter.start();

    }

    @Override
    public void setPresenter(StudyContract.Presenter presenter) {

    }

    @Override
    public void onStackSelected(StackEntity stackEntity) {
        mStudyStackName.setText(stackEntity.getStackName());
        mViewCount.setText(stackEntity.getWordCount() + " words");

    }
}
