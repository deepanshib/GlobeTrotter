package com.deepanshi.globetrotter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class currencyconvertr extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ProgressBar progressBar;
    EditText editText1,editText2;
    String item="USD";
    TextView textView,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencyconvertr);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
editText1=(EditText)findViewById(R.id.editText);
        editText2=(EditText)findViewById(R.id.editText2);
        textView2=(TextView)findViewById(R.id.text2);

        textView=(TextView)findViewById(R.id.text1);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("choose");
        categories.add("INR");
        categories.add("AED");
        categories.add("EUR");
        categories.add("AMD");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();
        if (!editText1.getText().toString().equals("")||!editText2.getText().toString().equals("")) {
            new RetrieveFeedTask().execute();
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {
            // Do some validation here
            try {
                URL url = new URL("http://apilayer.net/api/live?access_key=fe8d43ceee6d29a0c801c3df64937a87&currencies="+item);
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
                    JSONObject json1 = jsonObj.getJSONObject("quotes");
                    Log.i("convrsion", "" + json1);
                    Double res = json1.getDouble("USD"+item);

                Double finalAns=0.0,finalans2=0.0;

                DecimalFormat df = new DecimalFormat("#.###");

                if(!editText1.getText().toString().equals("")&&!item.equals("choose")) {
                    finalAns = res * Integer.parseInt(editText1.getText().toString());
                    textView.setText(editText1.getText().toString()+" "+"USD is "+df.format(finalAns)+" "+item);

                }
                if (!editText2.getText().toString().equals("")&&!item.equals("choose")) {
                    finalans2 = (1/res) *Integer.parseInt(editText2.getText().toString());
                    textView2.setText(editText2.getText().toString() +" "+ item + " is " +df.format(finalans2)+ " USD");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
//http://apilayer.net/api/live?access_key=fe8d43ceee6d29a0c801c3df64937a87
