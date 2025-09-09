package com.example.advanced_programming_2_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.advanced_programming_2_android.api.UserAPI;
import com.example.advanced_programming_2_android.database.Chat;
import com.example.advanced_programming_2_android.database.Storage;
import com.example.advanced_programming_2_android.database.User;
import com.example.advanced_programming_2_android.viewModels.ChatViewModel;
import com.example.advanced_programming_2_android.viewModels.ChatViewModelFactory;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModel;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModelFactory;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AddChatActivity extends AppCompatActivity {
    private List<Chat> chats;
    private TextView displayName;
    private RoundedImageView profilePic;
    private EditText etUsername;
    private Button btnAddChat;
    private ImageView settings;
    private ImageView logout;
    private ChatViewModel chatViewModel;

    private PreferencesViewModel preferencesViewModel;

    private Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);

        displayName = findViewById(R.id.displayName);
        profilePic = findViewById(R.id.profilePic);
        etUsername = findViewById(R.id.edAddChatUsername);
        btnAddChat = findViewById(R.id.btnAddChat);
        settings = findViewById(R.id.settings_action_bar);
        logout = findViewById(R.id.logout_action_bar);

        String token = getIntent().getStringExtra("token");
        String username = getIntent().getStringExtra("username");

        MyApplication myApp = (MyApplication) getApplication();

        preferencesViewModel = new ViewModelProvider(myApp).get(PreferencesViewModel.class);
        String url = preferencesViewModel.getServerAddressLiveData(this).getValue();

        UserAPI userAPI = new UserAPI(url);
        userAPI.getUserByUsername(username, token);
        MutableLiveData<User> myUser = userAPI.getUserMutableLiveData();

        displayName.setText(username);
        myUser.observe(this, user -> {
            Glide.with(this)
                    .load(Uri.parse(user.getProfilePic()))
                    .into(profilePic);
        });

        storage = Storage.getStorage(this);

        //chatViewModel = new ChatViewModel(token, url);
        ChatViewModelFactory factoryChat = new ChatViewModelFactory(token, url);
        chatViewModel = new ViewModelProvider(this, factoryChat).get(ChatViewModel.class);

        chatViewModel.getChatsApi();
        chatViewModel.getChat().observe(this, chatList -> {
            chats = chatList;
            storage.updateChats(chats);
        });

        btnAddChat.setOnClickListener(view -> {
            String chatWithUsername = etUsername.getText().toString();
            boolean isUsernameAlreadyExistInChat = false;
            for (Chat chatWith : chats) {
                if (chatWith.getUser().getUsername().equals(chatWithUsername)) {
                    isUsernameAlreadyExistInChat = true;
                    Toast.makeText(MyApplication.context, chatWith.getUser().getDisplayName() + " is already in the friend list", Toast.LENGTH_LONG).show();
                    break;
                }
            }
            if (chatWithUsername.equals(username)) {
                Toast.makeText(MyApplication.context, "Cannot add yourself", Toast.LENGTH_LONG).show();
            } else {
                if (!isUsernameAlreadyExistInChat) {
                    chatViewModel.createChatApi(chatWithUsername);
                    chatViewModel.getIsAddChatSucceeded().observe(this, isAddChatSucceeded -> {
                        if (isAddChatSucceeded) {
                            finish();
                        }
                    });
                }
            }
        });

        settings.setOnClickListener(view -> {
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
    }
}