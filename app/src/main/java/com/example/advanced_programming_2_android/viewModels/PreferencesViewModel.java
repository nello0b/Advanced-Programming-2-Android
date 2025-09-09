package com.example.advanced_programming_2_android.viewModels;


import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.advanced_programming_2_android.R;



public class PreferencesViewModel extends ViewModel {
    private MutableLiveData<String> usernameLiveData;
    private MutableLiveData<String> tokenLiveData;
    private MutableLiveData<String> passwordLiveData;
    private MutableLiveData<Integer> themeLiveData;

    private MutableLiveData<String> serverAddressLiveData;


    public PreferencesViewModel() {
    }

    private SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }


    public MutableLiveData<String> getUsernameLiveData(Context context) {
        if (usernameLiveData == null) {
            usernameLiveData = new MutableLiveData<>();
            loadUsernameFromSharedPreferences(context);
        }
        return usernameLiveData;
    }


    public MutableLiveData<String> getTokenLiveData(Context context) {
        if (tokenLiveData == null) {
            tokenLiveData = new MutableLiveData<>();
            loadTokenFromSharedPreferences(context);
        }
        return tokenLiveData;
    }

    public MutableLiveData<String> getPasswordLiveData(Context context) {
        if (passwordLiveData == null) {
            passwordLiveData = new MutableLiveData<>();
            loadPasswordFromSharedPreferences(context);
        }
        return passwordLiveData;
    }

    public MutableLiveData<Integer> getThemeLiveData(Context context) {
        if (themeLiveData == null) {
            themeLiveData = new MutableLiveData<>();
            loadThemeFromSharedPreferences(context);
        }
        return themeLiveData;
    }

    public void setUsername(Context context, String username) {
        usernameLiveData.setValue(username);
        saveUsernameToSharedPreferences(context);
    }

    public void setToken(Context context, String token) {
        tokenLiveData.setValue(token);
        saveTokenToSharedPreferences(context);
    }

    public void setPassword(Context context, String password) {
        passwordLiveData.setValue(password);
        savePasswordToSharedPreferences(context);
    }

    public void setTheme(Context context, int theme) {
        themeLiveData.setValue(theme);
        saveThemeToSharedPreferences(context);
        //settingsDao.updateTheme(theme);
    }

    private void loadUsernameFromSharedPreferences(Context context) {
        String username = getSharedPreferences(context).getString("username", "");
        usernameLiveData.setValue(username);
    }

    private void loadTokenFromSharedPreferences(Context context) {
        String token = getSharedPreferences(context).getString("token", "");
        tokenLiveData.setValue(token);
    }

    private void loadPasswordFromSharedPreferences(Context context) {
        String password = getSharedPreferences(context).getString("password", "");
        passwordLiveData.setValue(password);
    }



    private void loadThemeFromSharedPreferences(Context context) {
        int theme = getSharedPreferences(context).getInt("theme", 0);
        themeLiveData.setValue(theme);
    }

    private void saveUsernameToSharedPreferences(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("username", usernameLiveData.getValue());
        editor.apply();
    }

    private void saveTokenToSharedPreferences(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("token", tokenLiveData.getValue());
        editor.apply();
    }

    private void savePasswordToSharedPreferences(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("password", passwordLiveData.getValue());
        editor.apply();
    }

    private void saveThemeToSharedPreferences(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt("theme", themeLiveData.getValue());
        editor.apply();
    }

    public MutableLiveData<String> getServerAddressLiveData(Context context) {
        if (serverAddressLiveData == null) {
            serverAddressLiveData = new MutableLiveData<>();
            loadServerAddressFromSharedPreferences(context);
        }
        return serverAddressLiveData;
    }

    public void setServerAddress(Context context, String serverAddress) {
        serverAddressLiveData.setValue(serverAddress);
        saveServerAddressToSharedPreferences(context);
    }


    private void loadServerAddressFromSharedPreferences(Context context) {
        String serverAddress = getSharedPreferences(context).getString("serverAddress", "");
        serverAddressLiveData.setValue(serverAddress);
    }


    private void saveServerAddressToSharedPreferences(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("serverAddress", serverAddressLiveData.getValue());
        editor.apply();
    }
}
