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

package com.parisilabs.backgroundintentservice;

import java.util.Locale;

/**
 *
 * Constants used by multiple classes in this package
 */
public final class Constants {

    public static final String TAG = "PRSLB";

    // Set to true to turn on verbose logging
    public static final boolean LOGV = true;
    
    // Set to true to turn on debug logging
    public static final boolean LOGD = true;

    // Custom actions

    // Defines a custom Intent action
    public static final String BROADCAST_ACTION = "com.parisilabs.backgroundintentservice.BROADCAST";

    // Defines the key for the status "extra" in an Intent
    public static final String EXTENDED_DATA_STATUS = "com.parisilabs.backgroundintentservice.STATUS";

    // Defines the key for the log "extra" in an Intent
    public static final String EXTENDED_STATUS_LOG = "com.parisilabs.backgroundintentservice.LOG";

    // Status values to broadcast to the Activity

    // The download is starting
    public static final int STATE_ACTION_STARTED = 0;

    // The background thread is connecting to the RSS feed
    public static final int STATE_ACTION_CONNECTING = 1;

    // The background thread is parsing the RSS feed
    public static final int STATE_ACTION_PARSING = 2;

    // The background thread is writing data to the content provider
    public static final int STATE_ACTION_WRITING = 3;

    // The background thread is done
    public static final int STATE_ACTION_COMPLETE = 4;

    // The background thread is doing logging
    public static final int STATE_LOG = -1;

    public static final CharSequence BLANK = " ";
}
