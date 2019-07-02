package com.example.tabbedactivityapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class FragmentOther extends Fragment {

    public static final String MESSAGE_IDENTIFIER = ".com.example.tabbedactivityapp";
    View view;
    DatabaseHelper myDb;
    List<String> categoriesList = new ArrayList<String>();
    String[] categoriesArray;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_other_layout, container, false);

        OtherOperations();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showAllCategories();
    }

    private void OtherOperations() {
        myDb = new DatabaseHelper(getActivity());
        showAllCategories();
        addCategoryEvent();
        deleteCategoryEvent();
        categoriesClickEvent();

    }

    private void showAllCategories() {
        Cursor result = myDb.viewAll(DatabaseHelper.TABLE_CATEGORIES);
        if (result.getCount() == 0) {
            Toast.makeText(getActivity(), "No data to show.", Toast.LENGTH_LONG).show();
            return;
        }

        if (categoriesList.size() > 0)
            categoriesList.removeAll(categoriesList);

        result.moveToFirst();
        for (int i = 0; i < result.getCount(); ++i) {
            categoriesList.add(result.getString(1));
            result.moveToNext();
        }

        categoriesArray = categoriesList.toArray(new String[categoriesList.size()]);

        setListViewAdapter();
        setSpinnerAdapter();
    }

    private void setListViewAdapter() {
        ListView categories = view.findViewById(R.id.categoriesListView);
        categories.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categoriesArray));
    }

    private void setSpinnerAdapter() {
        Spinner deleteCategorySpinner = view.findViewById(R.id.deleteCategorySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categoriesArray);
        deleteCategorySpinner.setAdapter(adapter);
    }

    private void addCategoryEvent() {
        Button addCategoryButton = view.findViewById(R.id.addCategoryButton);
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText categoryEditText = view.findViewById(R.id.categoryEditText);
                String newCategory = categoryEditText.getText().toString();
                myDb.addData(DatabaseHelper.TABLE_CATEGORIES, newCategory);

                EditText[] ediTexts = {categoryEditText};
                clearEditText(ediTexts);

                showAllCategories();
            }
        });
    }

    private void deleteCategoryEvent() {
        Button deleteCategoryButton = view.findViewById(R.id.deleteCategoryButton);
        deleteCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner deleteCategorySpinner = view.findViewById(R.id.deleteCategorySpinner);
                String selectedCategory = deleteCategorySpinner.getSelectedItem().toString();
                myDb.deleteData(DatabaseHelper.TABLE_CATEGORIES, DatabaseHelper.COL_CATEGORY_NAME, selectedCategory);

                showAllCategories();
            }
        });
    }

    private void categoriesClickEvent() {
        final ListView categories = view.findViewById(R.id.categoriesListView);
        categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CategoryDetail.class);
                String message = categories.getItemAtPosition(position).toString();
                intent.putExtra(MESSAGE_IDENTIFIER, message);
                startActivity(intent);
            }
        });
    }

    private void clearEditText(EditText[] editTexts) {
        for (EditText editText : editTexts) {
            editText.setText("");
        }
    }
}
