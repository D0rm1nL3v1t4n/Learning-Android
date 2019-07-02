package com.example.tabbedactivityapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentExpense extends Fragment {

    //String[] fruit;
    //String[] prices;
    View view;
    DatabaseHelper myDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_expense_layout, container, false);

        Operations();

        return view;
    }

    private void Operations() {
        myDb = new DatabaseHelper(getActivity());

        setWriteButtonClickEvent();
        setReadButtonClickEvent();

        setCurrentBudget();
        setBudgetClickEvent();
    }

    private void setWriteButtonClickEvent() {
        Button writeDataButton = view.findViewById(R.id.writeDataButton);
        final EditText budgetInputEditText = view.findViewById(R.id.budgetInputEditText);
        final EditText savingsInputEditText = view.findViewById(R.id.savingsInputEditText);

        writeDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float budgetEntered = Float.parseFloat(budgetInputEditText.getText().toString());
                Float savingsEntered = Float.parseFloat(savingsInputEditText.getText().toString());

                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("Budget", budgetEntered);
                editor.putFloat("Savings", savingsEntered);
                editor.commit();
            }
        });
    }

    private void setReadButtonClickEvent() {
        Button readDataButton = view.findViewById(R.id.readDataButton);

        readDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                float budget = sharedPreferences.getFloat("Budget", 0);
                float savings = sharedPreferences.getFloat("Savings", 0);

                String toastMessage = "Budget: " + budget + "\nSavings: " + savings;
                Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setCurrentBudget() {
        TextView currentBudgetTextView = view.findViewById(R.id.currentBudgetTextView);
        String maxId = myDb.getMaxId(DatabaseHelper.TABLE_BUDGET);

        if (maxId == null) {
            currentBudgetTextView.setText("No budget found. Set your budget.");
            return;
        }
        Cursor result = myDb.searchData(DatabaseHelper.TABLE_BUDGET, DatabaseHelper.COL_BUDGET_ID, maxId);

        result.moveToFirst();
        currentBudgetTextView.setText("Current budget: £"+result.getString(1));
    }

    private void setBudgetClickEvent() {
        Button setBudgetButton = view.findViewById(R.id.setBudgetButton);
        setBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText budgetEditText = view.findViewById(R.id.budgetEditText);

                myDb.addData(DatabaseHelper.TABLE_BUDGET, budgetEditText.getText().toString());
                Toast.makeText(getActivity(), "Budget Set.", Toast.LENGTH_LONG).show();

                TextView currentBudgetTextView = view.findViewById(R.id.currentBudgetTextView);
                currentBudgetTextView.setText("Current Budget: £"+budgetEditText.getText());

                budgetEditText.setText("");


            }
        });


    }

//    private void setDefaultArrayValues() {
//        fruit = getResources().getStringArray(R.array.fruits);
//        prices = getResources().getStringArray(R.array.prices);
//    }
//
//    private void setItemAdapter() {
//        ListView fruitListView = view.findViewById(R.id.fruitListView);
//
//        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), fruit, prices);
//        fruitListView.setAdapter(itemAdapter);
//    }
//
//    private void addFruitClickEvent() {
//        Button addFruit = view.findViewById(R.id.addButton);
//        addFruit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText fruitName = view.findViewById(R.id.fruit);
//                EditText fruitPrice = view.findViewById(R.id.price);
//                fruit = addItemToStringArray(fruit, fruitName.getText().toString());
//                prices = addItemToStringArray(prices, fruitPrice.getText().toString());
//                setItemAdapter();
//                EditText[] editTexts = {fruitName, fruitPrice};
//                clearEditText(editTexts);
//            }
//        });
//    }
//
//    private String[] addItemToStringArray(String[] array, String newItem) {
//        List<String> list;
//        list = Arrays.asList(array);
//        ArrayList<String> arrayList = new ArrayList<>(list);
//        arrayList.add(newItem);
//        array = arrayList.toArray(new String[list.size()]);
//        return array;
//    }
//
//    private void clearEditText(EditText[] editTexts) {
//        for (EditText editText : editTexts) {
//            editText.setText("");
//        }
//    }
}
