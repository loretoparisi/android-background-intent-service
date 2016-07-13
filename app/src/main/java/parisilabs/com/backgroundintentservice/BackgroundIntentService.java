/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package parisilabs.com.backgroundintentservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * This service pucontentlls RSS  from a web site URL contained in the incoming Intent (see
 * onHandleIntent()).
 *
 * As it runs, it broadcasts its status using LocalBroadcastManager;
 *
 * any component that wants to see the status should implement a subclass of BroadcastReceiver and
 * register to receive broadcast Intents with category = CATEGORY_DEFAULT and action
 * Constants.BROADCAST_ACTION.
 *
 */
public class BackgroundIntentService extends IntentService {
    // Used to write to the system log from this class.
    public static final String LOG_TAG = "BackgroundIntentService";

    // Defines and instantiates an object for handling status updates.
    private BroadcastNotifier mBroadcaster;

    /**
     * An IntentService must always have a constructor that calls the super constructor. The
     * string supplied to the super constructor is used to give a name to the IntentService's
     * background thread.
     */
    public BackgroundIntentService() {
        super("BackgroundIntentService");
        mBroadcaster = new BroadcastNotifier(this);
    }

    /**
     * An IntentService must always have a constructor that calls the super constructor. The
     * string supplied to the super constructor is used to give a name to the IntentService's
     * background thread.
     */
    public BackgroundIntentService(Context ctx) {
        super("BackgroundIntentService");
        mBroadcaster = new BroadcastNotifier(ctx);
    }

    /**
     * In an IntentService, onHandleIntent is run on a background thread.  As it
     * runs, it broadcasts its current status using the LocalBroadcastManager.
     * @param workIntent The Intent that starts the IntentService. This Intent contains the
     * URL of the web site from which the RSS parser gets data.
     */
    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets a URL to read from the incoming Intent's "data" value
        String localUrlString = workIntent.getDataString();

        Log.d(Constants.TAG, "handling intent");

        // Broadcasts an Intent indicating that processing has started.
        mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_STARTED);

        // Reports that the service is about to connect to the RSS feed
        mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_CONNECTING);

        // Reports that the feed retrieval is complete.
        mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_COMPLETE);

    }

}
