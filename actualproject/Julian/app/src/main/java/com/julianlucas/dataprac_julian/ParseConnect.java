package com.julianlucas.dataprac_julian;

import android.app.Application;
import android.util.Log;

import com.julianlucas.dataprac_julian.item.MyItem;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;
/*
Setting Bitnami application password to 'cvO8Gpp6y6CY'
 (the default application username is 'user')
 ec2-52-53-128-162.us-west-1.compute.amazonaws.com
 */
public class ParseConnect extends Application {

    public static List<ParseObject> serverMarkers;
    public void onCreate() {
        super.onCreate();

        //enable local datastore
        Parse.enableLocalDatastore(this);

        //initliaze parse
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("b74932ea19aed2ce0cebc0a4bc4e055eadc1220d")
                .clientKey("8e1a262af23301c2db72566111bd7ce643868c4f")
                .server("http://52.53.128.162:80/parse/")
                .build()
        );

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();



        //enable public read access
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        getObjects();

    }

    public  static void upLoadObjects(){

        try{
        for(MyItem object : ClusteringActivity.items){

            ParseObject marker = new ParseObject("Markers");
            marker.put("latitude",object.getPosition().latitude);
            marker.put("longitude", object.getPosition().longitude);
            marker.put("Description",object.getSnippet());
            marker.put("Type", object.getType());
            marker.put("Title", object.getTitle());

            marker.save();
        }}catch (ParseException e){
            Log.i("upload", e.toString());
        }


    }

    public void getObjects(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Markers");


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects != null){
                    serverMarkers = objects;
                    for (int i = 0; i < objects.size(); i++){

                        Log.i("findtest",objects.get(i).getString("Title"));

                    }
                }
                else{
                    Log.i("findtest","error");
                }
            }
        });
    }
}
