package com.example.footballfans.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.footballfans.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        splashImage = findViewById(R.id.splash);

        int[] ids = new int[]{R.mipmap.splash
                ,R.mipmap.splash2
                ,R.mipmap.splash3
                ,R.mipmap.splash5};

        Random random = new Random();
        int r = random.nextInt(ids.length);
        this.splashImage.setImageDrawable(getResources().getDrawable(ids[r]));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }
        },2500);

    }
}
