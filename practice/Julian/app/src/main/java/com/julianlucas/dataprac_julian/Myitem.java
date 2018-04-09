package com.julianlucas.dataprac_julian;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by JulianLucas on 4/2/18.
 */

public class Myitem implements ClusterItem {
    private final LatLng mPosition;
    private String mTitle = null;
    private String mSnippet = null;

    public Myitem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public Myitem(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }
}

