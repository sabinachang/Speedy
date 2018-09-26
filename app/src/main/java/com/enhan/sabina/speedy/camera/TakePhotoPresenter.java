package com.enhan.sabina.speedy.camera;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.enhan.sabina.speedy.callbacks.TakePhotoCallback;
import com.enhan.sabina.speedy.data.DataRepository;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePhotoPresenter implements TakePhotoContract.Presenter{
    private final TakePhotoContract.View mTakePhotoView;
    private final DataRepository mDataRepository;

    public TakePhotoPresenter(TakePhotoContract.View takePhotoView, DataRepository dataRepository) {
        mTakePhotoView = takePhotoView;
        mTakePhotoView.setPresenter(this);
        mDataRepository = dataRepository;
    }

    @Override
    public void start() {

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mTakePhotoView.provideActivity().getExternalFilesDir(mTakePhotoView.providePictureDirectory());
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                mPhotoUri = FileProvider.getUriForFile(getActivity(),
                        "com.enhan.sabina.speedy.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void takePhoto() {
        createImageFile();
    }

    @Override
    public void launchCamera() {

    }
}
