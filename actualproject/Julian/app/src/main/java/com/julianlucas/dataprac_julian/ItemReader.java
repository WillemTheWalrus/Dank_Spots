/*package com.julianlucas.dataprac_julian;

import com.julianlucas.dataprac_julian.item.MyItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by JulianLucas on 4/9/18.


public class ItemReader {
    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    public List<MyItem> read(InputStream inputStream) throws JSONException {
        List<MyItem> items = new ArrayList<MyItem>();
        String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            String title = null;
            String snippet = null;
            String type = null;
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            if (!object.isNull("title")) {
                title = object.getString("title");
            }
            if (!object.isNull("snippet")) {
                snippet = object.getString("snippet");
            }
            if (!object.isNull("type")) {
                type = object.getString("type");
            }
            items.add(new MyItem(lat, lng, title, snippet ,type));
        }
        return items;
    }
    */
    package com.julianlucas.dataprac_julian;

import android.util.Log;

import com.julianlucas.dataprac_julian.item.MyItem;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



    public class ItemReader {


        public List<MyItem> read(List<ParseObject> pobjects) throws JSONException {
            List<MyItem> items = new ArrayList<MyItem>();
            for(int i = 0; i < pobjects.size(); i++){
                ParseGeoPoint location = pobjects.get(i).getParseGeoPoint("location");
                String title = pobjects.get(i).getString("Title");
                String description = pobjects.get(i).getString("Description");
                String type = pobjects.get(i).getString("Type");

                MyItem item = new MyItem(location.getLatitude(),location.getLongitude(),title,description,type);
                items.add(item);
            }
            Log.i("items", items.get(0).getTitle());
            return items;
        }

    }




