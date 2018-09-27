package com.enhan.sabina.speedy.detect;

import com.enhan.sabina.speedy.callbacks.DetectTextCallback;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.tasks.DetectTextTask;

public class DisplayTextPresenter implements DisplayTextConstract.Presenter,DetectTextCallback{
    private DisplayTextConstract.View mDetectPhotoView;
    private DataRepository mDataRepository;

    public DisplayTextPresenter(DisplayTextConstract.View detectPhotoView,DetectActivity activity,DataRepository dataRepository) {
        mDetectPhotoView = detectPhotoView;
        mDetectPhotoView.setPresenter(this);
        mDataRepository = dataRepository;

    }
    @Override
    public void start() {
        new DetectTextTask(this).execute(mDataRepository.retrieveImageForGoogle());
    }

    @Override
    public void onDetectSuccessful(Fire) {
        mDetectPhotoView.displayDetectedText(result);
    }

    @Override
    public void onDetectFailed() {

    }
}
