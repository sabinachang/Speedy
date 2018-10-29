package com.enhan.sabina.speedy.detect;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;
import com.enhan.sabina.speedy.callbacks.ChosenWordCallback;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

public interface DetectContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void addStackEntityToLocalDatabase(StackEntity stackEntity);

        void unbindListener();

        void bindListener(StackItemAdapter adapter);

        void getWordDefinition(String word);

        void setChosenWordCallback(ChosenWordCallback callback);

        void onAddedToChosenFragment(WordEntity wordEntity);

        void onBottomSheetCollapsed(boolean added,StackEntity stackEntity);

    }

    interface Navigator {
        void onDialogCloseButtonClickedNavigator();

        void onStackSelectedNavigator(StackEntity stackEntity);

        void updateTabCountHintNavigator(int num);

        void activateFabNavigator();

        void deactivateFabNavigator();

        void isWordDuplicateNavigator(boolean duplicate);

        void onAddStackButtonClickedNavigator(StackEntity stackEntity);

        void addDatabaseListenerNavigator(StackItemAdapter adapter);

        void updateTagline(String word);
    }
}
