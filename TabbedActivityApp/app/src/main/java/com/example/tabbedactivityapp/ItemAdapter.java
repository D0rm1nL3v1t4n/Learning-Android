package com.example.tabbedactivityapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {

    String[] fruit, prices;
    LayoutInflater mInflater;

    public ItemAdapter(Context context, String[] fruitArray, String[] pricesArray) {
        fruit = fruitArray;
        prices = pricesArray;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fruit.length;
    }

    @Override
    public Object getItem(int i) {
        return fruit[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.list_view_layout, null);
        TextView fruitNameTextView = v.findViewById(R.id.fruitName);
        TextView fruitPriceTextView = v.findViewById(R.id.fruitPrice);

        String fruitName = fruit[i];
        String fruitPrice = prices[i];

        fruitNameTextView.setText(fruitName);
        fruitPriceTextView.setText(fruitPrice);

        return v;
    }
}
