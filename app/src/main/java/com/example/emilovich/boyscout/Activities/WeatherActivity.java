package com.example.emilovich.boyscout.Activities;

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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
                String result = "";
                Toast.makeText(getApplicationContext(),"clicked", Toast.LENGTH_LONG).show();
                String url = "http://api.openweathermap.org/data/2.5/weather?q="+townSearch.getText().toString().toLowerCase()+",dk";
                if (!townSearch.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"I IF?", Toast.LENGTH_LONG).show();
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);
                    try{
                        textViewTownResult.setText("Vi er kommet ind i try :i");
                        HttpResponse respons = client.execute(httpGet);

                        InputStream content = respons.getEntity().getContent();

                        BufferedReader buffer = new BufferedReader(new InputStreamReader(content));

                        String s = "";
                        while ((s = buffer.readLine()) != null){
                            result += s;
                        }
                        textViewTownResult.setText(result);

                        Log.d("url: ",url);
                        Log.d("Result: ", result);




                        /*JSONObject result = new JSONObject("Result");
                        double temperature = result.getJSONObject("main").getDouble("temp");
                        double humidity = result.getJSONObject("main").getDouble("humidity");
                        String description = result.getJSONArray("weather").getJSONObject(0).getString("description");*/
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    textViewTownResult.setText("Nothing found! Enter town please");
                }
            }
        });
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
}
