package com.enhan.sabina.speedy.detect;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.ChosenWordCallback;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.ArrayList;
import java.util.List;

class ChosenWordAdapter extends android.support.v7.widget.RecyclerView.Adapter<ChosenWordAdapter.WordViewHolder> {

    private List<WordEntity> mWordEntityList;
    private ChosenWordCallback mChosenWordCallback;

    public ChosenWordAdapter(ChosenWordCallback chosenWordCallback) {
        mWordEntityList = new ArrayList<>();
        mChosenWordCallback = chosenWordCallback;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView =  LayoutInflater.from(SpeedyApplication.getAppContext()).inflate(R.layout.word_display_item,viewGroup,false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder wordViewHolder, int i) {

        wordViewHolder.mWordTextView.setText(mWordEntityList.get(i).getWord());
        if (mWordEntityList.get(i).isSelected()) {
            wordViewHolder.mButton.setBackgroundResource(R.drawable.ic_verified);
            wordViewHolder.mWordTextView.setTextColor(SpeedyApplication.getAppContext().getColor(R.color.secondaryColorDark));
        } else {
            wordViewHolder.mButton.setBackgroundResource(R.drawable.ic_add_light);
            wordViewHolder.mWordTextView.setTextColor(SpeedyApplication.getAppContext().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        return mWordEntityList.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {
        private TextView mWordTextView;
        private ImageView mButton;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            mWordTextView = itemView.findViewById(R.id.chosen_word);
            mButton = itemView.findViewById(R.id.selected_button);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WordEntity chosenWord = mWordEntityList.get(getAdapterPosition());
                    if (! chosenWord.isSelected()) {
                        mButton.setBackgroundResource(R.drawable.ic_verified);
                        chosenWord.setSelected(true);
                        mChosenWordCallback.onSelected(chosenWord);
                        mWordTextView.setTextColor(SpeedyApplication.getAppContext().getResources().getColor(R.color.secondaryColorDark));
                    } else {
                        mButton.setBackgroundResource(R.drawable.ic_add_light);
                        chosenWord.setSelected(false);
                        mChosenWordCallback.onRemoved(chosenWord);
                        mWordTextView.setTextColor(SpeedyApplication.getAppContext().getResources().getColor(R.color.colorPrimary));
                    }
                }
            });
        }
    }

    public void addWord(WordEntity wordEntity) {
        mWordEntityList.add(wordEntity);
        notifyDataSetChanged();
    }

    public List<WordEntity> getWordEntityList() {
        return mWordEntityList;
    }
}
