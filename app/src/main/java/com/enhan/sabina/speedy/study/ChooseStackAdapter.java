package com.enhan.sabina.speedy.study;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.ChooseStackCallback;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

import java.util.ArrayList;
import java.util.List;

class ChooseStackAdapter extends RecyclerView.Adapter<ChooseStackAdapter.StudyStackViewHolder> {
    private List<StackEntity> mStackEntities = new ArrayList<>();
    private ChooseStackCallback mChooseStackCallback;
    private int mPreviousPosition = -1;

    public ChooseStackAdapter(ChooseStackCallback chooseStackCallback) {
        mChooseStackCallback = chooseStackCallback;
    }

    @NonNull
    @Override
    public StudyStackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(SpeedyApplication.getAppContext()).inflate(R.layout.study_recyclerview_stack_item,viewGroup,false);
        return new StudyStackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyStackViewHolder studyStackViewHolder, int i) {
        studyStackViewHolder.mChosenStack.setText(mStackEntities.get(i).getStackName());
        if (mStackEntities.get(i).isSelected()) {
            studyStackViewHolder.mChosenStack.setTextColor(ContextCompat.getColor(SpeedyApplication.getAppContext(),R.color.secondaryColorDark));
            studyStackViewHolder.mStackStatus.setImageDrawable(ContextCompat.getDrawable(SpeedyApplication.getAppContext(),R.drawable.ic_circle));
        } else {
            studyStackViewHolder.mChosenStack.setTextColor(ContextCompat.getColor(SpeedyApplication.getAppContext(),R.color.colorPrimary));
            studyStackViewHolder.mStackStatus.setImageDrawable(ContextCompat.getDrawable(SpeedyApplication.getAppContext(),R.drawable.ic_rec));
        }
    }

    @Override
    public int getItemCount() {
        return mStackEntities.size();
    }

    public void addStacks(List<StackEntity> stackEntities) {
        mStackEntities = stackEntities;
        notifyDataSetChanged();
    }

    public class StudyStackViewHolder extends RecyclerView.ViewHolder {
        private TextView mChosenStack;
        private ImageView mStackStatus;

        public StudyStackViewHolder(@NonNull View itemView) {
            super(itemView);
            mChosenStack = itemView.findViewById(R.id.chosen_stack);
            mStackStatus = itemView.findViewById(R.id.stack_selected_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StackEntity stackEntity = mStackEntities.get(getAdapterPosition());
                    if (!stackEntity.isSelected()) {
                        stackEntity.setSelected(true);
                        mChooseStackCallback.onStackSelected(stackEntity);
                    } else {
                        stackEntity.setSelected(false);
                    }
                    if (mPreviousPosition != -1) {
                        if (mPreviousPosition != getAdapterPosition()) {
                            mStackEntities.get(mPreviousPosition).setSelected(false);
                        }
                    }
                    mPreviousPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
