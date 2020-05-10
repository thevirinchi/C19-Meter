package com.z3t4z00k.c19meter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;


public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        //ParseObject.registerSubclass();

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("devsocial1Id")
                .clientKey("devsocial1CliKey")
                .server("http://devsocial1.herokuapp.com/parse/")
                .build()
        );

        //Uncomment to test the server connection.
        /*ParseObject object = new ParseObject("ExampleObject");
        object.put("myNumber", "123");
        object.put("myString", "rob");
        Log.d("Log", "Objects put");

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException ex) {
                //Log.d("Log", "ex = " + ex.getMessage());
                if (ex == null) {
                    Log.d("Log", "Successful!");
                } else {
                    Log.d("Log", "Failed" + ex.toString());
                }
            }
        });*/


        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}