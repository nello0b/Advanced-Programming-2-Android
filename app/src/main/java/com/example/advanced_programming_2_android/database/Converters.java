package com.example.advanced_programming_2_android.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.example.advanced_programming_2_android.database.LastMessage;


public class Converters {
    // List<String> converter
    @TypeConverter
    public static List<String> fromString(String value) {
        return value == null ? new ArrayList<>() : new Gson().fromJson(value, new TypeToken<List<String>>() {}.getType());
    }

    @TypeConverter
    public static String listToString(List<String> list) {
        return new Gson().toJson(list);
    }

    // User converter
    @TypeConverter
    public static User fromUserString(String value) {
        return value == null ? null : new Gson().fromJson(value, User.class);
    }

    @TypeConverter
    public static String userToString(User user) {
        return user == null ? null : new Gson().toJson(user);
    }

    // List<Integer> converter
    @TypeConverter
    public static List<Integer> fromIntegerString(String value) {
        List<Integer> integerList = new ArrayList<>();
        String[] integerStrings = value.split(",");
        for (String integerString : integerStrings) {
            integerList.add(Integer.parseInt(integerString));
        }
        return integerList;
    }

    @TypeConverter
    public static String listToIntegerString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (Integer item : list) {
            sb.append(item);
            sb.append(",");
        }
        return sb.toString();
    }

    // List<User> converter
    @TypeConverter
    public static List<User> fromUserListString(String value) {
        return value == null ? new ArrayList<>() : new Gson().fromJson(value, new TypeToken<List<User>>() {}.getType());
    }

    @TypeConverter
    public static String userListToString(List<User> userList) {
        return new Gson().toJson(userList);
    }

    // List<Message> converter
    @TypeConverter
    public static List<Message> fromMessageListString(String value) {
        return value == null ? new ArrayList<>() : new Gson().fromJson(value, new TypeToken<List<Message>>() {}.getType());
    }

    @TypeConverter
    public static String messageListToString(List<Message> messageList) {
        return new Gson().toJson(messageList);
    }

    // LastMessage converter
    @TypeConverter
    public static LastMessage fromLastMessageString(String value) {
        return value == null ? null : new Gson().fromJson(value, LastMessage.class);
    }

    @TypeConverter
    public static String lastMessageToString(LastMessage lastMessage) {
        return lastMessage == null ? null : new Gson().toJson(lastMessage);
    }

}
