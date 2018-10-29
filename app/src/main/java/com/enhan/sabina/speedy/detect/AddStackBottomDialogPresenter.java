package com.enhan.sabina.speedy.detect;

import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.utils.AddStackBottomDialogFragment;

public class AddStackBottomDialogPresenter implements AddStackBottomDialogContract.Presenter {

    private DetectNavigator mDetectNavigator;
    private AddStackBottomDialogContract.View mView;

    public AddStackBottomDialogPresenter(AddStackBottomDialogContract.View view) {
        mDetectNavigator = DetectNavigator.getInstance();
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void onStackSelected(StackEntity stackEntity) {
        mDetectNavigator.onStackSelectedNavigator(stackEntity);
    }

    @Override
    public void onAddButtonSelected(StackEntity stackEntity) {
        mDetectNavigator.onAddStackButtonClickedNavigator(stackEntity);
    }

    @Override
    public void addDatabaseListener(StackItemAdapter adapter) {
        mDetectNavigator.addDatabaseListenerNavigator(adapter);
    }

    @Override
    public void onClosedButtonClicked() {
        mDetectNavigator.onDialogCloseButtonClickedNavigator();
    }
}
