package com.deepanshi.globetrotter;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class interest extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;
    Double clong = 0.0, clat = 0.0;
    LocationManager locate;
    LocationListener locationListener;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> addr = new ArrayList<>();
    private ArrayList<String> votes = new ArrayList<>();
    private ArrayList<String> photos = new ArrayList<>();
    private ArrayList<String> placeIds = new ArrayList<>();

    point_of_interest adapter = new point_of_interest(interest.this,names,addr,photos,placeIds);
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
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
                new interest.RetrieveFeedTask().execute();
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
                String urlls="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+clat+","+clong+"&radius=1500&type=shopping_mall&rating&keyword=best&key=AIzaSyDcNAhFsG8olP2zpUffRJgiUJ7avMsRPCQ\n";
                URL url = new URL(urlls);
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
                JSONArray jsonArray = jsonObj.getJSONArray("results");
                Log.d("jsonarray", ">" + jsonArray);
                int length = jsonArray.length();
                Log.d("length", "" + length);
                for (int i = 0; i < length; i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    String placeid = json.getString("place_id");
                    placeIds.add(placeid);
                    String name = json.getString("name");
                    names.add(name);
//                    String photo = json.getString("icon");
//                    photos.add(photo);
                    String address = json.getString("vicinity");
                    addr.add(address);

//              JSONObject json2 = json.getJSONObject("photos");
//         String photo = json2.getString("photo_reference");
//                    photos.add("https://maps.googleapis.com/maps/api/place/photo?maxwidth=50&photoreference="+photo+"&key=AIzaSyCo2CGB-Z0Z7B_7tQ9CO14H3D4AZpI1AFA");
//                    JSONObject json4 = json2.getJSONObject("user_rating");
//                    String vote = json4.getString("votes");
//                    votes.add(vote);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}