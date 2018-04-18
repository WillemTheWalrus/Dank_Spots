package com.julianlucas.dataprac_julian;

/**
 * Created by JulianLucas on 4/10/18.
 */

import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import com.julianlucas.dataprac_julian.item.MyItem;
import com.parse.ParseAnalytics;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

/**
 * Simple activity demonstrating ClusterManager.
 */
public class ClusteringActivity extends BaseActivity {

    public static List<MyItem> items;
    private ClusterManager<MyItem> mClusterManager;

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



    private void readItems() throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.data);
        items = new ItemReader().read(inputStream);
        mClusterManager.addItems(items);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        ParseConnect.upLoadObjects();
    }
}