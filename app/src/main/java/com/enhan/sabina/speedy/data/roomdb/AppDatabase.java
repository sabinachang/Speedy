package com.enhan.sabina.speedy.data.roomdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.enhan.sabina.speedy.data.roomdb.dao.StackDao;
import com.enhan.sabina.speedy.data.roomdb.dao.WordDao;
import com.enhan.sabina.speedy.data.roomdb.entity.StackEntity;
import com.enhan.sabina.speedy.data.roomdb.entity.WordEntity;

@Database(entities = {WordEntity.class, StackEntity.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WordDao wordDao();
    public abstract StackDao stackDao();

    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(final Context context) {
        if (sInstance == null){
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context, AppDatabase.class, "speedy_database")
                            .build();
                }
            }
        }
        return sInstance;
    }
}
