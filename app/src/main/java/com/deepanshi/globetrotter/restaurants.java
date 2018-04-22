package com.deepanshi.globetrotter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class restaurants extends AppCompatActivity  {
    private static final int PERMISSION_REQUEST_CODE = 100;
    Double clong = 0.0, clat = 0.0;
    LocationManager locate;
    LocationListener locationListener;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> addr = new ArrayList<>();
    private ArrayList<String> votes = new ArrayList<>();
    private ArrayList<String> votes2 = new ArrayList<>();
    private ArrayList<String> cuisines = new ArrayList<>();
    private ArrayList<String> cost = new ArrayList<>();
    private ArrayList<String> curr = new ArrayList<>();


    private ArrayList<String> photo = new ArrayList<>();
    restrolist adapter = new restrolist(restaurants.this,names, addr, votes, photo,votes2,cuisines,cost,curr);
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurants);
//        tv1 = (TextView) findViewById(R.id.textView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        try {
            locate = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toast.makeText(getApplicationContext(),"Please Wait...And make sure that your GPS and INTERNET is ON",Toast.LENGTH_LONG).show();
        locate = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                clat = location.getLatitude();
                clong = location.getLongitude();
                Toast.makeText(getApplicationContext(),"latitude-"+clat+" and longitude"+clong,Toast.LENGTH_LONG).show();
                new restaurants.RetrieveFeedTask().execute();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locate.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 50, locationListener);
        locate.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                5000, 50, locationListener);
        try {
            synchronized (this) {
                wait(3000);
        recyclerView.setAdapter(adapter);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {
            // Do some validation here
            try {
                URL url = new URL("https://developers.zomato.com/api/v2.1/search?count=15&lat=" + clat + "&lon=" + clong + "&radius=5000&sort=real_distance&order=asc&apikey=85867108041a209beb00e7a238045192");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONArray jsonArray = jsonObj.getJSONArray("restaurants");
                Log.d("jsonarray", ">" + jsonArray);
                int length = jsonArray.length();
                Log.d("length", "" + length);
                for (int i = 0; i < length; i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    JSONObject json2 = json.getJSONObject("restaurant");
                    Log.i("res", "" + json2);
                    JSONObject json3 = json2.getJSONObject("location");
                    String address = json3.getString("address");
                    addr.add(address);
                    String name = json2.getString("name");
                    names.add(name);
                    String photos = json2.getString("featured_image");
                    photo.add(photos);
                    String cuisine = json2.getString("cuisines");
cuisines.add(cuisine);
                    String costs = json2.getString("average_cost_for_two");
                    cost.add(costs);
                    String currency = json2.getString("currency");
                    curr.add(currency);
                    JSONObject json4 = json2.getJSONObject("user_rating");
                    String vote = json4.getString("aggregate_rating");
                    String vote2 = json4.getString("rating_text");

                    votes.add(vote);
                    votes2.add(vote2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}