package com.enhan.sabina.speedy.data;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Database;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.enhan.sabina.speedy.SpeedyApplication;
import com.enhan.sabina.speedy.callbacks.ChooseStackCallback;
import com.enhan.sabina.speedy.callbacks.DataRepositoryCallback;
import com.enhan.sabina.speedy.callbacks.DetectTextCallback;
import com.enhan.sabina.speedy.callbacks.GetDefinitionCallback;
import com.enhan.sabina.speedy.callbacks.ProcessTextCallback;
import com.enhan.sabina.speedy.callbacks.ReviewWordCallback;
import com.enhan.sabina.speedy.data.constants.AndroidData;
import com.enhan.sabina.speedy.data.local.LocalDataRepository;
import com.enhan.sabina.speedy.data.remote.GoogleTextDetectionApi;
import com.enhan.sabina.speedy.data.roomdb.AppDatabase;
import com.enhan.sabina.speedy.data.roomdb.dao.StackDao;
import com.enhan.sabina.speedy.data.roomdb.dao.WordDao;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;
import com.enhan.sabina.speedy.study.StudyPresenter;
import com.enhan.sabina.speedy.tasks.DeleteStackTask;
import com.enhan.sabina.speedy.tasks.DeleteWordTask;
import com.enhan.sabina.speedy.tasks.GetDefinitionTask;
import com.enhan.sabina.speedy.tasks.GetStackInfoTask;
import com.enhan.sabina.speedy.tasks.GetWordsTask;
import com.enhan.sabina.speedy.tasks.InsertStackTask;
import com.enhan.sabina.speedy.tasks.InsertWordTask;
import com.enhan.sabina.speedy.tasks.UpdateStackTask;
import com.enhan.sabina.speedy.tasks.UpdateWordTask;

import java.io.File;
import java.util.List;

public class DataRepository implements DataSource.Repository,DataRepositoryCallback{

    private static DataRepository INSTANCE = null;
    private AndroidData mAndroidDataSource;
    private LocalDataRepository mLocalDataRepository;
    private WordDao mWordDao;
    private StackDao mStackDao;
    private LiveData<List<StackEntity>> mAllStacks;
    private StudyPresenter mStudyPresenter;

    private DataRepository() {
        AppDatabase db = AppDatabase.getsInstance(SpeedyApplication.getAppContext());
        mAndroidDataSource = AndroidData.getInstance();
        mLocalDataRepository = LocalDataRepository.getInstance();
        mWordDao = db.wordDao();
        mStackDao = db.stackDao();
    }

    public static DataRepository getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new DataRepository();
            }
            return INSTANCE;
    }

    public void setPresenter(StudyPresenter studyPresenter){
        mStudyPresenter = studyPresenter;
    }

    @Override
    public Intent getPhotoIntent() {
        return mLocalDataRepository.retrievePhotoIntent();
    }

    @Override
    public void storePhotoIntent(Intent intent) {
        mLocalDataRepository.storePhotoIntent(intent);
    }

    @Override
    public void storePhotoUri(Uri uri) {
        mLocalDataRepository.storePhotoUri(uri);
    }

    @Override
    public Uri getPhotoUri() {
        return mLocalDataRepository.retrievePhotoUri();
    }

    @Override
    public Uri getUriFromFileProvider(Activity activity, String authority, File file) {
        return mAndroidDataSource.getUriFromFileProvider(activity,authority,file);
    }

    @Override
    public String getMediaOutputString() {
        return mAndroidDataSource.getMediaOutputString();
    }

    @Override
    public String providePictureDirectory() {
        return Environment.DIRECTORY_PICTURES;
    }

    @Override
    public void storeImage(Bitmap bitmap) {
        mLocalDataRepository.storeImageForGoogle(bitmap);
    }

    @Override
    public void startTextDetection(DetectTextCallback callback, Bitmap bitmap) {
        GoogleTextDetectionApi.getInstance().detectText(callback,bitmap);
    }

    @Override
    public void insertWord(WordEntity wordEntity) {
        new InsertWordTask(mWordDao).execute(wordEntity);
    }

    @Override
    public void updateWord(WordEntity wordEntity) {
        new UpdateWordTask(mWordDao).execute(wordEntity);
    }

    @Override
    public void deleteWord(WordEntity wordEntity) {
        new DeleteWordTask(mWordDao).execute(wordEntity);
    }

    @Override
    public void getWordsInStack(String stackName, ReviewWordCallback reviewWordCallback) {
        new GetWordsTask(mWordDao,reviewWordCallback).execute(stackName);
    }

    @Override
    public void deleteStack(StackEntity stack) {
        new DeleteStackTask(mStackDao).execute(stack);
    }

    @Override
    public void getStackInfo(String stackName, ChooseStackCallback chooseStackCallback) {
        new GetStackInfoTask(mStackDao, chooseStackCallback).execute(stackName);
    }

    @Override
    public void insertStack(StackEntity stackEntity) {
        new InsertStackTask(mStackDao).execute(stackEntity);

    }

    @Override
    public LiveData<List<StackEntity>> getAllStacks() {
//        Log.d("Datarepository","fetching stacks");
        return mStackDao.getAllStacks();
    }

    @Override
    public void updateStack(StackEntity stackEntity) {
        new UpdateStackTask(mStackDao).execute(stackEntity);
    }

    @Override
    public void getWordDefinition(String word, GetDefinitionCallback callback) {
        Log.d("datarepository","getting definition");
        new GetDefinitionTask(word,mLocalDataRepository.getDictionaryKey(),callback).getDefinition();
    }

    @Override
    public Bitmap retrieveImageForGoogle() {
        return mLocalDataRepository.retrieveImageForGoogle();
    }

    @Override
    public void onReceiveStackInfo(StackEntity stackEntity) {
    }

    @Override
    public void storeCompressedImage(Bitmap compressBitmap) {
        mLocalDataRepository.storeCompressedImageToLocal(compressBitmap);
    }

    @Override
    public Bitmap retrieveCompressedImage() {
        return mLocalDataRepository.retrieveCompressedImageFromLocal();
    }
}
