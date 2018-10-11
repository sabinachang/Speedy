package com.enhan.sabina.speedy.tasks;

import android.os.AsyncTask;

import com.enhan.sabina.speedy.data.roomdb.dao.StackDao;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

public class UpdateStackTask extends AsyncTask<StackEntity,Void,Void> {

    private StackDao mAsyncDao;

    public UpdateStackTask(StackDao dao ) {
        mAsyncDao = dao;
    }

    @Override
    protected Void doInBackground(StackEntity... stackEntities) {
        mAsyncDao.updateStack(stackEntities[0]);
        return null;
    }
}
