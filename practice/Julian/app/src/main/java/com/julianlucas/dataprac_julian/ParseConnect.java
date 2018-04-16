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

public class ParseConnect extends Application {

    public void onCreate() {
        super.onCreate();

        //enable local datastor
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
            marker.put("Title", object.getTitle());

            marker.save();
        }}catch (ParseException e){
            Log.i("upload", e.toString());
        }


    }

    public void getObjects(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ExampleObject");
        /*query.getInBackground("4oUzPYCdZG", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e==null && object != null){
                    Log.i("ObjectValue", object.getString("myString"));
                }
                else{
                    Log.i("ObjectValue","null");
                }
            }
        });*/

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects != null){
                    for (int i = 0; i < objects.size(); i++){
                        Log.i("findtest",objects.get(i).getString("myString"));
                    }
                }
                else{
                    Log.i("findtest","error");
                }
            }
        });
    }
}
