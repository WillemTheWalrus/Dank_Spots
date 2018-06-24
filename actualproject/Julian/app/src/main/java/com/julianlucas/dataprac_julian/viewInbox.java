package com.julianlucas.dataprac_julian;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class viewInbox extends AppCompatActivity {

    private ScrollView holder;
    private LinearLayout linearLayout;
    private LinearLayout horizontal;
    public List receivedMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        holder = findViewById(R.id.inboxscroll);
        horizontal = new LinearLayout(this);
       receivedMarkers = MainActivity.userAccountObject.getList("inbox");

       Log.i("inbox size", Integer.toString(receivedMarkers.size()));
       Log.i("inbox first item", receivedMarkers.get(0).toString());


       if(receivedMarkers.get(0).toString().equals("null")){
           TextView emptyBox = new TextView(this);
           emptyBox.setText("Your inbox is empty");
           emptyBox.setTextSize(12f);
           emptyBox.setTextColor(Color.WHITE);
           holder.addView(emptyBox);
       }
       else{
           try {


               for (int i = 0; i < receivedMarkers.size(); i++) {
                   LinearLayout linearLayout = new LinearLayout(this);
                   Button viewOnMap = new Button(this);
                   viewOnMap.setText("View On Map");
                   Button deleter = new Button(this);
                   deleter.setText("Delete");
                   TextView markerOwner = new TextView(this);
                   final ParseObject currentMarkerPointer = (ParseObject) receivedMarkers.get(i);
                   


                   ParseQuery query = new ParseQuery("Markers");
                   final ParseObject currentMarker = query.get(currentMarkerPointer.getObjectId());


                   Log.i("markerID", currentMarker.getObjectId());

                   viewOnMap.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(getBaseContext(), viewReceivedMarker.class);
                           ParseGeoPoint point = currentMarker.getParseGeoPoint("location");
                           double latitude = point.getLatitude();
                           double longitude = point.getLongitude();
                           intent.putExtra("longitude", longitude);
                           intent.putExtra("latitude", latitude);
                           startActivity(intent);

                       }
                   });

                   deleter.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                       }
                   });

                   linearLayout.addView(markerOwner);
                   linearLayout.addView(deleter);
                   linearLayout.addView(viewOnMap);
                   horizontal.addView(linearLayout);

               }
           }catch (ParseException e){
               e.printStackTrace();
           }
           Toast.makeText(this, "inbox not empty", Toast.LENGTH_LONG).show();
           holder.addView(horizontal);
       }


    }
}
