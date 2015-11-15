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

    TextView forecast;
    List<MyTask> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        forecast = (TextView) findViewById(R.id.textView4);
        forecast.setMovementMethod(new ScrollingMovementMethod());

        tasks = new ArrayList<>();
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
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);*/

        if(item.getItemId() == R.id.action_settings){
            requestData("http://api.wunderground.com/api/bb69b4e8504aea49/" +
                    "forecast/bestfct:true/q/33040.xml");}
        return false;
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

    protected void updateDisplay(String message) {
        forecast.append(message + "\n");
    }

    private class MyTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            String content = requestManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            updateDisplay(result);

        }

        @Override
        protected void onProgressUpdate(String... values) {
            updateDisplay(values[0]);
        }

    }
}
