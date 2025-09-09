package com.example.advanced_programming_2_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.advanced_programming_2_android.api.FirebaseTokenAPI;
import com.example.advanced_programming_2_android.api.TokenAPI;
import com.example.advanced_programming_2_android.api.UserAPI;
import com.example.advanced_programming_2_android.classes.LoginRequest;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModel;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModelFactory;
import com.google.firebase.messaging.FirebaseMessaging;

public class LogInActivity extends AppCompatActivity {
    private EditText tvUsername;
    private EditText tvPassword;
    private Button btnLogin;
    private ImageView settings;
    private PreferencesViewModel preferencesViewModel;

    private static final String TAG = "LogInActivity";

    public static final int PERMISSION_REQUEST_CODE = 1;
    public static final String POST_NOTIFICATION_PERMISSION = "android.permission.POST_NOTIFICATIONS";

    String url;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        MyApplication myApp = (MyApplication) getApplication();
        PreferencesViewModelFactory factory = new PreferencesViewModelFactory(getApplicationContext());
        preferencesViewModel = new ViewModelProvider(myApp, factory).get(PreferencesViewModel.class);

        tvUsername = findViewById(R.id.usernameLogin);
        tvPassword = findViewById(R.id.passwordLogin);
        btnLogin = findViewById(R.id.login_btn);
        settings = findViewById(R.id.settings_action_bar);

        url = preferencesViewModel.getServerAddressLiveData(this).getValue();

        TokenAPI tokenAPI = new TokenAPI(url);

        btnLogin.setOnClickListener(view -> {
            String username = tvUsername.getText().toString();
            String password = tvPassword.getText().toString();

            LoginRequest loginRequest = new LoginRequest(username, password);
            tokenAPI.login(loginRequest);

            MutableLiveData<String> tokenLiveData = tokenAPI.getTokenLiveData();
            tokenLiveData.observe(this, token -> {

                if (token != null) {
                    preferencesViewModel.setToken(this, token);
                    preferencesViewModel.setUsername(this, username);
                    retrieveFCMToken(username, url);
                    Intent intent = new Intent(this, ChatActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            });
        });


        settings.setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void retrieveFCMToken(String username, String url) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                return;
            }

            // Get the token
            String token = task.getResult();
            FirebaseTokenAPI firebaseTokenAPI = new FirebaseTokenAPI(url);
            firebaseTokenAPI.postFirebaseToken(token, username);

            // Save the token or send it to your server
        });
    }
}
