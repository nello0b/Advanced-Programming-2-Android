package com.example.advanced_programming_2_android.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChatDao {
    @Insert
    void insert(Chat... chat);

    @Query("SELECT * FROM Chats")
    List<Chat> getAllChats();

    //get chat by id
    @Query("SELECT * FROM Chats WHERE id = :id")
    Chat getChat(int id);

    @Update
    void update(Chat... chat);

    @Delete
    void delete(Chat... chat);


}
