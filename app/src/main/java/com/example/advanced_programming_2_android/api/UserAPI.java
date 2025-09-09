package com.example.advanced_programming_2_android.api;

import static com.example.advanced_programming_2_android.MyApplication.context;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.advanced_programming_2_android.MyApplication;
import com.example.advanced_programming_2_android.R;
import com.example.advanced_programming_2_android.classes.FullUser;
import com.example.advanced_programming_2_android.database.User;
import com.example.advanced_programming_2_android.settings.ConfigParser;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModel;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModelFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<Boolean> isUsernameExist;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UserAPI(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        isUsernameExist = new MutableLiveData<>();
        userMutableLiveData = new MutableLiveData<>();
    }

    public void getUserByUsername(String username, String authorization) {

        Call<User> call = webServiceAPI.getUserByUsername(username, authorization);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    userMutableLiveData.setValue(user);
                } 
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Failed to connect to the server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createUser(FullUser user) {
        Call<FullUser> call = webServiceAPI.createUser(user);
        call.enqueue(new Callback<FullUser>() {
            @Override
            public void onResponse(Call<FullUser> call, Response<FullUser> response) {
                if (response.isSuccessful()) {
                    isUsernameExist.setValue(false);
                    Toast.makeText(MyApplication.context, "You were registered", Toast.LENGTH_LONG).show();
                } else {
                    isUsernameExist.setValue(true);
                    Toast.makeText(MyApplication.context, "Username already exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<FullUser> call, Throwable t) {
                Toast.makeText(context, "Failed to connect to the server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public MutableLiveData<Boolean> getIsUsernameExist() {
        return isUsernameExist;
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
