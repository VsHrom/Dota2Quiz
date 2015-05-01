package com.example.dota2quiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.dota2quiz.login.DispatchActivity;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {
    TextView currentUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUserName = (TextView)findViewById(R.id.currentUserName);
        currentUserName.setText("Здраствуйте, " + ParseUser.getCurrentUser().getUsername() + "!");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void StartPlayLayout(View view) {
        Intent intent = new Intent(this,MainGameActiviti.class);
        startActivity(intent);
    }

    public void StartResultLayout(View view) {
        Intent intent = new Intent(this,ResultActivity.class);
        startActivity(intent);
    }
    public void LogOut(View view) {
        ParseUser.getCurrentUser().logOut();
        startActivity(new Intent(MainActivity.this, DispatchActivity.class));
    }
}

