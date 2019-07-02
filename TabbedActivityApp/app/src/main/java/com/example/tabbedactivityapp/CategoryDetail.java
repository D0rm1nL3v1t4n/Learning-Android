package com.example.tabbedactivityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CategoryDetail extends AppCompatActivity {

    private static String categoryName;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDb = new DatabaseHelper(this);

        getCategoryName();
        toggleEdit(false);
        editCategoryClickEvent();
        cancelEditClickEvent();
        saveCategoryClickEvent();
        deleteCategoryClickEvent();
    }

    private void toggleEdit(boolean editState) {
        EditText categoryEditText = findViewById(R.id.categoryEditText);
        Button cancelButton = findViewById(R.id.cancelButton);
        Button saveButton = findViewById(R.id.saveButton);
        Button editButton = findViewById(R.id.editButton);

        if (editState == false) {
            categoryEditText.setEnabled(false);
            cancelButton.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.VISIBLE);
            categoryEditText.setText(categoryName);
        }
        else {
            categoryEditText.setEnabled(true);
            cancelButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.INVISIBLE);
        }
    }

    private void getCategoryName() {
        Intent intent = getIntent();
        String message = intent.getStringExtra(FragmentOther.MESSAGE_IDENTIFIER);
        categoryName = message;
    }

    private void editCategoryClickEvent() {
        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEdit(true);
            }
        });
    }

    private void cancelEditClickEvent() {
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEdit(false);
            }
        });
    }

    private void saveCategoryClickEvent() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText categoryEditText = findViewById(R.id.categoryEditText);
                String newCategoryName = categoryEditText.getText().toString();

                myDb.updateData(DatabaseHelper.TABLE_CATEGORIES, DatabaseHelper.COL_CATEGORY_NAME, categoryName, DatabaseHelper.COL_CATEGORY_NAME, newCategoryName);
                categoryName = newCategoryName;
                toggleEdit(false);
            }
        });
    }

    private void deleteCategoryClickEvent() {
        Button deleteCategoryButton = findViewById(R.id.deleteCategoryButton);
        deleteCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteData(DatabaseHelper.TABLE_CATEGORIES, DatabaseHelper.COL_CATEGORY_NAME, categoryName);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
