package com.willemthewalrus.mapsui;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by wmd42 on 4/2/2018.
 */

public class markerObject implements ClusterItem {


    private String title;
    private String description;
    private LatLng location;

    /*
    0 = munchies
    1 = plug
    2 = spot
     */
    int type;

    public markerObject(String t, String d, LatLng l, int tt){
        title = t;
        description = d;
        location = l;
        type = tt;

    }

    public String getTitle(){
        return title;
    }

    public String getSnippet(){
        return description;
    }

    public LatLng getPosition() {
        return location;
    }

    public int getType() {
        return type;
    }
}
