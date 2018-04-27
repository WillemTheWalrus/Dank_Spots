package com.julianlucas.dataprac_julian;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.clustering.ClusterManager;
import com.julianlucas.dataprac_julian.item.MyItem;
import com.parse.ParseAnalytics;

import java.util.Collection;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {
    private ViewGroup mListView;

    public static String Username;
    public static String Password;

    Button b1, b2;
    EditText ed1, ed2;
    List<MyItem> markerList = ClusteringActivity.items;

    TextView tx1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        b1 = (Button) findViewById(R.id.login);
        b2 = (Button) findViewById(R.id.accept);
        ed1 = (EditText) findViewById(R.id.username);
        ed2 = (EditText) findViewById(R.id.password);
        tx1 = (TextView) findViewById(R.id.text1);
    }


    public void submitButton(View v) {
        Username = ed1.getText().toString();
        Password = ed2.getText().toString();
        setContentView(R.layout.activity_warning);

    }


    public void acceptButton(View v) {

        setContentView(R.layout.activity_markers);


    }




    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ClusteringActivity.class);
        startActivity(intent);
    }
}



