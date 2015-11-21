package com.smith.myweatherapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    TextView forecastDisp;
    List<MyTask> tasks;
    List<Forecast> forecastList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        forecastDisp = (TextView) findViewById(R.id.textView4);
        forecastDisp.setMovementMethod(new ScrollingMovementMethod());

        tasks = new ArrayList<>();

        requestData("http://api.wunderground.com/api/keygoeshere/" +
                "forecast/bestfct:true/q/33040.xml");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
        }else if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnHandle3(View view) {
        Intent dispIntent = new Intent(this, DisplayActivity.class);
        startActivity(dispIntent);
    }

    public void btnHandle4(View view) {
        Intent locatIntent = new Intent(this, LocationActivity.class);
        startActivity(locatIntent);
    }

    private void requestData(String uri){

        MyTask task = new MyTask();
        task.execute(uri);
    }

    protected void updateDisplay() {

        if(forecastList != null) {
            for (Forecast forecast : forecastList){
                forecastDisp.append(forecast.getName() + "\n");
            }
        }

    }

    private class MyTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            String content = requestManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            forecastList = ForecastParser.parseFeed(result);
            updateDisplay();

        }

        @Override
        protected void onProgressUpdate(String... values) {

            //updateDisplay(values[0]);
        }

    }
}
