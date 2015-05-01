package com.example.dota2quiz;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.view.View.OnLongClickListener;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class MainGameActiviti extends Activity {
    //data bank
    private static String[] questions;
    private static String[] answers1;
    private static String[] answers2;
    private static String[] answers3;
    private static String[] answers4;
    private static Integer[] correctAnswer;
    //Data bank
    Chronometer chron;
    private int questionNumber;
    final String FILENAME = "Question.txt";
    //Elements on Layoout
    TextView questionTextView;
    TextView numberTextView;
    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;
    //Elements on Layout
    String timeString;
    //Aplication
    private static int questionCount;
    private int randomQuestion [];
    //Aplication
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_activiti);
        questions = new String[33];
        answers1 = new String[33];
        answers2 = new String[33];
        answers3 = new String[33];
        answers4 = new String[33];
        correctAnswer = new Integer[33];


        questionTextView = (TextView)findViewById(R.id.textView);
        numberTextView = (TextView)findViewById(R.id.textView2);
        answer1 = (Button)findViewById(R.id.button);
        answer2 = (Button)findViewById(R.id.button2);
        answer3 = (Button)findViewById(R.id.button3);
        answer4 = (Button)findViewById(R.id.button4);

        chron = (Chronometer)findViewById(R.id.chronometer);
        chron.start();
        try {
            loadQuestions(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        randomQuestion = new int[questionCount];
        for(int i=0;i<questionCount;i++)
        {
            randomQuestion[i]=i;
            Log.d("Rand",Integer.toString(randomQuestion[i]));
        }
        shuffleArray(randomQuestion);
        Log.i("Rand", Arrays.toString(randomQuestion));

        questionNumber=-1;
        setQuestion();
    }
    public void onClick(View view)
    {
       switch (view.getId()){
           case R.id.button:
               Log.d("MY","Нажата кнопка 1");
                   if (correctAnswer[randomQuestion[questionNumber]] == 1) {
                       setQuestion();
                   }
                   else
                       youLose();
               break;
           case R.id.button2:
               Log.d("MY","Нажата кнопка 2");
               if (correctAnswer[randomQuestion[questionNumber]] == 2) {
                   setQuestion();
               }
               else
                   youLose();
               break;
           case R.id.button3:
               Log.d("MY","Нажата кнопка 3");
               if (correctAnswer[randomQuestion[questionNumber]] == 3) {
                   setQuestion();
               }
               else
                   youLose();
               break;
           case R.id.button4:
               Log.d("MY","Нажата кнопка 4");
               if (correctAnswer[randomQuestion[questionNumber]] == 4) {
                   setQuestion();
               }
               else
                   youLose();
               break;
       }
    }
    public void youLose()
    {
        answer1.setVisibility(View.INVISIBLE);
        answer2.setVisibility(View.INVISIBLE);
        answer3.setVisibility(View.INVISIBLE);
        answer4.setVisibility(View.INVISIBLE);
        questionTextView.setTextSize(48);
        questionTextView.setText("You lose"+"\n"+"Результат: "+String.valueOf(questionNumber));
        numberTextView.setVisibility(View.INVISIBLE);
        chron.stop();
        CharSequence time = chron.getText();
        timeString = time.toString();
        MyApp globalResultArray = ((MyApp)getApplicationContext());
        globalResultArray.addResult(String.valueOf(questionNumber)+"     time : "+timeString);

        shareScoreOnline();



    }
    public void shareScoreOnline(){
        //Parse.initialize(this, "wolbalreiikv1h8YL6YXMp499NJXJ0KWbyfJyHXC", "isXyyLsY3t3DEq9m6oDMZ807GWL79SnY2MyJ1kRi");

        ParseObject testObject = new ParseObject("GameScore");
        testObject.put("score", questionNumber);
        testObject.put("time", timeString);
        testObject.put("userName", ParseUser.getCurrentUser().getUsername());
        testObject.saveInBackground();

    }
    public void youWin()
    {
        answer1.setVisibility(View.INVISIBLE);
        answer2.setVisibility(View.INVISIBLE);
        answer3.setVisibility(View.INVISIBLE);
        answer4.setVisibility(View.INVISIBLE);
        questionTextView.setTextSize(48);
        questionTextView.setText("You Win!!!" + "\n" + "Результат: " + String.valueOf(questionNumber));
        numberTextView.setVisibility(View.INVISIBLE);
        chron.stop();
        CharSequence time = chron.getText();
        String timeString = time.toString();
        MyApp globalResultArray = ((MyApp)getApplicationContext());
        globalResultArray.addResult(String.valueOf(questionNumber)+"     time : "+timeString);

        shareScoreOnline();


    }

    public void setQuestion()
    {

        questionNumber++;
        Log.d("MY",Integer.toString(questionNumber));
        if(questionNumber==(questionCount)){
            youWin();
            return;
        }

        questionTextView.setText(questions[randomQuestion[questionNumber]]);
        numberTextView.setText(Integer.toString(questionNumber+1));
        answer1.setText(answers1[randomQuestion[questionNumber]]);
        answer2.setText(answers2[randomQuestion[questionNumber]]);
        answer3.setText(answers3[randomQuestion[questionNumber]]);
        answer4.setText(answers4[randomQuestion[questionNumber]]);

    }

    public static void loadQuestions (Context context) throws IOException {
        try {
            AssetManager assetManager = context.getAssets();
            InputStreamReader istream = new InputStreamReader(assetManager.open("Question.txt"));
            BufferedReader in = new BufferedReader(istream);
            String word;
            //int i=0;
            /*while ((word = in.readLine()) != null) {
                questions[i]=word;
                Log.d("MY",questions[i]);
                i++;

            }*/
            int n;

            word=in.readLine();
            n=Integer.parseInt(word);
            questionCount=n;
            Log.d("TESTS:кількість тестів",Integer.toString(n));
            for(int i=0;i<n;i++)
            {
                word=in.readLine();
                questions[i]=word;
                Log.d("TESTS:question",questions[i]);
                word=in.readLine();
                answers1[i]=word;
                Log.d("TESTS:answer",answers1[i]);
                word=in.readLine();
                answers2[i]=word;
                Log.d("TESTS:answer",answers2[i]);
                word=in.readLine();
                answers3[i]=word;
                Log.d("TESTS:answer",answers3[i]);
                word=in.readLine();
                answers4[i]=word;
                Log.d("TESTS:answer",answers4[i]);
                word = in.readLine();
                correctAnswer[i]=Integer.parseInt(word);
                Log.d("TESTS:correct answer:",Integer.toString(correctAnswer[i]));
            }
            in.close();
        } catch (FileNotFoundException e) {
            // FileNotFoundExpeption
        } catch (IOException e) {
            // IOExeption
        }
    }
    static void shuffleArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_game_activiti, menu);
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
