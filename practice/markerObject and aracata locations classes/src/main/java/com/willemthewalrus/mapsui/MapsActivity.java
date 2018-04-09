package com.willemthewalrus.mapsui;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Marker> mapMarkers = new ArrayList<Marker>();
    ArrayList<markerObject> markerList = arcataLocations.arcataLocationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        arcataLocations.populateLocations();
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for(int i = 0; i < mapMarkers.size(); i++){
                    if(marker.getTitle().equals(mapMarkers.get(i).getTitle())){
                        markerObject currentmarker = markerList.get(i);
                        TextView description = findViewById(R.id.description);
                        description.setText(currentmarker.getSnippet());
                        Log.i("markerclick", "markerfound");
                        marker.showInfoWindow();
                        return true;

                    }
                }
                Log.i("markerclick","markernotfound");
                return false;
            }
        });

        for(int i= 0; i < markerList.size(); i++){
            mapMarkers.add(mMap.addMarker(new MarkerOptions().position(markerList.get(i).getPosition()).title(markerList.get(i).getTitle())));
        }


        // Add a marker in Sydney and move the camera

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.870977, -124.086185), (float)15));

    }

    public void plugFilter(View view){

        CheckBox plugs  = findViewById(R.id.plugs);
        boolean isChecked = plugs.isChecked();

        if(isChecked){
            for(int i = 0; i < markerList.size(); i++){
                if(markerList.get(i).getType()==1){
                    mapMarkers.get(i).setVisible(true);
                }
            }
        }
        else{
            for(int i = 0; i < markerList.size(); i++){
                if(markerList.get(i).getType()==1){
                    mapMarkers.get(i).setVisible(false);
                }
            }
        }

    }

    public void spotFilter(View view){

        CheckBox spots  = findViewById(R.id.spots);
        boolean isChecked = spots.isChecked();

        if(isChecked){
            for(int i = 0; i < markerList.size(); i++){
                if(markerList.get(i).getType()== 2 ){
                    mapMarkers.get(i).setVisible(true);
                }
            }
        }
        else{
            for(int i = 0; i < markerList.size(); i++){
                if(markerList.get(i).getType()== 2){
                    mapMarkers.get(i).setVisible(false);
                }
            }
        }

    }

    public void munchiesFilter(View view){
        CheckBox munchies  = findViewById(R.id.munchies);
        boolean isChecked = munchies.isChecked();

        if(isChecked){
            for(int i = 0; i < markerList.size(); i++){
                if(markerList.get(i).getType()==0){
                    mapMarkers.get(i).setVisible(true);
                }
            }
        }
        else{
            for(int i = 0; i < markerList.size(); i++){
                if(markerList.get(i).getType()==0){
                    mapMarkers.get(i).setVisible(false);
                }
            }
        }
    }




}


