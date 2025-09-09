package com.example.advanced_programming_2_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.advanced_programming_2_android.api.UserAPI;
import com.example.advanced_programming_2_android.database.Conversation;
import com.example.advanced_programming_2_android.database.Message;
import com.example.advanced_programming_2_android.database.Storage;
import com.example.advanced_programming_2_android.database.User;
import com.example.advanced_programming_2_android.viewModels.ConversationViewModel;
import com.example.advanced_programming_2_android.viewModels.ConversationViewModelFactory;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModel;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModelFactory;
import com.makeramen.roundedimageview.RoundedImageView;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MessageActivity extends AppCompatActivity {
    private ImageView logout;
    private PreferencesViewModel preferencesViewModel;
    private ConversationViewModel conversationViewModel;
    private ImageView settingsButton; // Declaring a private ImageView variable called settings

    private ImageButton sendButton;

    private EditText inputMessage;

    private RecyclerView messagesRecycleView;

    private MessageAdapter messageAdapter;
    private firebaseService firebaseServiceInstance;

    private Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        MyApplication myApp = (MyApplication) getApplication();
        PreferencesViewModelFactory factory = new PreferencesViewModelFactory(getApplicationContext());
        preferencesViewModel = new ViewModelProvider(myApp, factory).get(PreferencesViewModel.class);

        firebaseServiceInstance = firebaseService.getInstance();

        String token = preferencesViewModel.getTokenLiveData(this).getValue();
        Uri profilePic = Uri.parse(getIntent().getStringExtra("profilePic"));
        String displayName = getIntent().getStringExtra("displayName");
        int chatId = getIntent().getIntExtra("chatId", 0);
        String partnerUsername =  getIntent().getStringExtra("username");

        String url = preferencesViewModel.getServerAddressLiveData(this).getValue();

        ConversationViewModelFactory factoryConversation = new ConversationViewModelFactory(chatId, token, url);
        conversationViewModel = new ViewModelProvider(this, factoryConversation).get(ConversationViewModel.class);

        RoundedImageView rivProfilePic = findViewById(R.id.profilePic);
        TextView tvDisplayName = findViewById(R.id.displayName);
        settingsButton = findViewById(R.id.settings_action_bar);
        logout = findViewById(R.id.logout_action_bar);
        sendButton = findViewById(R.id.sentBtn);
        inputMessage = findViewById(R.id.inputMessage);
        messagesRecycleView =  findViewById(R.id.chatRecycleView);

        String username = preferencesViewModel.getUsernameLiveData(this).getValue();

        storage = Storage.getStorage(this);

        List<String> users = new ArrayList<>();
        users.add(partnerUsername);
        users.add(username);
        Conversation dbConversation = storage.getConversationByUsernames(users);
        List<Message> dbMessages;
        if(dbConversation != null){
            dbMessages = dbConversation.getMessages();
        }
        else {
            dbMessages = new ArrayList<>();
        }

        messageAdapter = new MessageAdapter(dbMessages, username);
        messagesRecycleView.setAdapter(messageAdapter);
        messagesRecycleView.setLayoutManager(new LinearLayoutManager(this));

        // Load the profile picture into the RoundedImageView using Glide library
        Glide.with(this)
                .load(profilePic)
                .into(rivProfilePic);

        // Set the display name in the TextView
        tvDisplayName.setText(displayName);

        conversationViewModel.getChatByIdApi();
        conversationViewModel.getConversation().observe(this, conversation -> {
            if(conversation != null){
                messageAdapter.setMessages(conversation.getMessages());
                messageAdapter.notifyDataSetChanged();
                if (messageAdapter.getItemCount() > 0) {
                    messagesRecycleView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                }
                storage.updateConversation(conversation);
            }
        });

        sendButton.setOnClickListener(view -> {
            String messageToSend = inputMessage.getText().toString().trim();
            if (!messageToSend.equals("")) {
                conversationViewModel.sendMessageApi(messageToSend, chatId);
            }
            inputMessage.setText("");
            // scroll to the bottom of the list
            if (messageAdapter.getItemCount() > 0) {
                messagesRecycleView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
            }
            messageAdapter.notifyDataSetChanged();
        });


        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        logout.setOnClickListener(view -> {
            storage.clearStorage();

            preferencesViewModel.setToken(this, "");
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        observeNotificationEvent(url, token);
    }

    private void observeNotificationEvent(String url, String token) {
        firebaseServiceInstance.getNotificationLiveData().observe(this, notificationData -> {
            String fromUsername = notificationData.getTitle().split(" ")[0];
            String content = notificationData.getBody();
            UserAPI userAPI = new UserAPI(url);
            userAPI.getUserByUsername(fromUsername, token);
            userAPI.getUserMutableLiveData().observe(this, user->{
                Message newMessage = new Message(messageAdapter.getMessages().size(),
                        makeTimestampNow(), user, content);
                messageAdapter.addMessage(newMessage);
                messageAdapter.notifyDataSetChanged();
                messagesRecycleView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
            });
        });
    }

    private String makeTimestampNow() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return currentDateTime.format(formatter);
    }

}