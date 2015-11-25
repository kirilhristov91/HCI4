package com.hci4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler dbHandler;
    String username;

    LinearLayout mainLayout;
    EditText placeA;
    EditText placeB;
    ImageButton mainButton;
    NonScrollListView listMain;
    NonScrollListView historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        dbHandler = DatabaseHandler.getInstance(this);
        placeA = (EditText) findViewById(R.id.placeA);
        placeB = (EditText) findViewById(R.id.placeB);
        placeA.setShadowLayer(1,0,0, Color.BLACK);
        placeB.setShadowLayer(1,0,0, Color.BLACK);
        mainButton = (ImageButton) findViewById(R.id.mainButton);
        listMain = (NonScrollListView) findViewById(R.id.listMain);

        historyList = (NonScrollListView) findViewById(R.id.historyList);

        String[] history = {"history item1", "history item2", "history item3","history item4" };
        ArrayAdapter<String> historyAdapter =
                new CustomAdapter(this, history);
        historyList.setAdapter(historyAdapter);
        historyList.setScrollContainer(false);

        mainButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                        buttonClicked(v);
                    }
                }
        );
    }


    public void buttonClicked(View view) {
        if (placeA.getText().toString().equals("") || placeB.getText().toString().equals("")) {
            showError();
        } else {
            String[] routes = new String[2];
            routes[0] = placeA.getText().toString() + " - " + placeB.getText().toString();
            routes[1] = placeA.getText().toString() + " - " + placeB.getText().toString();

            ListAdapter routesAdapter = new CustomAdapter(this, routes);
            listMain.setAdapter(routesAdapter);
            listMain.setScrollContainer(false);
            listMain.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String route = String.valueOf(parent.getItemAtPosition(position));
                            Toast.makeText(MainActivity.this, route, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            finish();
                        }
                    }
            );

        }
    }

    private void showError(){
        AlertDialog.Builder allertBuilder = new AlertDialog.Builder(this);
        allertBuilder.setMessage("Please enter Starting Point and Destination");
        allertBuilder.setPositiveButton("OK", null);
        allertBuilder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!dbHandler.getIfLoggedIn(username)) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.action_feedback){
            Intent intent = new Intent(MainActivity.this, Feedback.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            dbHandler.updateUser(username, 0);
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}