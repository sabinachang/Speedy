package com.enhan.sabina.speedy.callbacks;

import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.detect.StackItemAdapter;

public interface DetectActivityCallback {
    void onDialogCloseButtonClicked();

    void onStackSelected(StackEntity stackEntity);

    void updateTabCountHint(int num);

    void activateFab();

    void deactivateFab();

    void isWordDuplicate(boolean duplicate);

    void onAddStackButtonClicked(StackEntity stackEntity);

    void addDatabaseListener(StackItemAdapter adapter);

    void onDefinitionGotten(String definition);

    void updateTagline(String word);
}
