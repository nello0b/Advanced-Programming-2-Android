package com.example.advanced_programming_2_android;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.advanced_programming_2_android.api.UserAPI;
import com.example.advanced_programming_2_android.classes.FullUser;

import com.example.advanced_programming_2_android.viewModels.PreferencesViewModel;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModelFactory;

import com.example.advanced_programming_2_android.classes.ImageConverter;


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class RegisterActivity extends AppCompatActivity {
    private ActivityResultLauncher<String> filePickerLauncher;
    private ImageView ivProfilepic;
    private EditText edUsername;
    private EditText edPassword;
    private EditText edDisplayName;
    private Button btnRegister;
    private ImageView settings;

    private PreferencesViewModel preferencesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ivProfilepic = findViewById(R.id.ivProfilePic);
        edUsername = findViewById(R.id.edRegUsername);
        edPassword = findViewById(R.id.edRegPassword);
        edDisplayName = findViewById(R.id.edRegDisplayName);
        btnRegister = findViewById(R.id.btnRegister);
        settings = findViewById(R.id.settings_action_bar);

        AtomicBoolean imageWasChosen = new AtomicBoolean(false);
        AtomicReference<String> profilePic = new AtomicReference<>("");

        MyApplication myApp = (MyApplication) getApplication();
        preferencesViewModel = new ViewModelProvider(myApp).get(PreferencesViewModel.class);

        String url = preferencesViewModel.getServerAddressLiveData(this).getValue();

        UserAPI userAPI = new UserAPI(url);

        btnRegister.setOnClickListener(view -> {
            String username = edUsername.getText().toString();
            String password = edPassword.getText().toString();
            String displayName = edDisplayName.getText().toString();

            if (allIsValid(username, password, displayName, imageWasChosen)) {
                // Convert the image to Base64
                String base64Image = ImageConverter.convertImageToBase64(RegisterActivity.this, Uri.parse(profilePic.get()));

                FullUser fullUser = new FullUser(username, password, displayName, base64Image);
                userAPI.createUser(fullUser);
                MutableLiveData<Boolean> tokenLiveData = userAPI.getIsUsernameExist();
                tokenLiveData.observe(this, isUsernameExist -> {
                    if (!isUsernameExist) {
                        finish();
                    }
                });
            }
        });

        filePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            // Handle the selected file URI
            if (uri != null) {
                Glide.with(this)
                        .load(uri)
                        .into(ivProfilepic);

                imageWasChosen.set(true);
                profilePic.set(uri.toString());

            }
        });

        Button btnPicture = findViewById(R.id.btnRegProfilePic);
        btnPicture.setOnClickListener(view -> {
            filePickerLauncher.launch("image/*");
        });

        settings.setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateUsername(String username) {
        return username.length() != 0 && username.length() <= 20;
    }

    private boolean validatePassword(String password) {
        if (password.length() < 8 || password.length() > 20) {
            return false;
        }
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        boolean hasNumber = password.matches(".*\\d.*");
        return hasLetter && hasNumber;
    }

    private boolean validateName(String name) {
        if (name.length() < 2 || name.length() > 20) {
            return false;
        }
        return name.matches("[a-zA-Z ]+");
    }

    @SuppressLint("SetTextI18n")
    private boolean allIsValid(String username, String password, String name, AtomicBoolean isImageValid) {

        if (!validateUsername(username)) {
            Toast.makeText(this, "username needs to be at most 20 characters", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!validatePassword(password)) {
            Toast.makeText(this, "password needs to be at least 8 charcters and at most 20 characters and be made by numbers and letters", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!validateName(name)) {
            Toast.makeText(this, "name needs to be at least 2 charcters and at most 20 characters and by made by only letters", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!isImageValid.get()) {
            Toast.makeText(this, "add an image", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}