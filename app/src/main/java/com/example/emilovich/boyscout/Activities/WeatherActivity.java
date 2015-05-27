package com.example.emilovich.boyscout.Activities;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emilovich.boyscout.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class WeatherActivity extends ActionBarActivity {
    private Button buttonSearchTown;
    private EditText townSearch;
    private TextView textViewTownResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initUI();
    }

    private void initUI() {
        buttonSearchTown = (Button) findViewById(R.id.buttonSearchTown);
        townSearch = (EditText) findViewById(R.id.townSearch);
        textViewTownResult = (TextView) findViewById(R.id.textViewTownResult);

        buttonSearchTown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!townSearch.getText().toString().equals("")) {
                    String url = "http://api.openweathermap.org/data/2.5/weather?q=" + townSearch.getText().toString().toLowerCase() + ",dk";
                    if (isConnected()) {
                        new HttpAsyncTask().execute(url);
                    }
                    else{
                        textViewTownResult.setText("No internet-connection.");
                    }
                } else {
                    textViewTownResult.setText("Enter town please");
                }
            }
        });
    }

    public String getWeatherInfo(String url) {
        InputStream inputStream = null;
        String result = "";

        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null){
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
                String line = "";
                while((line = bufferedReader.readLine())!= null){
                    result += line;
                }
                inputStream.close();
            }
            else{
                result = "Did not work!";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //check if internet
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            return getWeatherInfo(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            String response = "";
            double temp;
            double tempInCelsius;
            double windSpeed;
            double windSpeedInms;
            try {
                JSONObject json = new JSONObject(result);
                String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
                temp = json.getJSONObject("main").getDouble("temp");
                tempInCelsius = (temp-273.15);
                windSpeed = json.getJSONObject("wind").getDouble("speed");
                windSpeedInms = (windSpeed*0.44704);
                response = description + "\nTemperature " + String.format("%.2f", tempInCelsius) + " C \nWind " + String.format("%.2f", windSpeedInms)  + " m/s";
            } catch (JSONException e) {
                e.printStackTrace();
            }
            textViewTownResult.setText(response);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
