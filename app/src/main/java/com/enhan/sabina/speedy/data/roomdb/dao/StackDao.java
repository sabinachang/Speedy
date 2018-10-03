package com.enhan.sabina.speedy.data.roomdb.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Query;

import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;

import java.util.List;

@Dao
public interface StackDao {

    @Query("SELECT * FROM stack_table")
    LiveData<List<StackEntity>> getAllStacks();

    @Query("SELECT * FROM stack_table WHERE name = :stackName")
    StackEntity getStackInfo(String stackName);

    @Query("DELETE FROM stack_table WHERE id = :id")
    void deleteStack(int id);
}
