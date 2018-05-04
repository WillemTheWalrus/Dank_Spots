package com.julianlucas.dataprac_julian;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.Arrays;
import java.util.List;

public class buddyCenterHome extends AppCompatActivity {

    public List friendsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddy_center_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        for(int i = 0; i < ParseConnect.serverAccount.size(); i++){
            ParseObject currObject = ParseConnect.serverAccount.get(i);
            if(currObject.getString("Username").equals(MainActivity.Username)){
                friendsList = currObject.getList("friends");

            }
        }


        LinearLayout linearLayout = findViewById(R.id.scrollLinear);
        for(int i = 0; i < friendsList.size(); i++) {
            LinearLayout vertical = new LinearLayout(this);

            TextView friendName = new TextView(this);
            friendName.setText((String)friendsList.get(i));
            friendName.setTextColor(Color.WHITE);
            vertical.addView(friendName);
            linearLayout.addView(vertical);
        }
    }
}
