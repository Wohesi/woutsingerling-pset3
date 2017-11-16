package com.example.gebruiker.restaurant;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class basket_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_screen);


        // with a lot of help from the following stack-overflow:
        // https://stackoverflow.com/questions/7175880/how-can-i-store-an-integer-array-in-sharedpreferences

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        String item = settings.getString("order", "");

        ArrayList<String> list = new ArrayList<String>();
        try {
            JSONArray order = new JSONArray(item);

            for (int i=0; i<order.length(); i++) {
                try {
                    //result[i] = order.getInt(i);
                    list.add(order.getString(i));
                    System.out.println("dfsafsdfadsf"+list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        ArrayAdapter<String> thisAdapter =
                new ArrayAdapter<String>(
                        getApplicationContext(),
                        R.layout.row_layout,
                        list
                );

        // Assign adapter to ListView
        ListView myList = (ListView) findViewById(R.id.mylist);
        myList.setAdapter(thisAdapter);


        }

    }
