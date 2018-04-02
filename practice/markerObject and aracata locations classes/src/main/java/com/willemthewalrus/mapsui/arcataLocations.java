package com.willemthewalrus.mapsui;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wmd42 on 4/2/2018.
 */

public class arcataLocations {
    public static ArrayList<markerObject> arcataLocations = new ArrayList<markerObject>();
    public static void populateLocations(){
        arcataLocations.add(new markerObject("Don's Donuts","mmmmm Donuts", new LatLng(40.869402, -124.086886),0));
        arcataLocations.add(new markerObject("Arcata Pizza and Deli","Pizza thats open kinda late", new LatLng(40.870389, -124.086462),0));
    }

}
