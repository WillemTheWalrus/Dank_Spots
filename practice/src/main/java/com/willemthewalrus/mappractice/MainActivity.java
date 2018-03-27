package com.willemthewalrus.mappractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity  {

    public static double xcoordinate;
    public static double ycoordinate;
    public static String markername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void showMarkers(View view){

        EditText x = findViewById(R.id.x);
        EditText y = findViewById(R.id.y);
        EditText name = findViewById(R.id.markername);

        xcoordinate = Double.parseDouble(x.getText().toString());
        ycoordinate = Double.parseDouble(y.getText().toString());
        markername = name.getText().toString();

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);



    }




}
