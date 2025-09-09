package com.example.advanced_programming_2_android.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageStorageDao {
    @Insert
    void insert(MessageStorage... message);

    @Update
    void update(MessageStorage... message);

    @Delete
    void delete(MessageStorage... message);

    @Query("SELECT * FROM messages")
    List<MessageStorage> getAllMessages();

    // get a massage by id
    @Query("SELECT * FROM messages WHERE id = :id")
    MessageStorage getMessageById(int id);

    @Query("SELECT id FROM messages ORDER BY id DESC LIMIT 1")
    int getLastInsertedMessageId();
}
