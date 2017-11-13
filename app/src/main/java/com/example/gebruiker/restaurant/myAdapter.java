package com.example.gebruiker.restaurant;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import  android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class myAdapter extends ArrayAdapter<String> {

    public myAdapter(Context context, String[] values) {
        super(context, R.layout.row_layout_2, values);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.row_layout_2, parent, false);

        String category = getItem(position);
        TextView theTextView = (TextView) theView.findViewById(R.id.textView1);

        theTextView.setText(category);

        ImageView theImageView = (ImageView) theView.findViewById(R.id.arrow);
        theImageView.setImageResource(R.drawable.ic_arrow_forward_black_24dp);

        return theView;
    }
}