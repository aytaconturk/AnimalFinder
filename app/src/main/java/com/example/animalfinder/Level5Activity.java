package com.example.animalfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Level5Activity extends AppCompatActivity {

    private TextToSpeech T2S;
    private Button speakButton;
    private String questionText;
    private TextView tv_points;
    private TextView tv_highScore;
    private TextView question;
    private ImageView im_1;
    private ImageView im_2;
    private ImageView im_3;
    private ImageView im_4;
    private ImageView im_5;
    private ImageView im_6;
    private ImageView im_7;
    private ImageView im_8;
    private final int correctSound = R.raw.correct;
    private final int wrongSound = R.raw.wrong;
    private static String imgName;
    private static int highScore = 0;
    private static int points = 0;
    private static int pointIncrease = 15;

    //Three (3) consecutive correct answer will increase level by 1
    private static int levelIncreaseStatus = 0;

    //animal images
    private int[] images = new int[] {R.drawable.cat, R.drawable.dog, R.drawable.elephant, R.drawable.fox,
            R.drawable.lion, R.drawable.bird, R.drawable.bat, R.drawable.eagle, R.drawable.bear,
            R.drawable.camel, R.drawable.chicken, R.drawable.cock, R.drawable.cow, R.drawable.donkey,
            R.drawable.duck, R.drawable.horse, R.drawable.monkey, R.drawable.sheep, R.drawable.snake,
            R.drawable.wolf, R.drawable.zebra};

    //animal sounds
    private int[] sounds = new int[] {R.raw.cat, R.raw.dog, R.raw.elephant, R.raw.fox,
            R.raw.lion, R.raw.bird, R.raw.bat, R.raw.eagle, R.raw.bear,
            R.raw.camel, R.raw.chicken, R.raw.cock, R.raw.cow, R.raw.donkey,
            R.raw.duck, R.raw.horse, R.raw.monkey, R.raw.sheep, R.raw.snake,
            R.raw.wolf, R.raw.zebra};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level5);

        getLayoutObjects();

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        highScore = preferences.getInt("lastScore", 0);
        tv_highScore.setText(String.valueOf(highScore));

        start();
    }

    private void getLayoutObjects(){
        // Get the ImageView
        im_1 = findViewById(R.id.imageView1);
        im_2 = findViewById(R.id.imageView2);
        im_3 = findViewById(R.id.imageView3);
        im_4 = findViewById(R.id.imageView4);
        im_5 = findViewById(R.id.imageView5);
        im_6 = findViewById(R.id.imageView6);
        im_7 = findViewById(R.id.imageView7);
        im_8 = findViewById(R.id.imageView8);

        //Get the TextView
        question = findViewById(R.id.tv_question);

        //Get the speak button
        speakButton = findViewById(R.id.speakButton);

        //Get points TextView
        tv_points = findViewById(R.id.tv_points);

        //Get high Score points TextView
        tv_highScore = findViewById(R.id.tv_highScore);
    }

    public void start(){

        // Get a random between 0 and images.length-1
        int randomImg1, randomImg2, randomImg3, randomImg4, randomImg5, randomImg6, randomImg7, randomImg8;
        randomImg1 = (int)(Math.random() * images.length);


        while (true) {
            randomImg2 = (int)(Math.random() * images.length);
            if (randomImg1 != randomImg2)
                break;
        }

        while (true) {
            randomImg3 = (int)(Math.random() * images.length);
            if ((randomImg3 != randomImg1) && (randomImg3 != randomImg2) )
                break;
        }

        while (true) {
            randomImg4 = (int)(Math.random() * images.length);
            if ((randomImg4 != randomImg1) && (randomImg4 != randomImg2) && (randomImg4 != randomImg3))
                break;
        }

        while (true) {
            randomImg5 = (int)(Math.random() * images.length);
            if ((randomImg5 != randomImg1) && (randomImg5 != randomImg2) && (randomImg5 != randomImg3) && (randomImg5 != randomImg4))
                break;
        }

        while (true) {
            randomImg6 = (int)(Math.random() * images.length);
            if ((randomImg6 != randomImg1) && (randomImg6 != randomImg2) && (randomImg6 != randomImg3) && (randomImg6 != randomImg4) && (randomImg6 != randomImg5))
                break;
        }

        while (true) {
            randomImg7 = (int)(Math.random() * images.length);
            if ((randomImg7 != randomImg1) && (randomImg7 != randomImg2) && (randomImg7 != randomImg3) && (randomImg7 != randomImg4) && (randomImg7 != randomImg5) && (randomImg7 != randomImg6))
                break;
        }

        while (true) {
            randomImg8 = (int)(Math.random() * images.length);
            if ((randomImg8 != randomImg1) && (randomImg8 != randomImg2) && (randomImg8 != randomImg3) && (randomImg8 != randomImg4) && (randomImg8 != randomImg5) && (randomImg8 != randomImg6)  && (randomImg8 != randomImg7))
                break;
        }

        imgName = getResourceNameFromClassByID(images[randomImg1]);
        questionText = "Find the " + capitalize(imgName);

        //Set the question
        question.setText(questionText);

        int random = (int)(Math.random() * 8);

        if(random == 0){
            // Set the images
            im_1.setBackgroundResource(images[randomImg1]);
            im_2.setBackgroundResource(images[randomImg2]);
            im_3.setBackgroundResource(images[randomImg3]);
            im_4.setBackgroundResource(images[randomImg4]);
            im_5.setBackgroundResource(images[randomImg5]);
            im_6.setBackgroundResource(images[randomImg6]);
            im_7.setBackgroundResource(images[randomImg7]);
            im_8.setBackgroundResource(images[randomImg8]);

            im_1.setTag(images[randomImg1]);
            im_2.setTag(images[randomImg2]);
            im_3.setTag(images[randomImg3]);
            im_4.setTag(images[randomImg4]);
            im_5.setTag(images[randomImg5]);
            im_6.setTag(images[randomImg6]);
            im_7.setTag(images[randomImg7]);
            im_8.setTag(images[randomImg8]);
        }
        else if (random == 1) {
            // Set the images
            im_1.setBackgroundResource(images[randomImg2]);
            im_2.setBackgroundResource(images[randomImg1]);
            im_3.setBackgroundResource(images[randomImg3]);
            im_4.setBackgroundResource(images[randomImg4]);
            im_5.setBackgroundResource(images[randomImg5]);
            im_6.setBackgroundResource(images[randomImg6]);
            im_7.setBackgroundResource(images[randomImg7]);
            im_8.setBackgroundResource(images[randomImg8]);

            im_1.setTag(images[randomImg2]);
            im_2.setTag(images[randomImg1]);
            im_3.setTag(images[randomImg3]);
            im_4.setTag(images[randomImg4]);
            im_5.setTag(images[randomImg5]);
            im_6.setTag(images[randomImg6]);
            im_7.setTag(images[randomImg7]);
            im_8.setTag(images[randomImg8]);
        }
        else if (random == 2) {
            // Set the images
            im_1.setBackgroundResource(images[randomImg3]);
            im_2.setBackgroundResource(images[randomImg2]);
            im_3.setBackgroundResource(images[randomImg1]);
            im_4.setBackgroundResource(images[randomImg4]);
            im_5.setBackgroundResource(images[randomImg5]);
            im_6.setBackgroundResource(images[randomImg6]);
            im_7.setBackgroundResource(images[randomImg7]);
            im_8.setBackgroundResource(images[randomImg8]);

            im_1.setTag(images[randomImg3]);
            im_2.setTag(images[randomImg2]);
            im_3.setTag(images[randomImg1]);
            im_4.setTag(images[randomImg4]);
            im_5.setTag(images[randomImg5]);
            im_6.setTag(images[randomImg6]);
            im_7.setTag(images[randomImg7]);
            im_8.setTag(images[randomImg8]);
        }
        else if (random == 3) {
            // Set the images
            im_1.setBackgroundResource(images[randomImg4]);
            im_2.setBackgroundResource(images[randomImg2]);
            im_3.setBackgroundResource(images[randomImg3]);
            im_4.setBackgroundResource(images[randomImg1]);
            im_5.setBackgroundResource(images[randomImg5]);
            im_6.setBackgroundResource(images[randomImg6]);
            im_7.setBackgroundResource(images[randomImg7]);
            im_8.setBackgroundResource(images[randomImg8]);

            im_1.setTag(images[randomImg4]);
            im_2.setTag(images[randomImg2]);
            im_3.setTag(images[randomImg3]);
            im_4.setTag(images[randomImg1]);
            im_5.setTag(images[randomImg5]);
            im_6.setTag(images[randomImg6]);
            im_7.setTag(images[randomImg7]);
            im_8.setTag(images[randomImg8]);
        }
        else if (random == 4) {
            // Set the images
            im_1.setBackgroundResource(images[randomImg5]);
            im_2.setBackgroundResource(images[randomImg2]);
            im_3.setBackgroundResource(images[randomImg3]);
            im_4.setBackgroundResource(images[randomImg4]);
            im_5.setBackgroundResource(images[randomImg1]);
            im_6.setBackgroundResource(images[randomImg6]);
            im_7.setBackgroundResource(images[randomImg7]);
            im_8.setBackgroundResource(images[randomImg8]);

            im_1.setTag(images[randomImg5]);
            im_2.setTag(images[randomImg2]);
            im_3.setTag(images[randomImg3]);
            im_4.setTag(images[randomImg4]);
            im_5.setTag(images[randomImg1]);
            im_6.setTag(images[randomImg6]);
            im_7.setTag(images[randomImg7]);
            im_8.setTag(images[randomImg8]);
        }
        else if(random == 5){
            // Set the images
            im_1.setBackgroundResource(images[randomImg6]);
            im_2.setBackgroundResource(images[randomImg2]);
            im_3.setBackgroundResource(images[randomImg3]);
            im_4.setBackgroundResource(images[randomImg4]);
            im_5.setBackgroundResource(images[randomImg5]);
            im_6.setBackgroundResource(images[randomImg1]);
            im_7.setBackgroundResource(images[randomImg7]);
            im_8.setBackgroundResource(images[randomImg8]);

            im_1.setTag(images[randomImg6]);
            im_2.setTag(images[randomImg2]);
            im_3.setTag(images[randomImg3]);
            im_4.setTag(images[randomImg4]);
            im_5.setTag(images[randomImg5]);
            im_6.setTag(images[randomImg1]);
            im_7.setTag(images[randomImg7]);
            im_8.setTag(images[randomImg8]);
        }
        else if(random == 6){
            // Set the images
            im_1.setBackgroundResource(images[randomImg7]);
            im_2.setBackgroundResource(images[randomImg2]);
            im_3.setBackgroundResource(images[randomImg3]);
            im_4.setBackgroundResource(images[randomImg4]);
            im_5.setBackgroundResource(images[randomImg5]);
            im_6.setBackgroundResource(images[randomImg6]);
            im_7.setBackgroundResource(images[randomImg1]);
            im_8.setBackgroundResource(images[randomImg8]);

            im_1.setTag(images[randomImg7]);
            im_2.setTag(images[randomImg2]);
            im_3.setTag(images[randomImg3]);
            im_4.setTag(images[randomImg4]);
            im_5.setTag(images[randomImg5]);
            im_6.setTag(images[randomImg6]);
            im_7.setTag(images[randomImg1]);
            im_8.setTag(images[randomImg8]);
        }
        else{
            // Set the images
            im_1.setBackgroundResource(images[randomImg8]);
            im_2.setBackgroundResource(images[randomImg2]);
            im_3.setBackgroundResource(images[randomImg3]);
            im_4.setBackgroundResource(images[randomImg4]);
            im_5.setBackgroundResource(images[randomImg5]);
            im_6.setBackgroundResource(images[randomImg6]);
            im_7.setBackgroundResource(images[randomImg7]);
            im_8.setBackgroundResource(images[randomImg1]);

            im_1.setTag(images[randomImg8]);
            im_2.setTag(images[randomImg2]);
            im_3.setTag(images[randomImg3]);
            im_4.setTag(images[randomImg4]);
            im_5.setTag(images[randomImg5]);
            im_6.setTag(images[randomImg6]);
            im_7.setTag(images[randomImg7]);
            im_8.setTag(images[randomImg1]);
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
        checkStatus(im_1);
    }

    public void showMessage2(View view) {
        checkStatus(im_2);
    }

    public void showMessage3(View view) {
        checkStatus(im_3);
    }

    public void showMessage4(View view) {
        checkStatus(im_4);
    }

    public void showMessage5(View view) {
        checkStatus(im_5);
    }

    public void showMessage6(View view) {
        checkStatus(im_6);
    }

    public void showMessage7(View view) {
        checkStatus(im_7);
    }

    public void showMessage8(View view) {
        checkStatus(im_8);
    }

    private void checkStatus(ImageView im){
        if (checkAnswer(im)){
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

            points = 120;
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
                Intent nextLevel = new Intent(Level5Activity.this, lastActivity.class);

                SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("lastScore", points + highScore);
                editor.apply();

                startActivity(nextLevel);
                finish();
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