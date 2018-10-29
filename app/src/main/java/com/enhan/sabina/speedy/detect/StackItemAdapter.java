package com.enhan.sabina.speedy.detect;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.BottomSheetDialogFragmentCallback;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

import java.util.ArrayList;
import java.util.List;

public class StackItemAdapter extends RecyclerView.Adapter<StackItemAdapter.StackViewHolder> {
    private List<StackEntity> mStackEntities;
    private BottomSheetDialogFragmentCallback mBottomSheetCallback;

    public StackItemAdapter(BottomSheetDialogFragmentCallback callback) {
        mBottomSheetCallback = callback;
        mStackEntities = new ArrayList<>();
    }

    @NonNull
    @Override
    public StackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(SpeedyApplication.getAppContext()).inflate(R.layout.stack_recyclerview_item,viewGroup,false);
        return new StackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StackViewHolder stackViewHolder, int i) {
        stackViewHolder.mStackName.setText(mStackEntities.get(i).getStackName());
    }

    @Override
    public int getItemCount() {
        return mStackEntities.size();
    }

    public class StackViewHolder extends RecyclerView.ViewHolder {
        private TextView mStackName;
        private RelativeLayout mStackItem;

        public StackViewHolder(@NonNull View itemView) {
            super(itemView);
            mStackName = itemView.findViewById(R.id.stack_name);
            mStackItem = itemView.findViewById(R.id.choose_stack_item);
            mStackItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBottomSheetCallback.onWordAddToStack(mStackEntities.get(getAdapterPosition()));
                }
            });
        }
    }

    public void addStackNames(List<StackEntity> stackEntities) {
        mStackEntities = stackEntities;
        notifyDataSetChanged();

    }

}
