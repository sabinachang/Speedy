package com.enhan.sabina.speedy.callbacks;

import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;
import com.enhan.sabina.speedy.detect.StackItemAdapter;

import java.util.List;

public interface DetectActivityCallback {
    void onFabButtonClicked (); // detect activity

    void onDialogCloseButtonClicked(); // dialog fragment

    void onStackSelected(StackEntity stackEntity); // dialog fragment

    void updateTabCountHint(int num); // chosen word fragment

    void activateFab(); // chosen word fragment

    void deactivateFab(); //  chosen word fragment

    void isWordDuplicate(boolean duplicate); // chosen word fragment

    void onAddStackButtonClicked(StackEntity stackEntity); // dialog fragment

    void addDatabaseListener(StackItemAdapter adapter); // dialog fragment

}
