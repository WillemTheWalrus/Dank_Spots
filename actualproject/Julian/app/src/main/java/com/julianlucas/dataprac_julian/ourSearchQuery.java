package com.julianlucas.dataprac_julian;

import android.util.Log;
import android.widget.EditText;
import android.widget.SearchView.*;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseGeoPoint;
import static com.julianlucas.dataprac_julian.BaseActivity.*;

public class ourSearchQuery implements OnQueryTextListener {

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        Log.i("pressed", "search called");
        String location = query.toLowerCase();


        LatLng foundspot = new LatLng(-30, 100);
        for(int i = 0; i < ParseConnect.serverMarkers.size(); i++){

            String currentTitle = ParseConnect.serverMarkers.get(i).getString("Title").toLowerCase();


            if(ParseConnect.serverMarkers.get(i).getString("AddedBy").equals("default")){

                Log.i("query", location);
                Log.i("currentTitle", currentTitle);
                if(currentTitle.contains(location)){

                    ParseGeoPoint loco = ParseConnect.serverMarkers.get(i).getParseGeoPoint("location");
                    foundspot = new LatLng(loco.getLatitude(), loco.getLongitude());
                }
            }


        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(foundspot, 22.0f));
        return true;
    }
}
