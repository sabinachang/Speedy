package com.enhan.sabina.speedy.data;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.enhan.sabina.speedy.callbacks.ChooseStackCallback;
import com.enhan.sabina.speedy.callbacks.DetectTextCallback;
import com.enhan.sabina.speedy.callbacks.ProcessTextCallback;
import com.enhan.sabina.speedy.callbacks.ReviewWordCallback;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.io.File;
import java.util.List;

public interface DataSource {
    interface Constants {
        Intent getTakePhotoIntent();
        Uri getUriFromFileProvider(Activity activity, String authority, File file);
        String getMediaOutputString();
        String providePictureDirectory();
    }

    interface Repository {
        Intent getPhotoIntent();
        Uri getUriFromFileProvider(Activity activity, String authority, File file);
        String getMediaOutputString();
        String providePictureDirectory();
        void storeImage(Bitmap bitmap);
        Bitmap retrieveImageForGoogle();
        void startTextDetection(DetectTextCallback callback, Bitmap bitmap);

        // DB operations
        void insertWord(WordEntity wordEntity);
        void updateWord(WordEntity wordEntity);
        void deleteWord(WordEntity wordEntity);
        void getWordsInStack(String stackName, ReviewWordCallback reviewWordCallback);
        void deleteStack(StackEntity stackEntity);
        void getStackInfo(String stackName, ChooseStackCallback chooseStackCallback);
        void insertStack(StackEntity stackEntity);
        LiveData<List<StackEntity>> getAllStacks();
        void updateStack(StackEntity stackEntity);

    }

    interface Local {
        void storeImageForGoogle(Bitmap bitmap);
        Bitmap retrieveImageForGoogle();
    }
}
