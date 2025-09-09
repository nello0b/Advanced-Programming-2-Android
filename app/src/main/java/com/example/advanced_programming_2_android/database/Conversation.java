package com.example.advanced_programming_2_android.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;
// represent the response of Get /api/Chat/{id}
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "conversations")
@TypeConverters({Converters.class})
public class Conversation {
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("users")
    private List<User> users;
    @SerializedName("messages")
    private List<Message> messages;

    public Conversation(int id, List<User> users, List<Message> messages) {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        StringBuilder usersString = new StringBuilder();
        for (User user: users) {
            usersString.append(user.toString());
        }
        StringBuilder massagesString = new StringBuilder();
        for (Message massage: messages) {
            massagesString.append(massage.toString());
        }
        return "Conversation: {" +
                "id = " + id +
                ", users =" + usersString +
                ", messages=" + massagesString +
                '}';
    }
}
