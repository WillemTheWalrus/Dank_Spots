package com.julianlucas.dataprac_julian;

import android.content.Context;
import android.widget.CheckBox;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.julianlucas.dataprac_julian.item.MyItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ourClusterRenderer extends DefaultClusterRenderer<MyItem> {

    public ourClusterRenderer(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager){
        super(context, map, clusterManager);
        clusterManager.setRenderer(this);
    }

    @Override
    protected void onClusterItemRendered(MyItem item, Marker marker) {
        super.onClusterItemRendered(item, marker);

        if(item.getType().equals("munchies")){
            if(!BaseActivity.showMunchies){
                marker.setVisible(false);
            }

        }
        else if(item.getType().equals("spot")){

            if(!BaseActivity.showSpots){
                marker.setVisible(false);
            }
        }
        else if(item.getType().equals("plug")){

            if(!BaseActivity.showPlugs){
                marker.setVisible(false);
            }
        }


    }


    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);


        if(item.getType().equals("munchies")){
            if(!BaseActivity.showMunchies){
                markerOptions.visible(false);
            }

        }
        else if(item.getType().equals("spot")){
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            if(!BaseActivity.showSpots){
                markerOptions.visible(false);
            }
        }
        else if(item.getType().equals("plug")){
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            if(!BaseActivity.showPlugs){
                markerOptions.visible(false);
            }
        }

        //determine if it should be visible

    }
}
