package com.andrewtorr.skywriter;

import android.app.Application;

import com.andrewtorr.skywriter.Models.Script;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class SkyWriterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Script.class);

        Parse.initialize(this, "YQfgN2AowF3HwBFUYmWJW2LMzs5CaI916Erv2A2s", "j3b5UfhkzMaHzmcMzujKZwg5U7SDwDq0efbPHpis");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}