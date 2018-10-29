package com.enhan.sabina.speedy.detect;

import com.enhan.sabina.speedy.callbacks.ProcessTextCallback;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.utils.ProcessTextUtil;

public class DisplayTextPresenter implements DisplayTextContract.Presenter,ProcessTextCallback{
    private DisplayTextContract.View mView;
    private DataRepository mDataRepository;
    private DetectNavigator mDetectNavigator;

    public DisplayTextPresenter(DisplayTextContract.View view) {
        mView = view;
        mDataRepository = DataRepository.getInstance();
        mDetectNavigator = DetectNavigator.getInstance();
    }
    @Override
    public void start() {
        mDataRepository.startTextDetection(new ProcessTextUtil(this),mDataRepository.retrieveImageForGoogle());
    }

    @Override
    public void processedText(String text) {
        mView.displayDetectedText(text);
    }

    @Override
    public void updateTagline(String word) {
        mDetectNavigator.updateTagline(word);
    }
}
