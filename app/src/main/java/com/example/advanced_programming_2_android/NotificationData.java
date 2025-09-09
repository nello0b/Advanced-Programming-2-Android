package com.example.advanced_programming_2_android;

public class NotificationData {
    private String title;
    private String body;

    public NotificationData(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NotificationData{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}