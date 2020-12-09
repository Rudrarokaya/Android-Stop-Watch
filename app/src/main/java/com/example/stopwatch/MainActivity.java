package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.stopwatch.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public TextView progressText;
    private ProgressBar progressBar;
    public Button btnStart, btnReset;
    public boolean running;
    public int seconds = 0;
    public  boolean was_running;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            was_running = savedInstanceState.getBoolean("was_running");
        }
        runTimerClock();
         btnReset = findViewById(R.id.btn_reset);
         btnStart = findViewById(R.id.btn_start);

        progressBar = findViewById(R.id.progress_bar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (was_running){
            running = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        was_running = running;
        running = false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("was_running", was_running);
    }

    public void runTimerClock(){
        progressText = findViewById(R.id.progress_text);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int sec = seconds % 60;
                String time = String.format(Locale.getDefault(), "%d : %02d : %02d ", hours, minutes, sec);
                progressText.setText(time);
                if (running){
                    seconds++;

                    if (seconds == progressBar.getMax()){
                        progressBar.setProgress(0);
                    }
                    else
                        progressBar.setProgress(seconds);
                }

                handler.postDelayed(this,1000);
            }
        });
    }

    public void onClickStart(View view) {
        if (!running){
            btnStart.setText("Pause");
            running = true;
        }
        else{
            btnStart.setText("Start");
            running = false;
        }
    }

    public void onClickReset(View view) {
        if (running || !running){
            running = false;
            seconds = 0;
            progressBar.setProgress(0);

            btnStart.setText("Start");
        }
    }
}