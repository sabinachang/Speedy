package com.enhan.sabina.speedy.tasks;

import android.os.AsyncTask;

import com.enhan.sabina.speedy.callbacks.ReviewWordCallback;
import com.enhan.sabina.speedy.data.roomdb.dao.WordDao;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.List;

public class GetWordsTask extends AsyncTask<String,Void,List<WordEntity>> {

    private WordDao mAsyncDao;
    private ReviewWordCallback mReviewWordCallback;

    public GetWordsTask(WordDao dao, ReviewWordCallback reviewWordCallback) {
        mAsyncDao = dao;
        mReviewWordCallback = reviewWordCallback;
    }
    @Override
    protected List<WordEntity> doInBackground(String... strings) {
        return mAsyncDao.getWordsInStack(strings[0]);

    }

    @Override
    protected void onPostExecute(List<WordEntity> wordEntityList) {
        mReviewWordCallback.returnWordsInStack(wordEntityList);
    }
}
