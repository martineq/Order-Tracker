package com.example.uriel.ordertracker.App.Model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.uriel.ordertracker.R;

/**
 * Created by Uriel on 24-May-16.
 */
public class MenuAdapter extends ArrayAdapter<String> {

    public MenuAdapter(Context context, String[] items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawer_list_item, parent, false);
        }

        TextView itemText = (TextView) convertView.findViewById(R.id.item);
        itemText.setText(item);
        if(position == 0 || position == 1){
            itemText.setBackgroundColor(Color.WHITE);
            itemText.setTextColor(Color.BLACK);
        }else{
            itemText.setTextColor(Color.WHITE);
        }
        return convertView;
    }
}
