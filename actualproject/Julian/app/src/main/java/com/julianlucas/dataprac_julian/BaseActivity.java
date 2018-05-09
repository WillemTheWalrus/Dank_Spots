package com.julianlucas.dataprac_julian;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import org.xml.sax.helpers.ParserFactory;

import java.util.Collection;

public abstract class BaseActivity extends FragmentActivity implements  OnMapReadyCallback, LocationProvider.LocationCallback, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener {
    public static GoogleMap mMap;
    private LocationProvider mLocationProvider;
    public final int REQUEST_PERMISSIONS_ACCESS = 69;
    public static boolean showMunchies;
    public static boolean showSpots;
    public static boolean showPlugs;
    public SearchView locationSearch;
    public ourSearchQuery ourQuery;


    protected int getLayoutId() {
        return R.layout.activity_markers;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ParseConnect.getObjects();
        setUpMap();


        //create a new onTextQueryListener object
        ourQuery = new ourSearchQuery();

        //initialize booleans for displaying spots(changed by checkboxes)
        showMunchies = true;
        showPlugs = true;
        showSpots = true;

        //initialize search bar functionality
        locationSearch = findViewById(R.id.search);
        locationSearch.setSubmitButtonEnabled(true);
        locationSearch.setQueryRefinementEnabled(true);

        //assign our onTextQueryListener to handle the search event for our searchbar
        locationSearch.setOnQueryTextListener(ourQuery);

        //initialize our drawerer layout
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        if (item.getItemId() == R.id.mySpots) {
                            //launches an activity that displays a map with all of the user's personal markers
                            Intent intent = new Intent(getBaseContext(), viewUserMarkers.class);
                            startActivity(intent);
                            finish();

                        }
                        else if(item.getItemId() == R.id.homies){

                            //Launches the buddy center activity, an activity that deals with sending
                            // markers and friendlist management
                            Intent intent = new Intent(getBaseContext(), buddyCenterHome.class);
                            startActivity(intent);
                            finish();
                        }
                        return true;
                    }
                }
        );


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMap();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Camera permission has not been granted.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_ACCESS);
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        try {
            mLocationProvider.disconnect();
        } catch(NullPointerException e){
            Log.i("exception", e.toString());
        }
    }

    /*
    Updates the UI and parse server location information when a user moves
     */
    public void handleNewLocation(Location location) {

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));

        mMap.setMaxZoomPreference(23.0f);
        ParseConnect.upLoadLocation(location);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (mMap != null) {
            try {

                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationButtonClickListener(this);
                mMap.setOnMyLocationClickListener(this);
                mLocationProvider.connect();

            } catch(SecurityException e){
                Log.i("exception", e.toString());
            }
            startMap();
            mMap = map;
        }
        else{
            mMap = map;
        }


    }

    @Override
    /*
    Handles the result of location permissions request
     */
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_ACCESS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    try {

                        mLocationProvider = new LocationProvider(this, this);
                        mMap.setMyLocationEnabled(true);
                        mMap.setOnMyLocationButtonClickListener(this);
                        mMap.setOnMyLocationClickListener(this);
                        startMap();
                        mLocationProvider.connect();


                    }catch(SecurityException e){
                        Log.i("exception", e.toString());
                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }



    /*
    Checks to see if the user has granted location permissions.
     If the permissions have not been franted, then they are requested
     */
    private void setUpMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Camera permission has not been granted.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_ACCESS);

        } else {

            mLocationProvider = new LocationProvider(this, this);

        }
    }

    /*
      This is implemented in ClusteringActivity.java
     */
    protected abstract void startMap();


    protected GoogleMap getMap() {
        return mMap;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
}


