package com.example.tabbedactivityapp.ui.main;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

public class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float progressValue;

    public ProgressBarAnimation(ProgressBar progressBar, float progressValue) {
        super();
        this.progressBar = progressBar;
        this.progressValue = progressValue;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = progressValue * interpolatedTime;
        progressBar.setProgress((int) value);
    }
}
