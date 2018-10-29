package com.enhan.sabina.speedy.detect;

import com.enhan.sabina.speedy.BasePresenter;
import com.enhan.sabina.speedy.BaseView;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

public class AddStackBottomDialogContract {
    public interface View extends BaseView<Presenter> {

    }

    public interface Presenter extends BasePresenter {
       void onStackSelected(StackEntity stackEntity);

       void onAddButtonSelected(StackEntity stackEntity);

       void addDatabaseListener(StackItemAdapter adapter);

       void onClosedButtonClicked();
    }

}
