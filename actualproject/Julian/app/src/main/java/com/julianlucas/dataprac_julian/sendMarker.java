package com.julianlucas.dataprac_julian;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.ArrayList;

public class sendMarker extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String selectedMarkerTitle;
    private ParseObject selectedMarker;
    private String receiver;
    private Button sender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_marker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        receiver = getIntent().getStringExtra("receiver");
        sender = findViewById(R.id.send);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        GoogleMap.OnMarkerClickListener clickListener = new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                selectedMarkerTitle = marker.getTitle();
                sender.setClickable(true);
                return false;
            }
        };

        mMap.setOnMarkerClickListener(clickListener);
        double latitude = LocationProvider.loc.getLatitude();
        double longitude = LocationProvider.loc.getLongitude();

        // center camera on user
        LatLng userLocation = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13f));
        addMarker.putUserMarkers(mMap);
    }

    public void sendToFriend(View view){

        for(int i = 0; i < ParseConnect.serverAccount.size(); i++){

            //grab the parse object representing the account that is to receive the marker
            if(ParseConnect.serverAccount.get(i).get("Username").equals(receiver)){
                ArrayList<ParseObject> inboxMarkers;
                //check to see if the geopoint inbox array has been initialized
                if(ParseConnect.serverAccount.get(i).get("inbox") == null) {

                    //create a new Marker array to upload into the inbox column
                    inboxMarkers = new ArrayList<>();
                }
                else{
                    inboxMarkers = (ArrayList)ParseConnect.serverAccount.get(i).get("inbox");
                }

                for (int j = 0; j < ParseConnect.serverMarkers.size(); j++) {
                    if (ParseConnect.serverMarkers.get(j).getString("Title").equals(selectedMarkerTitle)) {
                            selectedMarker = ParseConnect.serverMarkers.get(j);

                    }
                }

                Log.i("selectedMarker", (String) selectedMarker.get("Title"));

                boolean duplicateMarker = false;

                //check to see if the user already has this marker in their inbox
                if(inboxMarkers.size() > 0){
                    for(int j = 0; j < inboxMarkers.size(); j++){
                        if(selectedMarker.getObjectId().equals(inboxMarkers.get(i).getObjectId())){
                            duplicateMarker = true;
                        }
                    }
                }
                if(!duplicateMarker) {

                    inboxMarkers.add(selectedMarker);
                    ParseConnect.serverAccount.get(i).put("inbox", inboxMarkers);


                    try {
                        ParseConnect.serverAccount.get(i).save();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(this, buddyCenterHome.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "They already have this marker in their inbox, please select a different one", Toast.LENGTH_SHORT).show();
                }



            }

        }
    }
}
