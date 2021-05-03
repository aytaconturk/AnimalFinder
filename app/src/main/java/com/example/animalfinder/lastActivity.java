package com.example.animalfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class lastActivity extends AppCompatActivity {

    private TextView tv_highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        //Get high Score points TextView
        tv_highScore = findViewById(R.id.score);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        int highScore = preferences.getInt("lastScore", 0);
        tv_highScore.setText(String.valueOf(highScore));
    }
}