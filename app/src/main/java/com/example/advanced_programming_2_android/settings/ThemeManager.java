package com.example.advanced_programming_2_android.settings;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;


public class ThemeManager {

    public static void applyTheme(int theme){
        // checking if the switch is turned on
        if (theme == 1) {

            // setting theme to night mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }

        // if the above condition turns false
        // it means switch is turned off
        // by-default the switch will be off
        else {
            // setting theme to light theme
            AppCompatDelegate.setDefaultNightMode (AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
