package com.example.dota2quiz;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Вадим on 21.03.2015.
 */
public class MyApp extends Application {
    private String Result;
    private ArrayList<String> resultsList;
    private int questionCount;

    public void onCreate(){
        Parse.initialize(this, "wolbalreiikv1h8YL6YXMp499NJXJ0KWbyfJyHXC", "isXyyLsY3t3DEq9m6oDMZ807GWL79SnY2MyJ1kRi");
    }
    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    {
        resultsList = new ArrayList<>(10);
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public ArrayList<String> getResultsList() {
        return resultsList;
    }

    public void setResultsList(ArrayList<String> resultsList) {
        this.resultsList = resultsList;
    }
    public void showList(){
        for(int i=0;i<resultsList.size();i++)
        {
            Log.d("MyApp:", resultsList.get(i));
        }
    }
    public void addResult(String result)
    {
        resultsList.add(result);
    }

}
