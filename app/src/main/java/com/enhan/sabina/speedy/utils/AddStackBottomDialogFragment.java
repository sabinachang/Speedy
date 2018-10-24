package com.enhan.sabina.speedy.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.BottomSheetDialogFragmentCallback;
import com.enhan.sabina.speedy.callbacks.DetectActivityCallback;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.detect.StackItemAdapter;

public class AddStackBottomDialogFragment extends BottomSheetDialogFragment implements BottomSheetDialogFragmentCallback {

    private static AddStackBottomDialogFragment INSTANCE;
    private RecyclerView mStackListRecyclerView;
    private StackItemAdapter mStackItemAdapter;
    private ImageView mCloseBtn;
    private ImageView mAddStackBtn;
    private EditText mStackUserInput;
    private DetectActivityCallback mDetectActivityCallback;

    public AddStackBottomDialogFragment() {
    }

    public static AddStackBottomDialogFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AddStackBottomDialogFragment();
            return INSTANCE;
        }
        return INSTANCE;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetectActivityCallback) {
            mDetectActivityCallback = (DetectActivityCallback) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_stack_bottomsheet,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mCloseBtn = view.findViewById(R.id.close_btn);
        mAddStackBtn = view.findViewById(R.id.add_stack_to_recyclerview);
        mStackUserInput = view.findViewById(R.id.stack_name_add);
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDetectActivityCallback.onDialogCloseButtonClicked();
                dismiss();
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
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(SpeedyApplication.getAppContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mStackUserInput.getWindowToken(), 0);
                    mDetectActivityCallback.onAddStackButtonClicked(new StackEntity(stackName));
                }
            }
        });

        mStackListRecyclerView = view.findViewById(R.id.stack_list_recyclerview);
        mStackListRecyclerView.setLayoutManager(new LinearLayoutManager(SpeedyApplication.getAppContext()));
        mStackItemAdapter = new StackItemAdapter(this);
        mStackListRecyclerView.setAdapter(mStackItemAdapter);
        mDetectActivityCallback.addDatabaseListener(mStackItemAdapter);
        setCancelable(false);
        super.onViewCreated(view, savedInstanceState);
    }

    public StackItemAdapter getStackItemAdapter() {
        return mStackItemAdapter;
    }

    @Override
    public void onWordAddToStack(StackEntity stackEntity) {
        mDetectActivityCallback.onStackSelected(stackEntity);
        dismiss();
    }
}
