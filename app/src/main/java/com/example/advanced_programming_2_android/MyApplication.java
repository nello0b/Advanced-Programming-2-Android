package com.example.advanced_programming_2_android;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;


public class MyApplication extends Application implements ViewModelStoreOwner {
    public static Context context;

    private ViewModelStore viewModelStore;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        viewModelStore = new ViewModelStore();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        viewModelStore.clear();
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return viewModelStore;
    }

    public static Context getContext() {
        return context;
    }
}
