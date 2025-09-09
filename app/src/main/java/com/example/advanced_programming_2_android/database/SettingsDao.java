package com.example.advanced_programming_2_android.database;
import com.example.advanced_programming_2_android.database.Settings;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SettingsDao {

    @Insert
    void insert(Settings settings);

    @Query("UPDATE Settings SET serverAddress = :serverAddress")
    void updateServerAddress(String serverAddress);

    @Query("UPDATE Settings SET theme = :theme")
    void updateTheme(int theme);

    @Query("SELECT serverAddress FROM settings LIMIT 1")
    String getServerAddress();

    @Query("SELECT theme FROM settings LIMIT 1")
    int getTheme();

    @Query("DELETE FROM settings")
    void deleteAll();

    @Query("SELECT * FROM settings LIMIT 1")
    Settings getSettings();
}

