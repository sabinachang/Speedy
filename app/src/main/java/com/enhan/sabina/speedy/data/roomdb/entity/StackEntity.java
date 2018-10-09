package com.enhan.sabina.speedy.data.roomdb.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "stack_table")
public class StackEntity {

    public StackEntity(String stackName){
        mStackName = stackName;
    }

    @ColumnInfo(name = "word_count")
    private int mWordCount;

    @ColumnInfo(name = "name")
    private String mStackName;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    public int getWordCount() {
        return mWordCount;
    }

    public void setWordCount(int wordCount) {
        mWordCount = wordCount;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getStackName() {
        return mStackName;
    }

    public void setStackName(String stackName) {
        mStackName = stackName;
    }
}
