package parisilabs.com.backgroundintentservice;

import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        try {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Checking service status", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    // Async Task Shared Library Test
                    EventsAPIBackgroundTask apiTask = new EventsAPIBackgroundTask();
                    apiTask.execute();

                }
            });

        } catch(NullPointerException ex) {
            Log.e(Constants.TAG,"Error", ex);
        }

        // Calling Application class (see application tag in AndroidManifest.xml)
        final GlobalApplication globalVariable = (GlobalApplication) getApplicationContext();

    }

    @Override
    public void onResume() {
        super.onResume();

        Context ctx = getApplicationContext();
        // restore application contex
        // like pass back this context to the library


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

    private class EventsAPIBackgroundTask extends AsyncTask<String, Void, String> {

        private BackgroundIntentService bgService;
        private Intent mServiceIntent;

        public EventsAPIBackgroundTask() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            // create the intent service
            bgService = new BackgroundIntentService(MainActivity.this.getApplicationContext() );

            // @see https://developer.android.com/training/run-background-service/send-request.html#CreateRequest
            mServiceIntent = new Intent(MainActivity.this.getApplicationContext(), BackgroundIntentService.class);

            // send a work request to the intent passing the data
            // @see http://stackoverflow.com/questions/18794504/intent-setdata-vs-intent-putextra
            // setData(uri) | putExtra(string)
            MainActivity.this.startService(mServiceIntent);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
