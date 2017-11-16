package com.example.gebruiker.restaurant;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class basket_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_screen);

        SharedPreferences settings = this.getSharedPreferences("order", this.MODE_PRIVATE );


        ArrayList<String> list = new ArrayList<String>();


        try {
            JSONArray order = new JSONArray(settings);

            for (int i=0; i<order.length(); i++) {
                try {
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


    /*

        Map<String,?> keys = prefs.getAll();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+keys);


        List<String> values = new ArrayList<String>();
        //JSONArray values = new JSONArray(settings.getString("order", none));


        for(Map.Entry<String,?> entry : keys.entrySet()){
            values.add(entry.getValue().toString());
        }


     */
