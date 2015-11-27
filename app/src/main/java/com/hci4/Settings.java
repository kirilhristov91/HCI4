package com.hci4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    int consumption;
    String username;
    DatabaseHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        dbHandler = DatabaseHandler.getInstance(this);

        final EditText oldPassword = (EditText) findViewById(R.id.settingsOld);
        final EditText newPassword = (EditText) findViewById(R.id.settingsNew);

        NumberPicker consumptionPicker = (NumberPicker) findViewById(R.id.consumptionPickerSettings);

        consumptionPicker.setMinValue(0);
        consumptionPicker.setValue(10);
        consumptionPicker.setMaxValue(20);
        consumptionPicker.setWrapSelectorWheel(false);

        consumptionPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                consumption = newVal;
            }
        });


        Button confirmPasswordChange = (Button) findViewById(R.id.confirmPasswordChange);
        confirmPasswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldPassword.getText().toString().equals("") || newPassword.getText().toString().equals("")) {
                    showError();
                } else {
                    dbHandler.updatePassword(username, newPassword.getText().toString());
                    String text = "Your password has been changed successfully";
                    Toast toast = Toast.makeText(Settings.this, text , Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        Button confirmConsumptionChange = (Button) findViewById(R.id.confirmConsumptionChange);
        confirmConsumptionChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHandler.updateConsumption(username, consumption);
                String text = "Your consumption has been changed successfully";
                Toast toast = Toast.makeText(Settings.this, text , Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private void showError(){
        AlertDialog.Builder allertBuilder = new AlertDialog.Builder(this);
        allertBuilder.setMessage("Please enter Old and New Password");
        allertBuilder.setPositiveButton("OK", null);
        allertBuilder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.backtohome_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_gohome) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
