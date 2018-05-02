package com.julianlucas.dataprac_julian.item;

/**
 * Created by JulianLucas on 4/9/18.
 */

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.util.ArrayList;

public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private String type;
    private String owner;
    private ArrayList<String> tags = new ArrayList<String>();

    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
        mTitle = null;
        mSnippet = null;
        type = null;
    }

    public MyItem(double lat, double lng, String title, String snippet, String tyype, String Owner) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
        type = tyype ;
        owner = Owner;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle; }

    @Override
    public String getSnippet() {
        return mSnippet; }

    public String getType(){ return type;}


    public void addTag(String newtag){
        tags.add(newtag);
    }

    public ArrayList<String> getTags() {
        return tags; }

    /**
     * Set the title of the marker
     * @param title string to be set as title
     */
    public void setTitle(String title) {

        mTitle = title;
    }

    /**
     * Set the description of the marker
     * @param snippet string to be set as snippet
     */
    public void setSnippet(String snippet) {

        mSnippet = snippet;
    }

    public String getOwner(){
        return owner;
    }


}
