package com.example.advanced_programming_2_android.database;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class LastMessage {
    @SerializedName("id")
    private int id;
    @SerializedName("created")
    private String created;
    @SerializedName("content")
    private String content;

    public LastMessage(int id, String created, String content) {
        this.id = id;
        this.created = created;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "LastMessage{" +
                "id='" + id + '\'' +
                ", created='" + created + '\'' +
                ", content='" + content + '\'' +
                "}\n";
    }
}
