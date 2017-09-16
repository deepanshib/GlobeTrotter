package com.deepanshi.globetrotter;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class weather extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;
    Double clong = 0.0, clat = 0.0;
    //    Double clong = 28.7196, clat = 77.0662;
    LocationManager locate;
    LocationListener locationListener;
    ProgressBar progressBar;
    TextView a,c,min,max,current,desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        a = (TextView) findViewById(R.id.name);
        c = (TextView) findViewById(R.id.country);
        min = (TextView) findViewById(R.id.min);
        max = (TextView) findViewById(R.id.max);
        current = (TextView) findViewById(R.id.currentTemp);
        desc = (TextView) findViewById(R.id.description);
        Toast.makeText(getApplicationContext(),"Please Wait...And make sure that your GPS and INTERNET is ON",Toast.LENGTH_LONG).show();
            locate = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                clat = location.getLatitude();
                clong = location.getLongitude();
                Toast.makeText(getApplicationContext(),"latitude-"+clat+" and longitude"+clong,Toast.LENGTH_LONG).show();
                new weather.RetrieveFeedTask().execute();
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
//60000 n 1000
    }
    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Almost Done", Toast.LENGTH_SHORT).show();

        }

        protected String doInBackground(Void... urls) {
            // Do some validation here
            try {
                String urlls = "https://api.openweathermap.org/data/2.5/weather?lat=" + clat + "&lon=" + clong + "&appid=477634cb38f36e106e1f1493f2e36b18\n";
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
                JSONObject json = jsonObj.getJSONObject("main");
                String currentT = json.getString("temp");
                double cc = Double.parseDouble(currentT);
                cc = cc - 273.15;
                current.setText(cc + " C");
                String minT = json.getString("temp_min");
                double minmin = Double.parseDouble(minT);
                minmin = minmin - 273.15;
                min.setText("Min. Temp.: \n" + minmin + " C");
                String maxT = json.getString("temp_max");
                double maxmax = Double.parseDouble(maxT);
                maxmax = maxmax - 273.15;
                max.setText("Max. Temp.: \n" + maxmax + " C");
                JSONObject json2 = jsonObj.getJSONObject("sys");
                String country = json2.getString("country");
                c.setText(country);
                String area = jsonObj.getString("name");
                a.setText(area);
                JSONArray jsonArray = jsonObj.getJSONArray("weather");
                JSONObject json3 = jsonArray.getJSONObject(0);
                String Wtype = json3.getString("description");
                desc.setText(Wtype);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }}

