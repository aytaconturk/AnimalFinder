package com.example.animalfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private TextToSpeech T2S;
    private Button speakButton;
    private String questionText;
    private TextView tv_points;
    private TextView question;
    private ImageView im_1;
    private ImageView im_2;
    private final int correctSound = R.raw.correct;
    private final int wrongSound = R.raw.wrong;
    private static String imgName;
    private static int points = 0;
    private static int pointIncrease = 5;

    //Three (3) consecutive correct answer will increase level by 1
    private static int levelIncreaseStatus = 0;

    private int[] images = new int[] {R.drawable.cat, R.drawable.dog, R.drawable.elephant, R.drawable.fox, R.drawable.lion};
    private int[] sounds = new int[] {R.raw.cat, R.raw.dog, R.raw.elephant, R.raw.fox, R.raw.lion};


    public String getResourceNameFromClassByID(int resourceID)
            throws IllegalArgumentException {
        Field[] drawableFields = R.drawable.class.getFields();

        for (Field f : drawableFields) {
            try {

                if (resourceID == f.getInt(null))
                    return f.getName(); // Return the name.
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException();
    }

    public String capitalize(String name){
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    private void getLayoutObjects(){
        // Get the ImageView
        im_1 = findViewById(R.id.imageView1);
        im_2 = findViewById(R.id.imageView2);

        //Get the TextView
        question = findViewById(R.id.tv_question);

        //Get the speak button
        speakButton = findViewById(R.id.speakButton);

        //Get points TextView
        tv_points = findViewById(R.id.tv_points);
    }

    public void start(){

        // Get a random between 0 and images.length-1
        int randomImg1, randomImg2;
        randomImg1 = (int)(Math.random() * images.length);


        while (true) {
            randomImg2 = (int)(Math.random() * images.length);

            if (randomImg1 != randomImg2)
                break;
        }

        imgName = getResourceNameFromClassByID(images[randomImg1]);
        questionText = "Find the " + capitalize(imgName);

        //Set the question
        question.setText(questionText);

        int random = (int)(Math.random() * 2);

        if(random == 0){
            // Set the images
            im_1.setBackgroundResource(images[randomImg1]);
            im_2.setBackgroundResource(images[randomImg2]);

            im_1.setTag(images[randomImg1]);
            im_2.setTag(images[randomImg2]);
        }
        else {
            // Set the images
            im_1.setBackgroundResource(images[randomImg2]);
            im_2.setBackgroundResource(images[randomImg1]);

            im_1.setTag(images[randomImg2]);
            im_2.setTag(images[randomImg1]);
        }

        // print Total point of the gamer
        tv_points.setText(String.valueOf(points));


        // speak the question
        speakQuestionText(questionText, 2000);

        playSound(sounds[randomImg1]);

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayToast(questionText);

                speakQuestionText(questionText, 500);
                playSound(sounds[randomImg1]);

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getLayoutObjects();

        start();
    }

    public void playSound(int resourceID) {
        final MediaPlayer soundMP = MediaPlayer.create(this, resourceID);

        // delay 2 second play to wait text2Speech finished
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                soundMP.start();
            }
        }, 4000);
    }

    private void speakQuestionText(String questionText, long delay){

        T2S = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    T2S.setLanguage(Locale.US);
                }
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                T2S.speak(questionText, TextToSpeech.QUEUE_FLUSH, null);
            }
        }, delay);
    }


    public void playSound(int resourceID, long delay) {
        final MediaPlayer soundMP = MediaPlayer.create(this, resourceID);

        // delay 2 second play to wait text2Speech finished
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                soundMP.start();
            }
        }, delay);

    }



    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void showMessage1(View view) {

        if (checkAnswer(im_1)){
            displayToast("Correct!");

            playSound(correctSound, 500);

            points = points + pointIncrease;
            tv_points.setText(String.valueOf(points));

            levelIncreaseStatus++;

            if(levelIncreaseStatus == 3){
                nextLevel();
            }
            else{
                makePractice();
            }
        }
        else {
            displayToast("Wrong!!!");

            playSound(wrongSound, 500);

            points = 0;
            pointIncrease--;
            levelIncreaseStatus = 0;

            makePractice();
        }


    }

    public void showMessage2(View view) {

        if (checkAnswer(im_2)){
            displayToast("Correct!");

            playSound(correctSound, 500);

            points = points + pointIncrease;
            tv_points.setText(String.valueOf(points));

            levelIncreaseStatus++;

            if(levelIncreaseStatus == 3){
                nextLevel();
            }
            else{
                makePractice();
            }
        }
        else {
            displayToast("Wrong!!!");

            playSound(wrongSound, 500);

            points = 0;
            pointIncrease--;
            levelIncreaseStatus = 0;

            makePractice();
        }

    }

    private void makePractice(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        },3000);
    }

    private void nextLevel(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent level2 = new Intent(MainActivity.this, Level2Activity.class);
                startActivity(level2);
            }
        },3000);
    }

    private int getDrawableId(ImageView iv) {
        return (Integer) iv.getTag();
    }

    private boolean checkAnswer(ImageView imageViewID){

        String name = getResourceNameFromClassByID(getDrawableId(imageViewID));

        if (name.equalsIgnoreCase(imgName))
            return true;
        else
            return false;

    }

}