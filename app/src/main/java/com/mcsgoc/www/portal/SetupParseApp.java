package com.mcsgoc.www.portal;

import android.app.Application;
import android.widget.Toast;

import com.mcsgoc.www.portal.helper.ConnectionDetector;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

/**
 * Created by Lab3 on 10/11/2015.
 */
public class SetupParseApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        // do not open this file
        // Enable Local Datastore.


        if (!new ConnectionDetector(getApplicationContext()).isConnectingToInternet()) {
            Toast.makeText(SetupParseApp.this, "No Internet Available", Toast.LENGTH_SHORT).show();
        }

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getString(R.string.APP_ID), getString(R.string.CLIENT_KEY));
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("Notice");

    }
}

