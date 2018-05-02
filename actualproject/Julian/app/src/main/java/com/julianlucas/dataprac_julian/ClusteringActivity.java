


package com.julianlucas.dataprac_julian;

/**
 * Created by JulianLucas on 4/10/18.
 */

        import android.content.Intent;
        import android.os.AsyncTask;
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
        import com.parse.FindCallback;
        import com.parse.ParseAnalytics;
        import com.parse.ParseException;
        import com.parse.ParseGeoPoint;
        import com.parse.ParseObject;
        import com.parse.ParseQuery;

        import org.json.JSONException;

        import java.io.InputStream;
        import java.text.CollationElementIterator;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Collection;
        import java.util.Random;
        import static com.julianlucas.dataprac_julian.ParseConnect.*;


/**
 * Simple activity demonstrating ClusterManager.
 **/
public class ClusteringActivity extends BaseActivity {

    public static List<MyItem> items;

    public static ClusterManager<MyItem> spotClusterManager;
    public static ClusterManager<MyItem> munchiesClusterManager;
    public static ClusterManager<MyItem> plugClusterManager;

    @Override
    protected void startMap() {

        //getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.869402, -124.086886), 15));
        MarkerManager markerManager = new MarkerManager(getMap());

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

    }

    public void randomMarker(View view){
        ParseGeoPoint userLocatoin = new ParseGeoPoint(LocationProvider.loc.getLatitude(), LocationProvider.loc.getLongitude());

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Markers");
        query.whereNear("location", userLocatoin);
        query.whereEqualTo("AddedBy", "default");
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0) {
                    //select a random marker that is close by by generating a random index for the
                    // ParseObject list form the callback
                    Random generator = new Random();
                    int index = generator.nextInt() % 10;
                    if (index < 0) {
                        index *= -1;
                    }
                    ParseObject currentObject = objects.get(index);

                    //grab the type of the selected marker from the server
                    String type = (String) currentObject.get("Type");
                    ParseGeoPoint selectedGeoPoint = (ParseGeoPoint) currentObject.get("location");
                    LatLng markerLoc = new LatLng(selectedGeoPoint.getLatitude(), selectedGeoPoint.getLongitude());

                    //zoom the camera in so that all markers are displayed and can be grabbed from the cluster manager
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLoc, 22.0f));

                    if(type.equals("munchies")){
                        munchiesClusterManager.cluster();
                        Collection<Marker> munchiesCollection = munchiesClusterManager.getMarkerCollection().getMarkers();
                        ArrayList<Marker> munchiesMarkers = new ArrayList<>(munchiesCollection);
                        for (int i = 0; i < munchiesMarkers.size(); i++){

                            if(munchiesMarkers.get(i).getPosition().latitude == markerLoc.latitude
                                    && munchiesMarkers.get(i).getPosition().longitude == markerLoc.longitude){
                                munchiesMarkers.get(i).showInfoWindow();
                            }
                        }
                    }
                    else if(type.equals("spot")){
                        spotClusterManager.cluster();
                        Collection<Marker> spotCollection = spotClusterManager.getMarkerCollection().getMarkers();
                        ArrayList<Marker> spotMarkers = new ArrayList<>(spotCollection);
                        for(int i = 0; i < spotMarkers.size(); i++){
                            if(spotMarkers.get(i).getPosition().latitude == markerLoc.latitude
                                    && spotMarkers.get(i).getPosition().longitude == markerLoc.longitude){
                                spotMarkers.get(i).showInfoWindow();
                            }
                        }
                    }
                    else if(type.equals("plug")){
                        plugClusterManager.cluster();
                        Collection<Marker> plugCollection = plugClusterManager.getMarkerCollection().getMarkers();
                        ArrayList<Marker> plugMarkers = new ArrayList<>(plugCollection);

                        for(int i = 0; i < serverMarkers.size(); i++){

                            if(plugMarkers.get(i).getPosition().latitude == markerLoc.latitude
                                    && plugMarkers.get(i).getPosition().longitude == markerLoc.latitude){
                                plugMarkers.get(i).showInfoWindow();
                            }

                        }
                    }
                }
            }

        });

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
        munchiesClusterManager.cluster();

    }

    public void filterSpots(View view){
        Collection<Marker> spotCollection = spotClusterManager.getMarkerCollection().getMarkers();
        ArrayList<Marker> spotMarkers = new ArrayList<>(spotCollection);

        CheckBox spotbox = findViewById(R.id.spots);
        if(!spotbox.isChecked())
        {
            for(int i = 0;  i < spotMarkers.size(); i++){
                spotMarkers.get(i).setVisible(false);
            }

        }else{

            for(int i = 0;  i < spotMarkers.size(); i++){
                spotMarkers.get(i).setVisible(true);
            }
        }
    }

    public void plugFilter(View view){
        Collection<Marker> plugCollection = plugClusterManager.getMarkerCollection().getMarkers();
        ArrayList<Marker> plugMarkers = new ArrayList<>(plugCollection);

        CheckBox plugbox = findViewById(R.id.plugs);
        if(!plugbox.isChecked())
        {
            for(int i = 0;  i < plugMarkers.size(); i++){
                plugMarkers.get(i).setVisible(false);
            }

        }else{

            for(int i = 0;  i < plugMarkers.size(); i++){
               plugMarkers.get(i).setVisible(true);
            }
        }
    }

    public static void addColor(){
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

    public void addNewMarker(View view){
        Intent intent = new Intent(this, addMarker.class);
        startActivity(intent);
        finish();
    }


}