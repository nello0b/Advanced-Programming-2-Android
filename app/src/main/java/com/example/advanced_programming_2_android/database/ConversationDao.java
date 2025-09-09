package com.example.advanced_programming_2_android.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ConversationDao {
    @Insert
    void insert(Conversation... conversations);

    @Query("SELECT * FROM conversations")
    List<Conversation> getAllConversations();

    @Query("SELECT * FROM conversations WHERE id = :id")
    Conversation getConversationById(int id);

    @Delete
    void delete(Conversation... conversations);


}
