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

class CustomHistoryAdapter extends ArrayAdapter<String> {

    ArrayList<History> historyItems;
    public CustomHistoryAdapter(Context context, String[] from, ArrayList<History> items) {
        super(context, R.layout.custom_row, from);
        historyItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        TextView textView8 = (TextView) customView.findViewById(R.id.textView8);
        TextView textView10 = (TextView) customView.findViewById(R.id.textView10);
        TextView textView12 = (TextView) customView.findViewById(R.id.textView12);
        TextView textView14 = (TextView) customView.findViewById(R.id.textView14);
        ImageView imageViewRow = (ImageView) customView.findViewById(R.id.imageViewRow);


        textView8.setText(historyItems.get(position).getFrom());
        textView10.setText(historyItems.get(position).getDestination());

        double cost = Math.random()*11;
        String text = String.format("%.2f", cost)+"£";
        textView12.setText(text);

        textView14.setText(historyItems.get(position).getDate());

        if (historyItems.get(position).getChoice().equals("car")) {
            imageViewRow.setImageResource(R.drawable.car);
        }
        else imageViewRow.setImageResource(R.drawable.bike);

        return customView;
    }
}
