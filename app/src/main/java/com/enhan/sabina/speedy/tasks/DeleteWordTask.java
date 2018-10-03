package com.enhan.sabina.speedy.tasks;

import android.os.AsyncTask;

import com.enhan.sabina.speedy.data.roomdb.dao.WordDao;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

public class DeleteWordTask extends AsyncTask<WordEntity,Void,Void> {

    private WordDao mAsyncDao;

    public DeleteWordTask(WordDao dao) {
        mAsyncDao = dao;
    }
    @Override
    protected Void doInBackground(WordEntity... wordEntities) {
        mAsyncDao.deleteWord(wordEntities[0].getId());
        return null;
    }
}
