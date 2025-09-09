package com.example.advanced_programming_2_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.advanced_programming_2_android.api.UserAPI;
import com.example.advanced_programming_2_android.database.AppDB;
import com.example.advanced_programming_2_android.database.User;
import com.example.advanced_programming_2_android.database.UserDao;

import java.util.LinkedList;
import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private UsersListData usersListData;
    private UserAPI userAPI;

    private String token;

    public UserRepository(String token, String url) {
        usersListData = new UsersListData();
        userAPI = new UserAPI(url);
        this.token = token;
    }

    class UsersListData extends MutableLiveData<List<User>> {
        public UsersListData() {
            super();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                //usersListData.postValue(userDao.getAllUsers());
            }).start();
        }
    }

    public LiveData<List<User>> getAllUsers() {
        return usersListData;
    }

    
}
