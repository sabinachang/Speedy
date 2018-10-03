package com.enhan.sabina.speedy.tasks;

import android.os.AsyncTask;

import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.data.roomdb.dao.WordDao;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

public class InsertWordTask extends AsyncTask<WordEntity,Void,Void> {

    private WordDao mAsyncDao;


    public InsertWordTask(WordDao dao ) {
        mAsyncDao = dao;
    }
    @Override
    protected Void doInBackground(WordEntity... wordEntities) {
        mAsyncDao.insert(wordEntities[0]);
        return null;
    }
}
