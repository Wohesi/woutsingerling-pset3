package com.example.gebruiker.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView mTextView = (TextView) findViewById(R.id.text);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://resto.mprog.nl/categories";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    private JSONObject jsonObject = null;
                    private JSONArray jsonArray = null;

                    @Override
                    public void onResponse(JSONObject response) {

                        // https://stackoverflow.com/questions/3395729/convert-json-array-to-normal-java-array
                        try {
                            jsonObject = new JSONObject(response.toString());
                            jsonArray = jsonObject.getJSONArray("categories");
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

                        ArrayAdapter<String> myAdapter =
                                new ArrayAdapter<String>(
                                        getApplicationContext(),
                                        android.R.layout.simple_list_item_1,
                                        list
                                );
                        ListView myList = (ListView) findViewById(R.id.mylist);
                        myList.setAdapter(myAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR!!!!!: "+error);
                error.printStackTrace();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }


    public void appetizers(View view) {
        Intent storyScreen = new Intent(this, appetizers.class);
        startActivity(storyScreen);
        finish();

    }
}
