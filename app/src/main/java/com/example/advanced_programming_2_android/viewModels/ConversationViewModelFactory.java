package com.example.advanced_programming_2_android.viewModels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

public class ConversationViewModelFactory implements ViewModelProvider.Factory {

    private final int conversationId;
    private final String token;

    private final String url;

    public ConversationViewModelFactory(int conversationId, String token, String url) {
        this.conversationId = conversationId;
        this.token = token;
        this.url = url;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ConversationViewModel.class)) {
            ConversationViewModel conversationViewModel = new ConversationViewModel(conversationId, token, url);
            return Objects.requireNonNull(modelClass.cast(conversationViewModel));
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

}
