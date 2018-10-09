package com.enhan.sabina.speedy.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.enhan.sabina.speedy.data.roomdb.dao.StackDao;
import com.enhan.sabina.speedy.data.roomdb.dao.WordDao;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

public class InsertStackTask extends AsyncTask<StackEntity,Void,Void> {

    private StackDao mAsyncDao;


    public InsertStackTask(StackDao dao ) {
        mAsyncDao = dao;
    }

    @Override
    protected Void doInBackground(StackEntity... stackEntities) {
        Log.d("insertask","inserting");
        mAsyncDao.insert(stackEntities[0]);
        return null;
    }
}
