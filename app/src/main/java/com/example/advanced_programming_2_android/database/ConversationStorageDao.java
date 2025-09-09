package com.example.advanced_programming_2_android.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ConversationStorageDao {
    @Insert
    void insert(ConversationStorage... conversations);

    @Update
    void update(ConversationStorage... conversations);

    @Query("SELECT id FROM conversations")
    List<Integer> getAllConversationIds();

    @Query("SELECT * FROM conversations")
    List<ConversationStorage> getAllConversations();

    @Query("SELECT * FROM conversations WHERE id = :id")
    ConversationStorage getConversationById(int id);

    @Delete
    void delete(ConversationStorage... conversations);


}
