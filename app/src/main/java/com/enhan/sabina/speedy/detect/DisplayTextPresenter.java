package com.enhan.sabina.speedy.detect;

import com.enhan.sabina.speedy.callbacks.DetectTextCallback;
import com.enhan.sabina.speedy.callbacks.ProcessTextCallback;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.tasks.DetectTextTask;
import com.enhan.sabina.speedy.utils.ProcessTextUtil;

public class DisplayTextPresenter implements DisplayTextConstract.Presenter,ProcessTextCallback{
    private DisplayTextConstract.View mDetectPhotoView;
    private DataRepository mDataRepository;

    private String mString = "The US financial regulator says Mr Musk's claims that he had secured funding to take the electric carmaker private were \"false and misleading\".\n" +
            "\n"  +
            "It is seeking to bar Mr Musk from acting as an officer or director of a publicly traded company.\n" +
            "\n" +
            "Mr Musk startled the business world last month when he took to Twitter to announce that he was considering a plan that would de-list the company from the stock exchange.\n" +
            "It is seeking to bar Mr Musk from acting as an officer or director of a publicly traded company.\n" +
            "\n" +
            "Mr " +"It is seeking to bar Mr Musk from acting as an officer or director of a publicly traded company.\n" +
            "\n" +
            "Mr" +"It is seeking to bar Mr Musk from acting as an officer or director of a publicly traded company.\n" +
            "\n" +
            "Mr " +"It is seeking to bar Mr Musk from acting as an officer or director of a publicly traded company.\n" +
            "\n";

    public DisplayTextPresenter(DisplayTextConstract.View detectPhotoView,DetectActivity activity,DataRepository dataRepository) {
        mDetectPhotoView = detectPhotoView;
        mDetectPhotoView.setPresenter(this);
        mDataRepository = dataRepository;

    }
    @Override
    public void start() {
//        mDataRepository.startTextDetection(new ProcessTextUtil(this),mDataRepository.retrieveImageForGoogle());
//        new DetectTextTask(new ProcessTextUtil(this)).execute(mDataRepository.retrieveImageForGoogle());
        mDetectPhotoView.displayDetectedText(mString);
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
