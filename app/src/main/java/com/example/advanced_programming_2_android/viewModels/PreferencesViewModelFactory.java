package com.example.advanced_programming_2_android.viewModels;

//import static com.example.advanced_programming_2_android.viewModels.PreferencesViewModel.createPreferencesViewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;



public class PreferencesViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public PreferencesViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(PreferencesViewModel.class)) {
            return (T) new PreferencesViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
