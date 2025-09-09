package com.example.advanced_programming_2_android;

import static com.example.advanced_programming_2_android.settings.ThemeManager.applyTheme;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.advanced_programming_2_android.database.Storage;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModel;
import com.example.advanced_programming_2_android.viewModels.PreferencesViewModelFactory;


public class SettingsActivity extends AppCompatActivity {

    private PreferencesViewModel preferencesViewModel;

    private EditText serverAddressInput;

    private RadioButton[] radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        serverAddressInput = findViewById(R.id.edHttp);

        radioButtons = new RadioButton[2];
        radioButtons[0] = findViewById(R.id.rbLight);
        radioButtons[1] = findViewById(R.id.rbDark);

        //PreferencesViewModelFactory factory = new PreferencesViewModelFactory(getApplicationContext());
        MyApplication myApp = (MyApplication) getApplication();
        preferencesViewModel = new ViewModelProvider(myApp).get(PreferencesViewModel.class);

        preferencesViewModel.getThemeLiveData(this).observe(this, theme -> {
            applyTheme(theme);
            radioButtons[theme - 1].setChecked(true);
        });

        preferencesViewModel.getServerAddressLiveData(this).observe(this, address ->{
            EditText addressEditText = serverAddressInput;
            addressEditText.setText(preferencesViewModel.getServerAddressLiveData(this).getValue());
        });

        MutableLiveData<Integer> themeLiveData = preferencesViewModel.getThemeLiveData(this);
        assert themeLiveData.getValue() != null;
        radioButtons[themeLiveData.getValue() - 1].setChecked(true);

        serverAddressInput.setText(preferencesViewModel.getServerAddressLiveData(this).getValue());

        


        Button applyButton = findViewById(R.id.btnApply);
        applyButton.setOnClickListener(view ->{
            // Get the selected radio button's ID
            RadioGroup radioGroup = findViewById(R.id.rgTheme);
            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

            // Find the selected radio button by ID
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            int radioButtonTag = parseInt(selectedRadioButton.getTag().toString());

            // Get the text from the EditText
            String editTextValue = serverAddressInput.getText().toString();

            // Display a Toast with the selected radio button and EditText values
            String toastMessage = "Applied";
            Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();


            if(!editTextValue.equals(preferencesViewModel.getServerAddressLiveData(this).getValue())){
                Storage storage = Storage.getStorage(this);
                storage.clearStorage();

                preferencesViewModel.setServerAddress(this, editTextValue);
                Intent intent = new Intent(this, MainActivity.class);
                preferencesViewModel.setToken(this, "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            preferencesViewModel.setTheme(this, radioButtonTag);
        });
    }
}