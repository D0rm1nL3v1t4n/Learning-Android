package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DatabaseActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        createSpinner();

        myDb = new DatabaseHelper(this);

        TextView placeholder = findViewById(R.id.dataOutput);
        placeholder.setText("");

        Button insertDataEvent = findViewById(R.id.insertData);
        insertDataEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        Button searchDataEvent = findViewById(R.id.searchData);
        searchDataEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData();
            }
        });

        Button deleteDataEvent = findViewById(R.id.deleteData);
        deleteDataEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

        Button viewAllEvent = findViewById(R.id.viewAll);
        viewAllEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllData();
            }
        });

        Button updateDataEvent = findViewById(R.id.updateData);
        updateDataEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void createSpinner() {
        Spinner updateSpinner = findViewById(R.id.updateCol);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.col_array, R.layout.my_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        updateSpinner.setAdapter(adapter);
    }

    private void insertData() {
        EditText firstNameEditText = findViewById(R.id.insertFirstName);
        EditText lastNameEditText = findViewById(R.id.insertLastName);
        EditText subjectEditText = findViewById(R.id.insertSubject);

        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String subject = subjectEditText.getText().toString();

        myDb.addData(firstName, lastName, subject);
        Toast.makeText(DatabaseActivity.this, "Insertion complete.", Toast.LENGTH_LONG).show();

        EditText[] editTexts = {firstNameEditText, lastNameEditText, subjectEditText};
        clearInputData(editTexts);
    }

    private void searchData() {
        EditText idSearch = findViewById(R.id.idSearch);
        TextView dataOutput = findViewById(R.id.dataOutput);

        String studentId = idSearch.getText().toString();

        Cursor result = myDb.searchById(studentId);
        if (result.getCount() == 0) {
            dataOutput.setText("No data found with 'Student ID' " + studentId);
            return;
        }
        result.moveToFirst();
        dataOutput.setText(result.getString(1) + ", " +result.getString(2) + ", " + result.getString(3));

        EditText[] editTexts = {idSearch};
        clearInputData(editTexts);
    }

    private void deleteData() {
        EditText idDelete = findViewById(R.id.idDelete);
        myDb.deleteById(idDelete.getText().toString());

        Toast.makeText(DatabaseActivity.this, "Deletion complete.", Toast.LENGTH_LONG).show();

        EditText[] editTexts = {idDelete};
        clearInputData(editTexts);
    }

    private void viewAllData() {
        Cursor result = myDb.viewAll();
        String message = "";
        if (result.getCount() == 0) {
            message = "No data in table to show.";
        }
        else {
            result.moveToFirst();
            for (int i = 0; i < result.getCount(); ++i) {
                message += result.getString(0) + ", " + result.getString(1) + ", " + result.getString(2) + ", " + result.getString(3) + "\n";
                result.moveToNext();
            }
        }
        TextView dataOutput = findViewById(R.id.dataOutput);
        dataOutput.setText(message);
    }

    private void updateData() {
        EditText idUpdate = findViewById(R.id.idUpdate);
        EditText newData = findViewById(R.id.newData);
        Spinner updateCol = findViewById(R.id.updateCol);

        String colName = "";

        String updateColText = updateCol.getSelectedItem().toString();
        if (updateColText.equals("First Name"))
            colName = getString(R.string.col_first_name);
        else if (updateColText.equals("Last Name"))
            colName = getString(R.string.col_last_name);
        else if (updateColText.equals("Subject"))
            colName = getString(R.string.col_subject);

        myDb.updateById(idUpdate.getText().toString(), colName, newData.getText().toString());
        Toast.makeText(DatabaseActivity.this, "Update complete.", Toast.LENGTH_LONG).show();

        EditText[] editTexts = {idUpdate, newData};
        clearInputData(editTexts);
    }

    private void clearInputData(EditText[] toClear) {
        for (EditText editText: toClear) {
            editText.setText("");
        }
    }
}
