package com.enhan.sabina.speedy.data.roomdb.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

import java.util.List;

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WordEntity wordEntity);

    @Query("SELECT * FROM word_table WHERE stack_name = :stackName")
    List<WordEntity> getWordsInStack(String stackName);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWord(WordEntity wordEntity);

    @Query("DELETE FROM word_table WHERE id = :id")
    void deleteWord(int id);
}
