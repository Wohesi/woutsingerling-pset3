package com.example.gebruiker.restaurant;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class basket_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_screen);

            SharedPreferences prefs = getSharedPreferences("settings",MODE_PRIVATE);
            //System.out.println("TEST!!!!!!!!!!!!!!!!!!!!!!!!"+prefs);
            //String s = prefs.getString("item", null);


        Map<String,?> keys = prefs.getAll();

        List<String> values = new ArrayList<String>();


        for(Map.Entry<String,?> entry : keys.entrySet()){
            values.add(entry.getValue().toString());
        }

        ArrayAdapter<String> thisAdapter =
                new ArrayAdapter<String>(
                        getApplicationContext(),
                        R.layout.row_layout,
                        values
                );



        // Assign adapter to ListView
        ListView myList = (ListView) findViewById(R.id.mylist);
        myList.setAdapter(thisAdapter);


        }

    }

