package com.example.advanced_programming_2_android.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.advanced_programming_2_android.database.Conversation;
import com.example.advanced_programming_2_android.database.User;
import com.example.advanced_programming_2_android.repositories.ConversationRepository;

public class ConversationViewModel extends ViewModel {
    private ConversationRepository conversationRepository;
    private LiveData<Conversation> conversation;

    public ConversationViewModel(int conversationId, String token, String url) {
        conversationRepository = new ConversationRepository(conversationId, token, url);
        conversation = conversationRepository.getConversation();
    }

    public LiveData<Conversation> getConversation() {
        return conversation;
    }

    public void getChatByIdApi(){
        conversationRepository.getConversationById();
    }

    public void sendMessageApi(String message, int chatId){
        Conversation conversation = this.conversation.getValue();
        assert conversation != null;
        User user = conversation.getUsers().get(0);
        conversationRepository.sendMessageApi(message, chatId, user);
    }
}
