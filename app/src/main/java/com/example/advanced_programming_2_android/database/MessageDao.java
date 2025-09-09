package com.example.advanced_programming_2_android.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    void insert(Message... message);

    @Update
    void update(Message... message);

    @Delete
    void delete(Message... message);

    @Query("SELECT * FROM messages")
    List<Message> getAllMessages();

    // get a massage by id
    @Query("SELECT * FROM messages WHERE id = :id")
    Message getMessageById(int id);


}
