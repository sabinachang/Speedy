package com.enhan.sabina.speedy.detect;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

public interface DetectContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void addStackEntityToLocalDatabase(StackEntity stackEntity);

        void unbindListener();

        void bindListener(StackItemAdapter adapter);

        void getWordDefinition();
    }

    interface Navigator {
        void onFabButtonClickedNavigator(); // detect activity

        void onDialogCloseButtonClickedNavigator(); // dialog fragment

        void onStackSelectedNavigator(StackEntity stackEntity); // dialog fragment

        void updateTabCountHintNavigator(int num); // chosen word fragment

        void activateFabNavigator(); // chosen word fragment

        void deactivateFabNavigator(); //  chosen word fragment

        void isWordDuplicateNavigator(boolean duplicate); // chosen word fragment

        void onAddStackButtonClickedNavigator(StackEntity stackEntity); // dialog fragment

        void addDatabaseListenerNavigator(StackItemAdapter adapter); // dialog fragment
    }
}
