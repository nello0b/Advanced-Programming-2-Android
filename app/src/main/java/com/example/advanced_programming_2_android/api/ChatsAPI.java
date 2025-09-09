package com.example.advanced_programming_2_android.api;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.advanced_programming_2_android.MyApplication;
import com.example.advanced_programming_2_android.R;
import com.example.advanced_programming_2_android.classes.Msg;
import com.example.advanced_programming_2_android.classes.Username;
import com.example.advanced_programming_2_android.database.Chat;
import com.example.advanced_programming_2_android.database.Conversation;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatsAPI {
    private MutableLiveData<Boolean> isAddChatSucceeded;
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;

    public ChatsAPI(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        isAddChatSucceeded = new MutableLiveData<>();
    }

    public void getChats(MutableLiveData<List<Chat>> chatsListData, String authorization) {
        Call<List<Chat>> call = webServiceAPI.getChats("bearer '" + authorization + "'");
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.isSuccessful()) {
                    List<Chat> chatList = response.body();
                    chatsListData.postValue(chatList);
                } else {
                    Toast.makeText(MyApplication.context, "Could not get your chat", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                Toast.makeText(MyApplication.context, "Failed to connect to the server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createChat(String chatWithUsername, String authorization) {
        Username username = new Username(chatWithUsername);
        Call<Void> call = webServiceAPI.createChat(username,"bearer '" + authorization + "'");
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MyApplication.context, "New chat was created", Toast.LENGTH_LONG).show();
                    isAddChatSucceeded.postValue(true);
                } else if (response.code() == 400) {
                    Toast.makeText(MyApplication.context, "The username " + chatWithUsername + " does not exist", Toast.LENGTH_LONG).show();
                    isAddChatSucceeded.postValue(false);
                } else {
                    Toast.makeText(MyApplication.context, "Failed to create chat", Toast.LENGTH_LONG).show();
                    isAddChatSucceeded.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MyApplication.context, "Failed to connect to the server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getChatById(MutableLiveData<Conversation> conversationData, int id, String authorization) {
        Call<Conversation> call = webServiceAPI.getChatById(id, "bearer '" + authorization + "'");
        call.enqueue(new Callback<Conversation>() {
            @Override
            public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                if (response.isSuccessful()) {
                    Conversation conversation = response.body();
                    conversationData.postValue(conversation);
                } else {
                    Toast.makeText(MyApplication.context, "Could not get your conversation", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Conversation> call, Throwable t) {
                Toast.makeText(MyApplication.context, "Failed to connect to the server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteChat(int id, String authorization) {
        Call<Void> call = webServiceAPI.deleteChat(id, "bearer '" + authorization + "'");
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Chat deleted successfully
                } else {
                    // Handle the error
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MyApplication.context, "Failed to connect to the server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createMessage(String message, int id, String authorization) {
        Msg msg = new Msg(message);
        Call<Void> call = webServiceAPI.createMessage(msg, id, "bearer '" + authorization + "'");
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Message created successfully
                } else {
                    Toast.makeText(MyApplication.context, "Failed to send message", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MyApplication.context, "Failed to connect to the server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getMessagesByChatId(int id, String authorization) {
        Call<Chat> call = webServiceAPI.getMessagesByChatId(id, "bearer '" + authorization + "'");
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if (response.isSuccessful()) {
                    Chat chat = response.body();
                    // Process the chat data with messages
                } else {
                    // Handle the error
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Toast.makeText(MyApplication.context, "Failed to connect to the server", Toast.LENGTH_LONG).show();
            }
        });
    }

    // maybe will be needed to change to repository
    public MutableLiveData<Boolean> getIsAddChatSucceeded() {
        return isAddChatSucceeded;
    }
}
