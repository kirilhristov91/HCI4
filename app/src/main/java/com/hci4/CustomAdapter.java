package com.hci4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

class CustomAdapter extends ArrayAdapter<String> {

    public CustomAdapter(Context context, String[] routes) {
        super(context, R.layout.custom_row, routes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        String singleRouteItem = getItem(position);
        TextView textViewRow = (TextView) customView.findViewById(R.id.textViewRow);
        ImageView imageViewRow = (ImageView) customView.findViewById(R.id.imageViewRow);

        textViewRow.setText(singleRouteItem);
        if (position == 0) {
            imageViewRow.setImageResource(R.drawable.car);
        }
        else imageViewRow.setImageResource(R.drawable.bike);

        return customView;
    }
}
