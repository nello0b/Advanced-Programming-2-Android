package com.example.advanced_programming_2_android.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "conversations")
@TypeConverters({ConvertersStorage.class})
public class ConversationStorage {
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("users")
    private List<String> users;
    @SerializedName("messages")
    private List<Integer> messages;

    public ConversationStorage(int id, List<String> users, List<Integer> messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<Integer> getMessages() {
        return messages;
    }

    public void setMessages(List<Integer> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        StringBuilder usersString = new StringBuilder();
        for (String user: users) {
            usersString.append(user.toString());
        }
        StringBuilder massagesString = new StringBuilder();
        for (Integer massage: messages) {
            massagesString.append(massage.toString());
        }
        return "Conversation: {" +
                "id = " + id +
                ", users =" + usersString +
                ", messages=" + massagesString +
                '}';
    }
}
