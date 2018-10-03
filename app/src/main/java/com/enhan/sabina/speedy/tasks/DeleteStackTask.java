package com.enhan.sabina.speedy.tasks;

import android.os.AsyncTask;

import com.enhan.sabina.speedy.data.roomdb.dao.StackDao;
import com.enhan.sabina.speedy.data.roomdb.dao.WordDao;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

public class DeleteStackTask extends AsyncTask<StackEntity,Void,Void> {
    private StackDao mAsyncDao;


    public DeleteStackTask(StackDao dao ) {
        mAsyncDao = dao;
    }

    @Override
    protected Void doInBackground(StackEntity... stackEntities) {
        mAsyncDao.deleteStack(stackEntities[0].getId());
        return null;
    }
}
