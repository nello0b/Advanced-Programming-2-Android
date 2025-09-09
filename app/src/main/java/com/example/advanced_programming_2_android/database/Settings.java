package com.example.advanced_programming_2_android.database;

import static com.example.advanced_programming_2_android.MyApplication.context;
import static java.lang.Integer.parseInt;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.advanced_programming_2_android.R;
import com.example.advanced_programming_2_android.settings.ConfigParser;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

@Entity(tableName = "settings")
public class Settings {

    @PrimaryKey
    private final int id = 1;
    @SerializedName("serverAddress")
    private String serverAddress;

    @SerializedName("theme")
    private int theme;


    public Settings() {
        Map<String, String> configMap = ConfigParser.parseConfig(context, R.xml.config);
        String defaultTheme = configMap.get("theme");
        String defaultServerAddress = configMap.get("server_address");
        assert defaultTheme != null;
        serverAddress = defaultServerAddress;
        theme = parseInt(defaultTheme);
    }


    public String getServerAddress() {
        return serverAddress;
    }

    public int getTheme() {
        return theme;
    }

    public int getId() {
        return id;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public void setId(int id) {

    }
}
