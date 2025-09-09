package com.example.advanced_programming_2_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log; // Added import statement
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.advanced_programming_2_android.api.UserAPI;
import com.example.advanced_programming_2_android.database.Chat;
import com.example.advanced_programming_2_android.database.LastMessage;
import com.example.advanced_programming_2_android.database.Message;
import com.example.advanced_programming_2_android.database.Storage;
import com.example.advanced_programming_2_android.database.User;
import com.example.advanced_programming_2_android.viewModels.ChatViewModel;
import com.example.advanced_programming_2_android.viewModels.ChatViewModelFactory;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModel;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.makeramen.roundedimageview.RoundedImageView;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private List<Chat> chats;
    private EditText etSearch;
    private FloatingActionButton addChatBtn;
    private ImageView settings;
    private ImageView logout;
    private ChatAdapter chatAdapter;
    private TextView displayName;
    private RoundedImageView profilePic;
    private Button btnSearch;
    private PreferencesViewModel preferencesViewModel;
    private ChatViewModel chatViewModel;
    private firebaseService firebaseServiceInstance;
    private Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        MyApplication myApp = (MyApplication) getApplication();
        PreferencesViewModelFactory factory = new PreferencesViewModelFactory(getApplicationContext());
        preferencesViewModel = new ViewModelProvider(myApp, factory).get(PreferencesViewModel.class);

        firebaseServiceInstance = firebaseService.getInstance();

        String token = preferencesViewModel.getTokenLiveData(this).getValue();
        String username = preferencesViewModel.getUsernameLiveData(this).getValue();

        String url = preferencesViewModel.getServerAddressLiveData(this).getValue();

        ChatViewModelFactory factoryChat = new ChatViewModelFactory(token, url);
        chatViewModel = new ViewModelProvider(this, factoryChat).get(ChatViewModel.class);

        UserAPI userAPI = new UserAPI(url);
        userAPI.getUserByUsername(username, token);
        MutableLiveData<User> myUser = userAPI.getUserMutableLiveData();

        displayName = findViewById(R.id.displayName);
        profilePic = findViewById(R.id.profilePic);
        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);
        addChatBtn = findViewById(R.id.btnAddChat);
        settings = findViewById(R.id.settings_action_bar);
        logout = findViewById(R.id.logout_action_bar);

        displayName.setText(username);
        myUser.observe(this, user -> {
            Glide.with(this)
                    .load(Uri.parse(user.getProfilePic()))
                    .into(profilePic);
        });

        storage = Storage.getStorage(this);

        List<Chat> dbChats = storage.getAllChats();

        ListView lvChats = findViewById(R.id.lvChats);
        chatAdapter = new ChatAdapter(dbChats);
        lvChats.setAdapter(chatAdapter);

        

        chatViewModel.getChat().observe(this, chatList -> {
            chats = chatList;
            chatAdapter.updateChats(chats);
            storage.updateChats(chatList);
        });

        btnSearch.setOnClickListener(view -> {
            String nameToSearch = etSearch.getText().toString();
            if (!nameToSearch.isEmpty()) {
                List<Chat> filteredChats = new ArrayList<>();
                for (Chat chat : chats) {
                    if (chat.getUser().getDisplayName().toLowerCase().startsWith(nameToSearch.toLowerCase())) {
                        filteredChats.add(chat);
                    }
                }
                chatAdapter.updateChats(filteredChats);
            } else {
                chatAdapter.updateChats(chats); // Update with the original chat list
            }
        });

        lvChats.setOnItemClickListener((parent, view, position, id) -> {
            Chat c = (Chat) chatAdapter.getItem(position);

            // for now not for later
            Intent intent = new Intent(this, MessageActivity.class);
            intent.putExtra("username", c.getUser().getUsername());
            intent.putExtra("profilePic", c.getUser().getProfilePic());
            intent.putExtra("displayName", c.getUser().getDisplayName());
            intent.putExtra("chatId", c.getId());
            startActivity(intent);
        });

        addChatBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddChatActivity.class);
            intent.putExtra("token", token);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        settings.setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        logout.setOnClickListener(view -> {
            storage.clearStorage();

            Intent intent = new Intent(this, MainActivity.class);
            preferencesViewModel.setToken(this, "");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        chatViewModel.getChatsApi();
        observeNotificationEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        
    }

    @Override
    protected void onPause() {
        super.onPause();
        
    }

    private void observeNotificationEvent() {
        firebaseServiceInstance.getNotificationLiveData().observe(this, notificationData -> {
            handleNewChat();
        });
    }

    private void handleNewChat() {
        chatViewModel.getChat().observe(this, chats -> {
            chatAdapter.updateChats(chats);
            chatAdapter.notifyDataSetChanged();
        });
    }

    private String makeTimestampNow() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return currentDateTime.format(formatter);
    }
}
