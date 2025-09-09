package com.example.advanced_programming_2_android.viewModels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.advanced_programming_2_android.database.Chat;
import com.example.advanced_programming_2_android.repositories.ChatRepository;

import java.util.List;

public class ChatViewModel extends ViewModel {
    private ChatRepository chatRepository;
    private LiveData<Boolean> isAddChatSucceeded;

    public ChatViewModel(String token, String url) {
        chatRepository = new ChatRepository(token, url);
        isAddChatSucceeded = chatRepository.getIsAddChatSucceeded();
    }

    public LiveData<List<Chat>> getChat() {
        return chatRepository.getAllChats();
    }
    public LiveData<Boolean> getIsAddChatSucceeded() {
        return isAddChatSucceeded;
    }

    public void getChatsApi(){
        chatRepository.getChatsApi();
    }

    public void createChatApi(String chatWithUsername){
        chatRepository.createChatApi(chatWithUsername);
    }

}
