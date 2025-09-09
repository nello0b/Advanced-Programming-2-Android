package com.example.advanced_programming_2_android.classes;

public class FireBaseRequest {

    private String fireBaseToken;
    private String username;

    public FireBaseRequest(String fireBaseToken, String username) {
        this.fireBaseToken = fireBaseToken;
        this.username = username;
    }

    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public void setFireBaseToken(String fireBaseToken) {
        this.fireBaseToken = fireBaseToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "fireBaseRequest{" +
                "fireBaseToken='" + fireBaseToken + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}