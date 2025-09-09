package com.example.advanced_programming_2_android.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "messages")
@TypeConverters({Converters.class})
public class Message {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private int id;
    @SerializedName("created")
    private String createdDate;
    @SerializedName("sender")
    private User sender;
    @SerializedName("content")
    private String contact;

    public Message(@NonNull int id, String createdDate, User sender, String contact) {
        this.id = id;
        this.createdDate = createdDate;
        this.sender = sender;
        this.contact = contact;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", createdDate='" + createdDate + '\'' +
                ", sender=" + sender.toString() +
                ", contact='" + contact + '\'' +
                "}\n";
    }
}

