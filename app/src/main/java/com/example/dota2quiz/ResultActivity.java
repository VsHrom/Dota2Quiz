package com.example.dota2quiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class ResultActivity extends ActionBarActivity {
    String[] names;
    String[] results ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        names = new String[2];
        names[0] = "Вася";
        names[1] = "Петя";


        /*MyApp globalResultArray = ((MyApp)getApplicationContext());
        String results[] = new String[globalResultArray.getResultsList().size()];
        results = globalResultArray.getResultsList().toArray(results);
        Arrays.sort(results, Collections.reverseOrder());*/

        Log.d("score","before initialze");
        //Parse.initialize(this, "wolbalreiikv1h8YL6YXMp499NJXJ0KWbyfJyHXC", "isXyyLsY3t3DEq9m6oDMZ807GWL79SnY2MyJ1kRi");
        Log.d("score","after initialze");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
        //query.whereGreaterThan("score", "0");
        query.setLimit(20); // limit to at most 20 results
        query.orderByDescending("score");
        Log.d("score","Start query");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    results = new String[scoreList.size()];
                    int i=0;
                    for(ParseObject obj : scoreList){
                        results[i]=Integer.toString(obj.getInt("score"));
                        Log.d("score:",i+": "+ results[i]);
                        i++;
                    }
                    showList(results);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
        //Log.d("score",results[0]);


    }
    public void showList(String[] names){
        // находим список
        ListView lvMain = (ListView) findViewById(R.id.listView);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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

    public void StartMainGameActivityLayout(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
