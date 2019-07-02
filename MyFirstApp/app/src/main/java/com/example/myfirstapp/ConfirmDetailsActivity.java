package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ConfirmDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_details);


        SetInitialProgressBar();
        UseIntent();

        Button confirmDetails = findViewById(R.id.confirmDetails);
        confirmDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDetailsEvent();
            }
        });

        Button plus = findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlusEvent();
            }
        });

        Button minus = findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinusEvent();
            }
        });

        Button tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayoutIntent();
            }
        });
    }

    private void SetInitialProgressBar() {
        ProgressBar circleProgressBar = findViewById(R.id.circleProgressBar);
        circleProgressBar.setProgress(25);
        UpdateProgressInfo(25);
    }

    private void UseIntent() {
        Intent intent = getIntent();
        String userDetails = intent.getStringExtra(MainActivity.PUT_EXTRA);

        TextView userDetailsTextView = findViewById(R.id.userDetails);
        userDetailsTextView.setText(userDetails);
    }

    private void UpdateProgressInfo(int progress) {
        TextView progressBarInfo = findViewById(R.id.progressPercentage);
        progressBarInfo.setText(progress + "%");
    }


    //////////////////////////////
    // OnClick event functions: //
    //////////////////////////////

    private void ConfirmDetailsEvent() {
        ProgressBar circleProgressBar = findViewById(R.id.circleProgressBar);
        int newProgress = 50;
        circleProgressBar.setProgress(newProgress);
        UpdateProgressInfo(newProgress);
    }

    private void PlusEvent() {
        ProgressBar circleProgressBar = findViewById(R.id.circleProgressBar);
        int currentProgress = circleProgressBar.getProgress();
        int newProgress = currentProgress + 5;
        circleProgressBar.setProgress(newProgress);
        UpdateProgressInfo(newProgress);
    }

    private void MinusEvent() {
        ProgressBar circleProgressBar = findViewById(R.id.circleProgressBar);
        int currentProgress = circleProgressBar.getProgress();
        int newProgress = currentProgress - 5;
        circleProgressBar.setProgress(newProgress);
        UpdateProgressInfo(newProgress);
    }

    private void TabLayoutIntent() {
        //Intent intent = new Intent(this, /* class_name.class */);
        //startActivity(intent);
    }
}
