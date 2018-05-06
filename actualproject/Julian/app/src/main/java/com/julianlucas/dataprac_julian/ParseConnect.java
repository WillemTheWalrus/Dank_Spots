package com.julianlucas.dataprac_julian;

import android.app.Application;
import android.location.Location;
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

import java.util.Arrays;
import java.util.List;

import static com.julianlucas.dataprac_julian.MainActivity.Username;

/*
Setting Bitnami application password to 'cvO8Gpp6y6CY'
 (the default application username is 'user')
 ec2-52-53-128-162.us-west-1.compute.amazonaws.com
 */
public class ParseConnect extends Application {

    /*
    ArrayList<GeoPoint> myGeoPoints = new ArrayList<GeoPoint>();
    for(int i = 0; i < serverMarkers.size(); i++)
    {
        myGeioPoints.add(serverMarkers.get(i).get("location"));
        }
     */

    public static List<ParseObject> serverMarkers;
    public static List<ParseObject> serverAccount;
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
        getAccounts();



    }

    public  static void upLoadLocation(Location location){

        try{

            for(int i = 0; i < serverAccount.size(); i++){
                if(serverAccount.get(i).getString("Username").equals(Username)){
                    ParseGeoPoint point = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
                    serverAccount.get(i).put("Location", point );
                    serverAccount.get(i).save();
                }




        }}catch (ParseException e){
            Log.i("upload", e.toString());
        }


    }

    public static void upLoadAccount(String username, String password)
    {
        ParseObject newAccount = new ParseObject("Account");
        newAccount.put("Username", username);
        newAccount.put("Password", password);
        newAccount.saveInBackground();
    }

    public static void getAccounts() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");

        try{
            serverAccount = query.find();
        }catch (ParseException e){
            Log.i("error", e.toString());
        }




    }


    public static void getObjects(){
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
