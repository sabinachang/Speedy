package com.enhan.sabina.speedy.detect;

import com.enhan.sabina.speedy.callbacks.DetectTextCallback;
import com.enhan.sabina.speedy.callbacks.ProcessTextCallback;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.tasks.DetectTextTask;
import com.enhan.sabina.speedy.utils.ProcessTextUtil;

public class DisplayTextPresenter implements DisplayTextConstract.Presenter,ProcessTextCallback{
    private DisplayTextConstract.View mDetectPhotoView;
    private DataRepository mDataRepository;

    public DisplayTextPresenter(DisplayTextConstract.View detectPhotoView,DetectActivity activity,DataRepository dataRepository) {
        mDetectPhotoView = detectPhotoView;
        mDetectPhotoView.setPresenter(this);
        mDataRepository = dataRepository;

    }
    @Override
    public void start() {
        mDataRepository.startTextDetection(new ProcessTextUtil(this),mDataRepository.retrieveImageForGoogle());
//        new DetectTextTask(new ProcessTextUtil(this)).execute(mDataRepository.retrieveImageForGoogle());
    }

    @Override
    public void processedText(String text) {
        mDetectPhotoView.displayDetectedText(text);
    }

//    @Override
//    public void onDetectSuccessful(Fire) {
//        mDetectPhotoView.displayDetectedText(result);
//    }

//    @Override
//    public void onDetectFailed() {
//
//    }
}
