package com.example.advanced_programming_2_android.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;



// represent the response of GET /api/Chats
@Entity(tableName = "chats")
public class Chat {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private User user;
    @SerializedName("lastMessage")
    private LastMessage lastMessage;

    public Chat(@NonNull int id, User user, LastMessage lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    @Ignore
    public Chat(@NonNull int id, User user) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public String toString(){
        String toString="id: "+id+", user: "+user.toString()+", lastMessage: "+"''.";
        if(this.lastMessage!=null){
            toString="id: "+id+", user: "+user.toString()+", lastMessage: "+lastMessage.toString() +".";
        }
        return toString;
    }
}

