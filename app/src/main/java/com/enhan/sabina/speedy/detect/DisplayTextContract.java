package com.enhan.sabina.speedy.detect;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;

public interface DisplayTextContract {
    interface View extends BaseView<Presenter> {
        void displayDetectedText(String detectedText);
    }

    interface Presenter extends BasePresenter {
        void updateTagline(String word);
    }
}
