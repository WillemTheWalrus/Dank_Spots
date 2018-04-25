


package com.julianlucas.dataprac_julian;

/**
 * Created by JulianLucas on 4/10/18.
 */

        import android.util.Log;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.CheckBox;
        import android.widget.Toast;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.model.BitmapDescriptor;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.maps.android.MarkerManager;
        import com.google.maps.android.clustering.ClusterManager;
        import com.julianlucas.dataprac_julian.item.MyItem;
        import com.parse.ParseAnalytics;

        import org.json.JSONException;

        import java.io.InputStream;
        import java.text.CollationElementIterator;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Collection;


/**
 * Simple activity demonstrating ClusterManager.
 **/
public class ClusteringActivity extends BaseActivity {

    public static List<MyItem> items;
    public static ClusterManager<MyItem> mClusterManager;
    public static ClusterManager<MyItem> spotClusterManager;
    public static ClusterManager<MyItem> munchiesClusterManager;
    public static ClusterManager<MyItem> plugClusterManager;

    @Override
    protected void startMap() {

        //getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.869402, -124.086886), 15));
        MarkerManager markerManager = new MarkerManager(getMap());
        mClusterManager = new ClusterManager<MyItem>(this, getMap(), markerManager);
        spotClusterManager = new ClusterManager<MyItem>(this, getMap(),markerManager);
        plugClusterManager = new ClusterManager<MyItem>(this, getMap(),markerManager);
        munchiesClusterManager = new ClusterManager<MyItem>(this, getMap(),markerManager);
        getMap().setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                spotClusterManager.onCameraIdle();
                munchiesClusterManager.onCameraIdle();
                plugClusterManager.onCameraIdle();
            }
        });



        try {
            readItems();
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }

        Collection<Marker> spotCollection = spotClusterManager.getMarkerCollection().getMarkers();
        Collection<Marker> munchiesCollection = munchiesClusterManager.getMarkerCollection().getMarkers();
        Collection<Marker> plugCollection = plugClusterManager.getMarkerCollection().getMarkers();

        ArrayList<Marker> spotMarkers = new ArrayList<>(spotCollection);
        ArrayList<Marker> munchiesMarkers = new ArrayList<>(munchiesCollection);
        ArrayList<Marker> plugMarkers = new ArrayList<>(plugCollection);
        for(int i = 0; i < spotMarkers.size(); i++){
            spotMarkers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

        }
        for(int i = 0; i < munchiesMarkers.size(); i++){
            munchiesMarkers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }

        for(int i = 0; i < plugMarkers.size(); i++){
            plugMarkers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }

    }

    public  void filterMunchies(View view){

        Collection<Marker> munchiesCollection = munchiesClusterManager.getMarkerCollection().getMarkers();
        ArrayList<Marker> munchiesMarkers = new ArrayList<>(munchiesCollection);

        CheckBox munchbox = findViewById(R.id.munchies);
        if(!munchbox.isChecked())
        {
            for(int i = 0;  i < munchiesMarkers.size(); i++){
                munchiesMarkers.get(i).setVisible(false);
            }

        }else{

            for(int i = 0;  i < munchiesMarkers.size(); i++){
                munchiesMarkers.get(i).setVisible(true);
            }
        }

    }

    public void addColor(View view){
        Collection<Marker> spotCollection = spotClusterManager.getMarkerCollection().getMarkers();
        Collection<Marker> munchiesCollection = munchiesClusterManager.getMarkerCollection().getMarkers();
        Collection<Marker> plugCollection = plugClusterManager.getMarkerCollection().getMarkers();

        ArrayList<Marker> spotMarkers = new ArrayList<>(spotCollection);
        ArrayList<Marker> munchiesMarkers = new ArrayList<>(munchiesCollection);
        ArrayList<Marker> plugMarkers = new ArrayList<>(plugCollection);
        for(int i = 0; i < spotMarkers.size(); i++){
            spotMarkers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        }
        for(int i = 0; i < munchiesMarkers.size(); i++){
            munchiesMarkers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }

        for(int i = 0; i < plugMarkers.size(); i++){
            plugMarkers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }
    }

    private void readItems() throws JSONException {


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        items = new ItemReader().read(ParseConnect.serverMarkers);

        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getType().equals("munchies")){
                munchiesClusterManager.addItem(items.get(i));
            }
            else if(items.get(i).getType().equals("spot")){
                spotClusterManager.addItem(items.get(i));
            }
            else if(items.get(i).getType().equals("plug")){
                plugClusterManager.addItem(items.get(i));
            }
        }

    }
}