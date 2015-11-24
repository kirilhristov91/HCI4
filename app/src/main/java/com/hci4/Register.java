package com.hci4;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class Register extends AppCompatActivity implements View.OnClickListener{

    EditText usernameRegister, passwordRegister;
    TextView loginLink;
    Button registerButton;
    int consumption;
    DatabaseHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dbHandler = DatabaseHandler.getInstance(this);

        usernameRegister = (EditText) findViewById(R.id.usernameRegister);
        passwordRegister = (EditText) findViewById(R.id.passwordRegister);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);
        loginLink = (TextView) findViewById(R.id.loginLink);
        loginLink.setOnClickListener(this);
        NumberPicker consumptionPicker = (NumberPicker) findViewById(R.id.consumptionPicker);

        consumptionPicker.setMinValue(0);
        consumptionPicker.setMaxValue(20);
        consumptionPicker.setWrapSelectorWheel(false);

        consumptionPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                consumption = newVal;
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
                User newUser = new User(username, password, consumption);
                dbHandler.addUser(newUser);
                startActivity(new Intent(this, Login.class));
                break;
        }
    }
}
