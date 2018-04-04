package com.willemthewalrus.mapsui;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wmd42 on 4/2/2018.
 */

//arcataLocationsList.add(new markerObject("","",new LatLng(),));

public class arcataLocations {
    public static ArrayList<markerObject> arcataLocationsList = new ArrayList<markerObject>();
    public static void populateLocations(){
        arcataLocationsList.add(new markerObject("Don's Donuts","mmmmm Donuts", new LatLng(40.869402, -124.086886),0));
        arcataLocationsList.add(new markerObject("Arcata Pizza and Deli","Pizza thats open kinda late", new LatLng(40.870389, -124.086462),0));
        arcataLocationsList.add(new markerObject("Slice of Humboldt Pie","Tasty pie, empenadas, and local cider",new LatLng(40.868720, -124.087744),0));
        arcataLocationsList.add(new markerObject("Arcata Scoops","Rich and sweer icecream",new LatLng(40.870602, -124.087132),0));
        arcataLocationsList.add(new markerObject("Crush","Fancy",new LatLng(40.870874, -124.086508),0));
        arcataLocationsList.add(new markerObject("Los Bagels","Freshly baked bagels with creative offerings",new LatLng(40.870640, -124.087619),0));
        arcataLocationsList.add(new markerObject("La Chiquita","Shmack burritos",new LatLng(40.870345, -124.087580),0));
        arcataLocationsList.add(new markerObject("Sushi Spot","quality sushi",new LatLng(40.868840, -124.085382),0));
        arcataLocationsList.add(new markerObject("Heart of Humboldt","Good Prices on bud, no form filling",new LatLng(40.867107, -124.088960),1));
        arcataLocationsList.add(new markerObject("Humboldt Patient Resource Center","Good prices, clones also sold here",new LatLng(40.867186, -124.089445),1));
        arcataLocationsList.add(new markerObject("Arcata Marsh","Beautiul birds and sunsets, everyone mobs here for the sunset",new LatLng(40.860344, -124.094531),2));


    }

}
