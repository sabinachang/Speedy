package com.enhan.sabina.speedy.data.roomdb.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "word_table",
        foreignKeys = {
            @ForeignKey(entity = StackEntity.class,
                    parentColumns = "id",
                    childColumns = "stack_id",
                    onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "stack_id")
        })
public class WordEntity {

    public WordEntity(String word, String definition, String stackName, int stackId) {
        mWord = word;
        mDefinition = definition;
        mStackId = stackId;
        mStackName = stackName;
    }

    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    @ColumnInfo(name = "definition")
    private String mDefinition;

    @ColumnInfo(name = "stack_id")
    private int mStackId;

    @ColumnInfo(name = "stack_name")
    private String mStackName;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    public String getWord() {
        return mWord;
    }

    public void setWord(@NonNull String word) {
        mWord = word;
    }

    public String getDefinition() {
        return mDefinition;
    }

    public void setDefinition(String definition) {
        mDefinition = definition;
    }

    public int getStackId() {
        return mStackId;
    }

    public void setStackId(int stackId) {
        mStackId = stackId;
    }

    public int getId() {
        return id;
    }

    public String getStackName() {
        return mStackName;
    }

    public void setStackName(String stackName) {
        mStackName = stackName;
    }
}
