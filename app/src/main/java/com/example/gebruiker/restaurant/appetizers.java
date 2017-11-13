package com.example.gebruiker.restaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class appetizers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appetizers);

        final TextView mTextView = (TextView) findViewById(R.id.text);

        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://testo.mprog.nl/menu";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                /*
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    private JSONObject jsonObject = null;
                    private JSONArray jsonArray = null;

                    @Override
                    public void onResponse(JSONObject response) {
                        // https://stackoverflow.com/questions/3395729/convert-json-array-to-normal-java-array
                        try {
                            jsonObject = new JSONObject(response.toString());
                            jsonArray = jsonObject.getJSONArray("appetizers");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ArrayList<String> list = new ArrayList<String>();
                        for (int i=0; i<jsonArray.length(); i++) {
                            try {
                                list.add(jsonArray.getString(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ArrayAdapter<String> thisAdapter =
                                new ArrayAdapter<String>(
                                        getApplicationContext(),
                                        R.layout.row_layout,
                                        list
                                );
                        ListView myList = (ListView) findViewById(R.id.mylist);
                        myList.setAdapter(thisAdapter);

                    }
                }

        */ )
    }
}


