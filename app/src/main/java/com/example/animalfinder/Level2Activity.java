package com.example.animalfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
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

public class Level2Activity extends AppCompatActivity {

    private TextToSpeech T2S;
    private Button speakButton;
    private String questionText;
    private TextView tv_points;
    private TextView question;
    private ImageView im_1;
    private ImageView im_2;
    private ImageView im_3;
    private final int correctSound = R.raw.correct;
    private final int wrongSound = R.raw.wrong;
    private static String imgName;
    private static int points = 15;
    private static int pointIncrease = 10;

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
        setContentView(R.layout.activity_level2);

        getLayoutObjects();

        start();
    }

    private void getLayoutObjects(){
        // Get the ImageView
        im_1 = findViewById(R.id.imageView1);
        im_2 = findViewById(R.id.imageView2);
        im_3 = (ImageView) findViewById(R.id.imageView3);

        //Get the TextView
        question = findViewById(R.id.tv_question);

        //Get the speak button
        speakButton = findViewById(R.id.speakButton);

        //Get points TextView
        tv_points = findViewById(R.id.tv_points);
    }

    public void start(){

        // Get a random between 0 and images.length-1
        int randomImg1, randomImg2, randomImg3;
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
        System.out.println("random 1: " + randomImg1);
        System.out.println("random 2: " + randomImg2);
        System.out.println("random 3: " + randomImg3);

        imgName = getResourceNameFromClassByID(images[randomImg1]);
        questionText = "Find the " + capitalize(imgName);

        //Set the question
        question.setText(questionText);

        int random = (int)(Math.random() * 3);

        if(random == 0){
            // Set the images
            im_1.setBackgroundResource(images[randomImg1]);
            im_2.setBackgroundResource(images[randomImg2]);
            im_3.setBackgroundResource(images[randomImg3]);

            im_1.setTag(images[randomImg1]);
            im_2.setTag(images[randomImg2]);
            im_3.setTag(images[randomImg3]);
        }
        else if (random == 1) {
            // Set the images
            im_1.setBackgroundResource(images[randomImg2]);
            im_2.setBackgroundResource(images[randomImg1]);
            im_3.setBackgroundResource(images[randomImg3]);

            im_1.setTag(images[randomImg2]);
            im_2.setTag(images[randomImg1]);
            im_3.setTag(images[randomImg3]);
        }
        else {
            // Set the images
            im_1.setBackgroundResource(images[randomImg3]);
            im_2.setBackgroundResource(images[randomImg2]);
            im_3.setBackgroundResource(images[randomImg1]);

            im_1.setTag(images[randomImg3]);
            im_2.setTag(images[randomImg2]);
            im_3.setTag(images[randomImg1]);
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

            points = 75;
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
                Intent nextLevel = new Intent(Level2Activity.this, Level3Activity.class);

                //send Total points value to the Next Activity
                nextLevel.putExtra("TOTAL_POINTS", points);

                startActivity(nextLevel);
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