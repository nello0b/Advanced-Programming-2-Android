package com.example.advanced_programming_2_android.api;

import static com.example.advanced_programming_2_android.MyApplication.context;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.advanced_programming_2_android.MyApplication;
import com.example.advanced_programming_2_android.R;
import com.example.advanced_programming_2_android.classes.LoginRequest;
import com.example.advanced_programming_2_android.settings.ConfigParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenAPI {
    private MutableLiveData<String> tokenLiveData;
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;

    public TokenAPI(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        tokenLiveData = new MutableLiveData<>();
    }

    public void login(LoginRequest loginRequest) {
        Call<String> call = webServiceAPI.login(loginRequest);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    tokenLiveData.setValue(token);
                } else {
                    Toast.makeText(MyApplication.context, "Username or password is incorrect", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MyApplication.context, "Failed to connect to the server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public MutableLiveData<String> getTokenLiveData() {
        return tokenLiveData;
    }
}
