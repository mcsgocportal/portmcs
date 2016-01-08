package com.mcsgoc.www.portal;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Lab3 on 10/11/2015.
 */
public class SetupParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // do not open this file
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getString(R.string.APP_ID), getString(R.string.CLIENT_KEY));

    }
}

