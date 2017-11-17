package com.example.gebruiker.restaurant;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import org.json.JSONTokener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class basket_screen extends AppCompatActivity {

        private JSONArray jsonArray;
        private JSONArray jsonResponse;
        private ArrayList<String> finalList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_screen);

    // Assign adapter to ListView
        final ListView myList = (ListView) findViewById(R.id.mylist);

        // Getting list of orders
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences settings = this.getSharedPreferences("order", MODE_PRIVATE);
        String item = settings.getString("order", "");
        Log.i("test", item);

        // Connection with API
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://resto.mprog.nl/menu";

        // Instantiate new list
        finalList = new ArrayList<>();

        // with a lot of help from the following stack-overflow:
        // https://stackoverflow.com/questions/7175880/how-can-i-store-an-integer-array-in-sharedpreferences

        // Setting a list of all the orders
        final ArrayList<String> list = new ArrayList<String>();
        try {
            JSONArray order = new JSONArray(item);
            jsonArray = order;

            for (int i=0; i<order.length(); i++) {
                try {
                    // adding orders to the list
                    list.add(order.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Creating a new list to get the prices of the orders
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    private JSONObject jsonObject = null;

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            jsonObject = new JSONObject(response.toString());
                            jsonResponse = jsonObject.getJSONArray("items");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //ArrayList<String> list = new ArrayList<String>();
                        for (int x = 0; x < jsonArray.length(); x++) {
                            for (int i = 0; i < jsonResponse.length(); i++) {
                                try {
                                    if (jsonResponse.getJSONObject(i).getString("name").equals(jsonArray.getString(x))) {
                                        //list.get(i);
                                        //String itemOrder =  list.get(i);
                                        JSONObject itemOrder = jsonResponse.getJSONObject(i);

                                        //list.add(jsonResponse.getJSONObject(i).optString("price"));
                                        String itemPrice = jsonResponse.getJSONObject(i).optString("price");
                                        String finalOrder = itemOrder.optString("name") +" "+"$"+ itemPrice;


                                        finalList.add(finalOrder);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        ArrayAdapter<String> thisAdapter =
                                new ArrayAdapter<String>(
                                        getApplicationContext(),
                                        R.layout.row_layout,
                                        finalList
                                );
                        myList.setAdapter(thisAdapter);
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);

        myList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(adapterView.getContext(), android.R.style.Theme_Material_Dialog_Alert);

                        builder.setTitle("Delete");
                        builder.setMessage("Are you sure you want to delete?");
                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                                SharedPreferences settings = getSharedPreferences("order", MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();

                                jsonArray.remove(i);

                                editor.putString("order", String.valueOf(jsonArray));
                                editor.commit();

                                // https://stackoverflow.com/questions/1397361/how-do-i-restart-an-android-activity
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);


                            }
                        });
                         builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int which) {
                                 // do nothing
                             }
                         });
                         builder.setIcon(android.R.drawable.ic_dialog_alert);
                         builder.show();

                    }
                }
        );

    }

    public void time(View view) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://resto.mprog.nl/order";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String toast = response.toString();

                        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        queue.add(jsObjRequest);

    }
}



