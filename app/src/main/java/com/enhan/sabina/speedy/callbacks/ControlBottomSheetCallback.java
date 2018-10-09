package com.enhan.sabina.speedy.callbacks;

import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.List;

public interface ControlBottomSheetCallback {
    void onFabButtonClicked (List<WordEntity> entityList);
    void onDialogCloseButtonClicked();
    void onStackSelected(String stackName);

}
