package com.enhan.sabina.speedy.study;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.List;

public class ReviewWordAdapter extends RecyclerView.Adapter<ReviewWordAdapter.WordViewHolder> {

    private List<WordEntity> mWordEntityList;

    public ReviewWordAdapter(List<WordEntity>wordEntityList) {
        Log.d("recyclerview","init");
        mWordEntityList = wordEntityList;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(SpeedyApplication.getAppContext()).inflate(R.layout.review_recyclerview_word_item,viewGroup,false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder wordViewHolder, int i) {

        wordViewHolder.mWord.setText(mWordEntityList.get(i).getWord());
        wordViewHolder.mDefinition.setText(mWordEntityList.get(i).getDefinition());
        if (mWordEntityList.get(i).isDefinitionShown()) {
            wordViewHolder.mFlip.setBackground(ContextCompat.getDrawable(SpeedyApplication.getAppContext(),R.drawable.ic_definition_on));
        } else {
            wordViewHolder.mFlip.setBackground(ContextCompat.getDrawable(SpeedyApplication.getAppContext(),R.drawable.ic_definition_off));
        }
    }

    @Override
    public int getItemCount() {
        return mWordEntityList.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder{

        private TextView mWord;
        private TextView mDefinition;
        private Button mFlip;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            mWord = itemView.findViewById(R.id.flashcard_word);
            mDefinition = itemView.findViewById(R.id.flashcard_definition);
            mFlip = itemView.findViewById(R.id.flip);

            mDefinition.setVisibility(View.INVISIBLE);

            mFlip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!mWordEntityList.get(getAdapterPosition()).isDefinitionShown()) {
                        mFlip.setBackground(ContextCompat.getDrawable(SpeedyApplication.getAppContext(),R.drawable.ic_definition_on));
                        mDefinition.setVisibility(View.VISIBLE);
                        mWordEntityList.get(getAdapterPosition()).setDefinitionShown(true);
                    } else {
                        mFlip.setBackground(ContextCompat.getDrawable(SpeedyApplication.getAppContext(),R.drawable.ic_definition_off));
                        mDefinition.setVisibility(View.INVISIBLE);
                        mWordEntityList.get(getAdapterPosition()).setDefinitionShown(false);
                    }
//                    mDefinition.setVisibility(View.VISIBLE);

                }
            });
        }
    }
}
