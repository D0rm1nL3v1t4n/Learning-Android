package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String PUT_EXTRA = ".com.example.myfirstapp.USER_DETAILS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateTitleSpinner();

        Button enter = findViewById(R.id.eventNext);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterClicked();
            }
        });

        Button database = findViewById(R.id.database);
        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseClicked();
            }
        });

    }

    private void CreateTitleSpinner() {
        Spinner titleSpinner = findViewById(R.id.title);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.title_array, R.layout.my_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSpinner.setAdapter(adapter);
    }

    private void EnterClicked() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map = GetMessage(map);

        String message = "";
        for (Object key : map.keySet()) {
            message += key + ": " + map.get(key) + "\n";
        }
        NextActivity(message);
    }

    private void NextActivity(String message) {
        Intent intent = new Intent(this, ConfirmDetailsActivity.class);
        intent.putExtra(PUT_EXTRA, message);
        startActivity(intent);
    }

    private Map GetMessage(Map map) {

        Spinner titleSpinner = findViewById(R.id.title);
        EditText firstNameEditText = findViewById(R.id.firstName);
        EditText lastNameEditText = findViewById(R.id.lastName);
        DatePicker dateOfBirthDatePicker = findViewById(R.id.dateOfBirth);
        EditText emailAddressEditText = findViewById(R.id.emailAddress);
        EditText phoneNumberEditText = findViewById(R.id.phoneNumber);
        EditText addressEditText = findViewById(R.id.address);

        map.put("Title", titleSpinner.getSelectedItem().toString());
        map.put("First Name", firstNameEditText.getText().toString());
        map.put("Last Name", lastNameEditText.getText().toString());
        map.put("Date of Birth", GetDateOfBirth(dateOfBirthDatePicker));
        map.put("Email Address", emailAddressEditText.getText().toString());
        map.put("Phone Number", phoneNumberEditText.getText().toString());
        map.put("Address", addressEditText.getText().toString());

        return map;
    }

    private String GetDateOfBirth(DatePicker dateOfBirthDatePicker) {
        int dobDay = dateOfBirthDatePicker.getDayOfMonth();
        int dobMonth = dateOfBirthDatePicker.getMonth() + 1;
        int dobYear = dateOfBirthDatePicker.getYear();
        String dateOfBirth = Integer.toString(dobDay) + "/" + Integer.toString(dobMonth) + "/" + Integer.toString(dobYear);
        return dateOfBirth;
    }

    private void DatabaseClicked() {
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }

}
