package com.enhan.sabina.speedy.tasks;

import android.os.AsyncTask;

import com.enhan.sabina.speedy.data.roomdb.dao.StackDao;
import com.enhan.sabina.speedy.data.roomdb.dao.WordDao;

public class GetStackInfoTask extends AsyncTask<String,Void,Void> {

    private StackDao mAsyncDao;


    public GetStackInfoTask(StackDao dao ) {
        mAsyncDao = dao;
    }
    @Override
    protected Void doInBackground(String... strings) {
        mAsyncDao.getStackInfo(strings[0]);
        return null;
    }
}
