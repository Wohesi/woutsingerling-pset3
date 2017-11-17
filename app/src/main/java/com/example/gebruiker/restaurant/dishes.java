package com.example.gebruiker.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class dishes extends AppCompatActivity {

    // added basket to toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.basket_icon, menu);
        return true;
    }

    public void basket(MenuItem item) {
        Intent basket_screen = new Intent(this, basket_screen.class);
        startActivity(basket_screen);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appetizers);


        // get the story
        Intent intent = getIntent();
        final String recievedcategory = intent.getStringExtra("category");

        final TextView mTextView = (TextView) findViewById(R.id.text);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://resto.mprog.nl/menu";

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
                            jsonArray = jsonObject.getJSONArray("items");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ArrayList<String> list = new ArrayList<String>();
                        for (int i=0; i<jsonArray.length(); i++) {
                            try {
                                if (jsonArray.getJSONObject(i).optString("category").equals(recievedcategory)) {
                                    list.add(jsonArray.getJSONObject(i).optString("name"));
                            }
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

                        try {
                            myList.setOnItemClickListener(

                                    new AdapterView.OnItemClickListener() {

                                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(dishes.this);
                                        String item = settings.getString("order", "");

                                        JSONArray order = new JSONArray(item);


                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                            String categoryPicked = "You added " +
                                                    String.valueOf(adapterView.getItemAtPosition(position)) + "to your order";
                                            Toast.makeText(dishes.this, categoryPicked, Toast.LENGTH_SHORT).show();

                                            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(dishes.this);
                                            SharedPreferences.Editor editor = settings.edit();

                                            order.put(String.valueOf(adapterView.getItemAtPosition(position)));
                                            editor.putString("order", String.valueOf(order));
                                            editor.commit();

    //
                                        }

                                    });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }




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





}
