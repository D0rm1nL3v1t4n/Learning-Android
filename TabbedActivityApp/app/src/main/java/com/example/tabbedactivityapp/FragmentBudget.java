package com.example.tabbedactivityapp;

import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tabbedactivityapp.ui.main.ProgressBarAnimation;

public class FragmentBudget extends Fragment {

    DatabaseHelper myDb;
    View view;
    float budgetValue = 0;
    float expensesPercentage = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_budget_layout, container, false);

        Operations(view);

        return view;
    }

    private void Operations(View view) {
        myDb = new DatabaseHelper(getActivity());

        setInitialProgress();
        getBudgetValue();
        enterClickEvent();
    }

    private void setInitialProgress() {
        ProgressBar circleProgressBar = view.findViewById(R.id.circleProgressBar);
        circleProgressBar.setProgress(0);
    }

    private void setProgressInfo(ProgressBar circleProgressBar) {
        EditText totalExpensesEditText = view.findViewById(R.id.totalExpensesEditText);
        float totalExpensesValue = Float.parseFloat(totalExpensesEditText.getText().toString());
        float totalExpensesPercentage = totalExpensesValue*100/budgetValue;

        expensesPercentage = totalExpensesPercentage;

        int percentageValue = Math.round(totalExpensesPercentage);
        progressBarValueChangeAnimation(circleProgressBar, percentageValue);

        String percentage = expensesPercentage+"%";
        String budget = "£"+budgetValue;
        String totalExpenses = "£"+totalExpensesValue;

        TextView progressPercentageTextView = view.findViewById(R.id.progressPercentageTextView);
        TextView budgetTextView = view.findViewById(R.id.budgetTextView);
        TextView totalExpensesTextView = view.findViewById(R.id.totalExpensesTextView);

        progressPercentageTextView.setText(percentage);
        budgetTextView.setText(budget);
        totalExpensesTextView.setText(totalExpenses);

    }

    private void progressBarValueChangeAnimation(ProgressBar circleProgressBar, int progress) {
        ProgressBarAnimation animation = new ProgressBarAnimation(circleProgressBar, progress);
        int duration;
        if (progress >= 75)
            duration = 1000;
        else if (progress >= 50)
            duration = 750;
        else if (progress >= 25)
            duration = 400;
        else
            duration = 200;

        animation.setDuration(duration);
        circleProgressBar.startAnimation(animation);
    }

    private void enterClickEvent() {
        Button enterButton = view.findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar circleProgressBar = view.findViewById(R.id.circleProgressBar);
                setProgressInfo(circleProgressBar);

                ProgressBar savingsProgressBar = view.findViewById(R.id.savingsProgressBar);
                setSavingsProgress(savingsProgressBar);
            }
        });
    }

    private void setSavingsProgress(ProgressBar savingsProgressBar) {
        EditText savingsEditText = view.findViewById(R.id.savingsEditText);
        float savingsValue = Float.parseFloat(savingsEditText.getText().toString());
        float savingsPercentage = savingsValue*100/ budgetValue;
        int savingsProgress = Math.round(savingsPercentage);

        TextView savingsPercentageTextView = view.findViewById(R.id.savingsPercentageTextView);
        savingsPercentageTextView.setText(savingsPercentage + "%");

        if (savingsPercentage + expensesPercentage > 100) {
            float overflowPercentage = savingsPercentage + expensesPercentage - 100;
            int overflowProgress = Math.round(overflowPercentage);
            savingsProgress -= overflowProgress;
            if (100-expensesPercentage > 0)
                savingsPercentageTextView.setText((100-expensesPercentage) + "%");
            else {
                savingsPercentageTextView.setText("0%");
            }

        }



        savingsProgressBar.setProgress(savingsProgress);
        rotateSavingsProgressBar();
    }

    private void rotateSavingsProgressBar() {
        ProgressBar savingsProgressBar = view.findViewById(R.id.savingsProgressBar);
        int progress = savingsProgressBar.getProgress();
        savingsProgressBar.setRotation(270 - (progress*360/100));
    }

    private void getBudgetValue() {
        String maxId = myDb.getMaxId(DatabaseHelper.TABLE_BUDGET);
        if (maxId == null) {
            Toast.makeText(getActivity(), "No budget found.", Toast.LENGTH_LONG).show();
            return;
        }
        Cursor result = myDb.searchData(DatabaseHelper.TABLE_BUDGET, DatabaseHelper.COL_BUDGET_ID, maxId);
        result.moveToFirst();
        budgetValue = Float.parseFloat(result.getString(1));
    }

}
