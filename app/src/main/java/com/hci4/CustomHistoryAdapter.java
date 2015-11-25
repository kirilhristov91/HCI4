package com.hci4;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.ArrayList;

class CustomHistoryAdapter extends ArrayAdapter<History> {

    ArrayList<History> historyItems;
    LayoutInflater mInflater ;

    Context context;

    int layoutResourceId;


    public CustomHistoryAdapter(Context context, ArrayList<History> items) {
        super(context, R.layout.custom_row, items);
        historyItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        TextView textView8 = (TextView) convertView.findViewById(R.id.textView8);
        TextView textView10 = (TextView) convertView.findViewById(R.id.textView10);
        TextView textView12 = (TextView) convertView.findViewById(R.id.textView12);
        TextView textView14 = (TextView) convertView.findViewById(R.id.textView14);
        ImageView imageViewRow = (ImageView) customView.findViewById(R.id.imageViewRow);


        textView8.setText(historyItems.get(position).getFrom());
        textView10.setText(historyItems.get(position).getDestination());

        double cost = Math.random()*11;
        String text = String.format("%.2f", cost)+"£";
        textView12.setText(text);

        textView14.setText(historyItems.get(position).getDate());

        if (position == 0) {
            imageViewRow.setImageResource(R.drawable.car);
        }
        else imageViewRow.setImageResource(R.drawable.bike);

        return customView;
    }
}
