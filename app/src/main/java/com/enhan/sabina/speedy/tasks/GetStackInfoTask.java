package com.enhan.sabina.speedy.tasks;

import android.os.AsyncTask;

import com.enhan.sabina.speedy.callbacks.ChooseStackCallback;
import com.enhan.sabina.speedy.data.roomdb.dao.StackDao;
import com.enhan.sabina.speedy.data.roomdb.dao.WordDao;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

public class GetStackInfoTask extends AsyncTask<String,Void,StackEntity> {

    private StackDao mAsyncDao;
    private ChooseStackCallback mChooseStackCallback;


    public GetStackInfoTask(StackDao dao,ChooseStackCallback callback ) {
        mAsyncDao = dao;
        mChooseStackCallback = callback;
    }
    @Override
    protected StackEntity doInBackground(String... strings) {
        return mAsyncDao.getStackInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(StackEntity stackEntity) {
        mChooseStackCallback.returnStackEntity(stackEntity);
    }
}
