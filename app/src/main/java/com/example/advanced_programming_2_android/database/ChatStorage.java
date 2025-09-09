package com.example.advanced_programming_2_android.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;


// represent the response of GET /api/Chats
@Entity(tableName = "chats")
public class ChatStorage {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private String user;
    @SerializedName("lastMessage")
    private LastMessage lastMessage;

    public ChatStorage(@NonNull int id, String user, LastMessage lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    @Ignore
    public ChatStorage(@NonNull int id, String user) {
        this.id = id;
        this.user = user;
    }

    // Getters and Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ChatStorage other = (ChatStorage) obj;
        return id == other.id &&
                Objects.equals(user, other.user);
    }
}

