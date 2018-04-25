package com.julianlucas.dataprac_julian;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.List;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.util.Collection;

public abstract class BaseActivity extends FragmentActivity implements OnMapReadyCallback {
    public static GoogleMap mMap;


    protected int getLayoutId() {
        return R.layout.activity_markers;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setUpMap();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMap();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (mMap != null) {
            return;
        }
        mMap = map;
        startMap();
    }

    private void setUpMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    /**
     * Run the demo-specific code.
     */
    protected abstract void startMap();

    protected GoogleMap getMap() {
        return mMap;
    }


    /*
    public void plugFilter(View view){

        CheckBox plugs  = findViewById(R.id.plugs);
        boolean isChecked = plugs.isChecked();

        if(isChecked){
            for(int i = 0; i < markerList.size(); i++){
                if(markerList.get(i).getType().equals("plug")){
                    mapMarkers.
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
    */
}


