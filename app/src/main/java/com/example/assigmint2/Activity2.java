package com.example.assigmint2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Activity2 extends AppCompatActivity {
    private EditText editTextInput;
    private TextView textveiwTimer;
    private Button buttonSetTime;
    private Button buttonStart;
    private CountDownTimer Timer;
    private boolean isTimerRunning;
    private long startTime;
    private long leftTime;
    private long EndTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        textveiwTimer = findViewById(R.id.timwe_textv);
        editTextInput = findViewById(R.id.etext_input);
        buttonSetTime = findViewById(R.id.set_btn);
        buttonStart = findViewById(R.id.start_btn);

    }

    public void buttonSetOnClick(View view) {
        String input = editTextInput.getText().toString();
        long timeInMilliS = Long.parseLong(input) * 60000;
        setTime(timeInMilliS);
        editTextInput.setText("");
    }

    private void setTime(long timeInMilliS) {
        startTime = timeInMilliS;
        resetTimer();
        closeKeyboard();
    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodM = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodM.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void resetTimer() {
        leftTime = startTime;
        updateTimer();
        updateStartButton();
    }

    private void updateTimer() {
        int seconds = (int) (leftTime / 1000) % 60;
        int minutes = (int) ((leftTime / 1000) % 3600) / 60;
        int hours = (int) (leftTime / 1000) / 3600;

        String timeLeftInterface;
        if (hours < 0) {
            timeLeftInterface = String.format(Locale.getDefault() , "%02d:%02d", minutes, seconds);

        } else {
            timeLeftInterface = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);

        }
        textveiwTimer.setText(timeLeftInterface);
    }
    private void updateStartButton() {
        if (isTimerRunning == false) {
            editTextInput.setVisibility(View.VISIBLE);
            buttonSetTime.setVisibility(View.VISIBLE);
            buttonStart.setText("Start");

        } else {
            editTextInput.setVisibility(View.INVISIBLE);
            buttonSetTime.setVisibility(View.INVISIBLE);
            buttonStart.setText("Pause");

            if (leftTime < 1000) {

                buttonStart.setVisibility(View.INVISIBLE);
            } else {

                buttonStart.setVisibility(View.VISIBLE);
            }

        }
    }

    public void buttonstartOnClick(View view) {
        if (isTimerRunning) {
            pauseTimer();
        } else {
            startTimer();
        }
    }

    private void pauseTimer() {
        Timer.cancel();
        isTimerRunning = false;
        updateStartButton();
    }
    private void startTimer() {
        EndTime = System.currentTimeMillis() + leftTime;
        Timer = new CountDownTimer(leftTime, 1000) {
            @Override
            public void onTick(long timeUntilFinished) {
                leftTime = timeUntilFinished;
                updateTimer();
            }
            @Override
            public void onFinish() {
                isTimerRunning = false;
                updateStartButton();
            }
        }.start();
        isTimerRunning = true;
        updateStartButton();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("startTime", startTime);
        editor.putLong("leftTime", leftTime);
        editor.putBoolean("isTimerRunning", isTimerRunning);
        editor.putLong("endTime", EndTime);
        editor.apply();
        if (Timer != null) {
            Timer.cancel();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        startTime = prefs.getLong("startTimer", 600000);
        leftTime = prefs.getLong("timeLeft", startTime);
        isTimerRunning = prefs.getBoolean("timerRunning", false);
        updateTimer();
        updateStartButton();
        if (isTimerRunning) {
            EndTime = prefs.getLong("endTime", 0);
            leftTime = EndTime - System.currentTimeMillis();
            if (leftTime < 0) {
                leftTime = 0;
                isTimerRunning = false;
                updateTimer();
                updateStartButton();
            } else {
                startTimer();
            }
        }
    }

}