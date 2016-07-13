package com.parisilabs.backgroundintentservice;

import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.parisilabs.util.ServiceTools;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // An instance of the status broadcast receiver
    BackgroundIntentBroadcastReceiver mBackgroundIntentReceiver;

    private MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(Constants.TAG, "In the onCreate() event");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // The filter's action is BROADCAST_ACTION
        IntentFilter statusIntentFilter = new IntentFilter(
                Constants.BROADCAST_ACTION);
        // Sets the filter's category to DEFAULT
        statusIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);

        // Instantiates a new BackgroundIntentBroadcastReceiver
        mBackgroundIntentReceiver = new BackgroundIntentBroadcastReceiver();
        // Registers the BackgroundIntentBroadcastReceiver and its intent filters
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mBackgroundIntentReceiver,
                statusIntentFilter);

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

    /**-----------------
     *
     * Activity Life Cycle
     * @see http://stackoverflow.com/questions/8515936/android-activity-life-cycle-what-are-all-these-methods-for
     *
     * ---> open |
     * onCreate()
     * onStart()
     * onResume()
     *
     * * ---> back button
     * onPause()
     * onStop()
     * onDestroy()
     *
     * ---> background
     * onPause()
     * onStop()
     *
     * ---> foreground
     * onRestart()
     * onStart()
     * onResume()
     *
     *-------------------*/

    /**
     * Called when the activity will start interacting with the user.
     * At this point your activity is at the top of the activity stack, with user input going to it. Always followed by onPause().
     *
     * At onResume Broadcast receiver should be re-registered only if
     * you want to receive events when the app is in foreground
     * @see http://stackoverflow.com/questions/7887169/android-when-to-register-unregister-broadcast-receivers-created-in-an-activity
     */
    @Override
    public void onResume()
    {
        super.onResume();

        Log.d(Constants.TAG, "In the onResume() event");

        Context ctx = getApplicationContext();
        // restore application contex
        // like pass back this context to the library


    }

    /**
     * Called as part of the activity lifecycle when an activity is going into the background,
     * but has not (yet) been killed. The counterpart to onResume().
     * When activity B is launched in front of activity A,
     * this callback will be invoked on A. B will not be created until A's onPause() returns, so be sure to not do anything lengthy here.
     *
     * At onPause Broadcast receiver should be unregistered only if
     * you want to receive events when the app is in foreground
     * @see http://stackoverflow.com/questions/7887169/android-when-to-register-unregister-broadcast-receivers-created-in-an-activity
     */
    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(Constants.TAG, "In the onPause() event");
    }

    /**
     * Called when you are no longer visible to the user. You will next receive either onRestart(), onDestroy(), or nothing,
     * depending on later user activity.
     * Note that this method may never be called, in low memory situations where the system
     * does not have enough memory to keep your activity's process running after its onPause() method is called.
     */
    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(Constants.TAG, "In the onStop() event");
    }

    /**
     * Called when the activity is becoming visible to the user.
     * Followed by onResume() if the activity comes to the foreground, or onStop() if it becomes hidden.
     */
    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(Constants.TAG, "In the onStart() event");
    }

    /**
     * Called after your activity has been stopped, prior to it being started again.
     * Always followed by onStart()
     */
    @Override
    public void onRestart()
    {
        super.onRestart();
        Log.d(Constants.TAG, "In the onRestart() event");
    }

    /*
     * This callback is invoked when the system is about to destroy the Activity.
     * Note: the onDestroy is not guaranteed to be called you must assure that the
     * Broadcast receiver will be unregistered.
     */
    @Override
    public void onDestroy()
    {

        // If the BackgroundIntentBroadcastReceiver still exists, unregister it and set it to null
        if (mBackgroundIntentReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mBackgroundIntentReceiver);
            mBackgroundIntentReceiver = null;
        }

        Log.d(Constants.TAG, "In the onDestroy() event");

        // Must always call the super method at the end.
        super.onDestroy();
    }

    /**-----------------
     *
     * Activity Life Cycle
     * @see http://stackoverflow.com/questions/8515936/android-activity-life-cycle-what-are-all-these-methods-for
     *
     *-------------------*/


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

            Boolean isServiceRunning = ServiceTools.isServiceRunning(
                    MainActivity.this.getApplicationContext(),
                    BackgroundIntentService.class);

            Log.d(Constants.TAG,String.format("Service:%s running:%b", BackgroundIntentService.class.getName(), isServiceRunning));

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
