package com.julianlucas.dataprac_julian;

/**
 * Created by JulianLucas on 4/10/18.
 */

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.julianlucas.dataprac_julian.item.MyItem;
import com.parse.ParseAnalytics;

import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;


/**
 * Simple activity demonstrating ClusterManager.
 **/
public class ClusteringActivity extends BaseActivity {

    public static List<MyItem> items;
    public static ClusterManager<MyItem> mClusterManager;

    @Override
    protected void startMap() {

        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.869402, -124.086886), 15));

        mClusterManager = new ClusterManager<MyItem>(this, getMap());
        getMap().setOnCameraIdleListener(mClusterManager);

        try {
            readItems();
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }
    }

    public void filterMunchies(View view){
        CheckBox munchbox = findViewById(R.id.munchies);
        if(!munchbox.isChecked())
        {
            BaseActivity.mMap.clear();
            mClusterManager.clearItems();

            for(int i = 0; i < items.size(); i++){
                if(!items.get(i).getType().equals("munchies")){
                    mClusterManager.addItem(items.get(i));
                }
            }

            mClusterManager.cluster();

        }else{

            for(int i = 0; i < items.size(); i++){
                if(items.get(i).getType().equals("munchies")){
                    mClusterManager.addItem(items.get(i));
                }
            }
            mClusterManager.cluster();
        }

    }



    private void readItems() throws JSONException {
        /*InputStream inputStream = getResources().openRawResource(R.raw.data);
        items = new ItemReader().read(inputStream);
        mClusterManager.addItems(items);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        ParseConnect.upLoadObjects(); */

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        items = new ItemReader().read(ParseConnect.serverMarkers);

        mClusterManager.addItems(items);


        //ParseConnect.upLoadObjects();

    }
}