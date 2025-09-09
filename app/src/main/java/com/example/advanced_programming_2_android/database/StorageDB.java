package com.example.advanced_programming_2_android.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class, MessageStorage.class, ChatStorage.class,ConversationStorage.class}, version = 1, exportSchema = false)
@TypeConverters({ConvertersStorage.class})
public abstract class StorageDB extends RoomDatabase {


    private static StorageDB instance; // Add the instance variable

    public static synchronized StorageDB getInstance() {
        return instance;
    }
    public static synchronized StorageDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), StorageDB.class, "database-storage")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract UserDao getUserDao();
    public abstract MessageStorageDao getMessageDao();
    public abstract ChatStorageDao getChatDao();
    public abstract ConversationStorageDao getConversationDao();
}

