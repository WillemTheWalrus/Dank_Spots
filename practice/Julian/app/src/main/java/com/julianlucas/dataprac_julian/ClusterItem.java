package com.julianlucas.dataprac_julian;

/**
 * Created by JulianLucas on 4/2/18.
 */

import com.google.android.gms.maps.model.LatLng;

/**
 * ClusterItem represents a marker on the map.
 */
public interface ClusterItem {

    /**
     * The position of this marker. This must always return the same value.
     */
    LatLng getPosition();

    /**
     * The title of this marker.
     */
    String getTitle();

    /**
     * The description of this marker.
     */
    String getSnippet();
}
