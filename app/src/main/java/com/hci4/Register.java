package com.hci4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Register extends AppCompatActivity implements View.OnClickListener{

    EditText usernameRegister, passwordRegister;
    TextView loginLink;
    Button registerButton;
    Spinner makeDropDown, modelDropDown;
    private String makeDropDownChoice, modelDropDownChoice;
    DatabaseHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHandler = DatabaseHandler.getInstance(this);

        usernameRegister = (EditText) findViewById(R.id.usernameRegister);
        passwordRegister = (EditText) findViewById(R.id.passwordRegister);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);
        loginLink = (TextView) findViewById(R.id.loginLink);
        loginLink.setOnClickListener(this);
        makeDropDown = (Spinner) findViewById(R.id.makeDropDown);
        modelDropDown = (Spinner) findViewById(R.id.modelDropDown);

        String[] makes = new String[]{"Do not have car", "Audi", "Mercedes", };
        ArrayAdapter<String> makeAdapter =
                new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_dropdown_item, makes);
        makeDropDown.setAdapter(makeAdapter);
        makeDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                makeDropDownChoice = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                makeDropDownChoice = "Do not have car";
            }
        });

        String[] models = new String[]{"None", "A320", "A6", "Mercedes", };
        ArrayAdapter<String> modelAdapter =
                new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_dropdown_item, models);
        modelDropDown.setAdapter(modelAdapter);
        modelDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelDropDownChoice = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                modelDropDownChoice = "None";
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginLink:
                startActivity(new Intent(this, Login.class));
            case R.id.registerButton:
                String username = usernameRegister.getText().toString();
                String password = passwordRegister.getText().toString();
                User newUser = new User(username, password);
                dbHandler.addUser(newUser);
                startActivity(new Intent(this, Login.class));
                break;
        }
    }
}
