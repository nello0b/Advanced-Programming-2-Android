package com.example.advanced_programming_2_android.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChatStorageDao {
    @Insert
    void insert(ChatStorage... chat);

    @Query("SELECT * FROM Chats")
    List<ChatStorage> getAllChats();

    //get chat by id
    @Query("SELECT * FROM Chats WHERE id = :id")
    ChatStorage getChat(int id);

    @Query("SELECT * FROM Chats WHERE id = :id")
    ChatStorage getChatById(int id);

    @Update
    void update(ChatStorage... chat);

    @Delete
    void delete(ChatStorage... chat);


}
