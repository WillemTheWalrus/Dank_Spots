package com.willemthewalrus.awspractice;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/* Bitnami login info
password: cvO8Gpp6y6CY
 application username is 'user')
 */
/**
 * Created by William on 3/16/2018.
 */

public class StarterApplication extends Application {

    public void onCreate(){
        super.onCreate();

        //enable local datastor
        Parse.enableLocalDatastore(this);

        //initliaze parse
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                            .applicationId( "b74932ea19aed2ce0cebc0a4bc4e055eadc1220d")
                            .clientKey("8e1a262af23301c2db72566111bd7ce643868c4f")
                            .server("http://52.53.128.162:80/parse/")
        .build()
        );


        ParseObject object = new ParseObject("ExampleObject");
        object.put("myNumber", "666");
        object.put("myString", "balls");

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.i("Parse Result", "Much Successss");

                }
                else{
                    Log.i("Parse Result", "FAILURE");
                }
            }
        });

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        //enable public read access
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);


    }



}
