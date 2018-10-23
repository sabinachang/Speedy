package com.enhan.sabina.speedy.study;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.ChooseStackCallback;
import com.enhan.sabina.speedy.callbacks.StudyCallback;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

public class ChooseStackFragment extends Fragment implements ChooseStackContract.View,ChooseStackCallback {


    private TextView mStudyStackName;
    private TextView mViewCount;
    private RecyclerView mRecyclerView;
    private ChooseStackAdapter mAdapter;
    private ImageView mStart;
    private StudyCallback mStudyCallback;
    private ChooseStackContract.Presenter mPresenter;

    public ChooseStackFragment() {

    }

    public static ChooseStackFragment newInstance() {
        return new ChooseStackFragment();
    }

    @Override
    public void setPresenter(ChooseStackContract.Presenter presenter) {
//        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_stack,container,false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StudyActivity) {
            mStudyCallback = (StudyCallback) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mStudyStackName = view.findViewById(R.id.study_stack_name);
//        mViewCount = view.findViewById(R.id.view_count);


        mStart = view.findViewById(R.id.start_study);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SpeedyApplication.getAppContext(),"start studying",Toast.LENGTH_SHORT).show();

//                transToReviewWords();

                mStudyCallback.transToReviewWords(mStudyStackName.getText().toString());
            }
        });

        mRecyclerView = view.findViewById(R.id.study_stack_recyclerview);
        mAdapter = new ChooseStackAdapter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(SpeedyApplication.getAppContext()));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter = new ChooseStackPresenter(this);
        mPresenter.addAdapter(mAdapter);
        mPresenter.start();
    }

    @Override
    public void returnStackEntity(StackEntity stackEntity) {
        mPresenter.returnStackName(stackEntity);
    }



    @Override
    public void onStackSelected(StackEntity stackEntity) {
        mStudyStackName.setText(stackEntity.getStackName());
//        mViewCount.setText(stackEntity.getWordCount() + " words");

    }



}
