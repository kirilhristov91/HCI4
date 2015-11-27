package com.hci4;

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
    String[] from;
    public CustomHistoryAdapter(Context context, String[] from, ArrayList<History> items) {
        super(context, R.layout.custom_row, from);
        historyItems = items;
        this.from = from;
    }

    static class ViewHolderItem{
        TextView textview8;
        TextView textview10;
        TextView textview12;
        TextView textview14;
        TextView textViewHistoryCal;
        ImageView imageViewRow;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItem viewHolder;

        if(convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_row, parent, false);

            viewHolder = new ViewHolderItem();

            viewHolder.textview8 = (TextView) convertView.findViewById(R.id.textView8);
            viewHolder.textview10 = (TextView) convertView.findViewById(R.id.textView10);
            viewHolder.textview12 = (TextView) convertView.findViewById(R.id.textView12);
            viewHolder.textview14 = (TextView) convertView.findViewById(R.id.textView14);
            viewHolder.textViewHistoryCal = (TextView) convertView.findViewById(R.id.textViewHistoryCal);
            viewHolder.imageViewRow = (ImageView) convertView.findViewById(R.id.imageViewRow);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        String fromDestionation = from[position];

        if(fromDestionation != null){
            viewHolder.textview8.setText(historyItems.get(position).getFrom());
            viewHolder.textview10.setText(historyItems.get(position).getDestination());
            viewHolder.textview14.setText(historyItems.get(position).getDate());

            if (historyItems.get(position).getChoice().equals("car")) {
                viewHolder.imageViewRow.setImageResource(R.drawable.car);
                double cost = Math.random() * 11;
                String text = String.format("%.2f", cost) + "£";
                viewHolder.textview12.setText(text);
                viewHolder.textViewHistoryCal.setText("0 Cal burned");

            } else{
                int cost = (int)(Math.random() * 3);
                String text = cost + "£";
                viewHolder.textview12.setText(text);
                viewHolder.imageViewRow.setImageResource(R.drawable.bike);
                int calories = (int)(Math.random()*900)+1;
                viewHolder.textViewHistoryCal.setText(calories+" Cal burned");
            }
        }

        return convertView;
    }
}
