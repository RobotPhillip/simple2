package com.robotandpencils.simple2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // Make HTTPS requests to this URL for map info
    private String mapInfoUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=51.042823,-114.0948155";
    // JSON object that contains map information
    JSONObject mapInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button doButton = (Button) findViewById(R.id.getMap);
        doButton.setClickable(true);
        doButton.setEnabled(true);
        doButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // make HTTP request to retrieve the weather
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mapInfoUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mapInfo = (JSONObject) response.getJSONArray("results").get(0);
                            String location = mapInfo.getString("formatted_address");
                            String status = response.getString("status");
                            Toast.makeText(getApplicationContext(), "found map", Toast.LENGTH_SHORT).show();

                            EditText locationText = (EditText) findViewById(R.id.valueMapLocation);
                            locationText.setText(location);

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error, try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("tag", "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Error while loading ", Toast.LENGTH_SHORT).show();
                    }
                });

                // Add request to request queue
                AppController.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
