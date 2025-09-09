package com.example.advanced_programming_2_android.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "messages")
@TypeConverters({ConvertersStorage.class})
public class MessageStorage {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @SerializedName("id")
    private Integer id;

    @NonNull
    @SerializedName("id2")
    private int id2;
    @SerializedName("created")
    private String createdDate;
    @SerializedName("sender")
    private String sender;
    @SerializedName("content")
    private String contact;

    public MessageStorage(@NonNull int id2, String createdDate, String sender, String contact) {
        this.id2 = id2;
        this.createdDate = createdDate;
        this.sender = sender;
        this.contact = contact;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender.getUsername();
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
                "id2=" + id2 +
                ", createdDate='" + createdDate + '\'' +
                ", sender=" + sender +
                ", contact='" + contact + '\'' +
                "}\n";
    }


}
