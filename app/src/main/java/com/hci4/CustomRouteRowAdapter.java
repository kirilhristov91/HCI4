package com.hci4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomRouteRowAdapter extends ArrayAdapter<String>{

    public CustomRouteRowAdapter(Context context, String[] routes) {
        super(context, R.layout.custom_row, routes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_route_row, parent, false);

        String singleRouteItem = getItem(position);
        TextView textView2 = (TextView) customView.findViewById(R.id.textView2);
        TextView textView4 = (TextView) customView.findViewById(R.id.textView4);
        TextView textView6 = (TextView) customView.findViewById(R.id.textView6);
        TextView textViewCaloriesCount = (TextView) customView.findViewById(R.id.textViewCaloriesCount);
        ImageView imageViewRouteRow = (ImageView) customView.findViewById(R.id.imageViewRouteRow);

        String[] parts = singleRouteItem.split("-",2);

        textView2.setText(parts[0]);
        textView4.setText(parts[1]);

        if (position == 1) {
            imageViewRouteRow.setImageResource(R.drawable.car);

            double cost = (Math.random()*7)+5;
            String text = String.format("%.2f", cost)+"£";
            textView6.setText(text);
            textViewCaloriesCount.setText("0 Cal to be burned");
        }
        else {
            imageViewRouteRow.setImageResource(R.drawable.bike);
            int cost = (int)(Math.random()*2)+1;
            String text = cost+"£";
            textView6.setText(text);
            int calories = (int)(Math.random()*900)+1;
            textViewCaloriesCount.setText(calories +" Cal to be burned");
        }
        return customView;
    }
}
