package com.julianlucas.dataprac_julian;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by JulianLucas on 4/2/18.
 */

public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    public String mTitle = " ";
    public String mSnippet = " ";

    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public MyItem(double lat, double lng, String title, String snippet) {
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
}
