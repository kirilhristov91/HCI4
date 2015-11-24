package com.hci4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {

    TextView registerLink;
    Button loginButton;
    DatabaseHandler dbHandler;
    EditText usernameLogin, passwordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dbHandler = DatabaseHandler.getInstance(this);
        usernameLogin = (EditText) findViewById(R.id.usernameLogin);
        passwordLogin = (EditText) findViewById(R.id.passwordLogin);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        registerLink = (TextView) findViewById(R.id.registerLink);
        registerLink.setOnClickListener(this);
        //registerLink.setText(dbHandler.printDatabase());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerLink:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.loginButton:
                String username = usernameLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                if(dbHandler.authenticate(username,password)) {
                    dbHandler.updateUser(username, 1);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
                else {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                    dlgAlert.setMessage("incorrect username or password");
                    dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("Try again", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                    dlgAlert.setPositiveButton("Try again",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                }
                break;
        }
    }
}
