package com.example.advanced_programming_2_android.api;


import com.example.advanced_programming_2_android.MyApplication;
import com.example.advanced_programming_2_android.R;
import com.example.advanced_programming_2_android.classes.FireBaseRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirebaseTokenAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;

    public FirebaseTokenAPI(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void postFirebaseToken(String firebaseToken, String username) {
        FireBaseRequest fireBaseRequest = new FireBaseRequest(firebaseToken, username);
        Call<String> call = webServiceAPI.postFireBaseToken(fireBaseRequest);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String result = response.body();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Handle network failure
            }
        });
    }
}
