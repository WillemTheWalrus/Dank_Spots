package com.julianlucas.dataprac_julian;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class buddyCenterHome extends AppCompatActivity {

    public List friendsList;
    public LinearLayout linearLayout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddy_center_home);
        linearLayout = findViewById(R.id.scrollLinear);
        ParseConnect.getAccounts();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Buddy Center");



        for(int i = 0; i < ParseConnect.serverAccount.size(); i++){
            ParseObject currObject = ParseConnect.serverAccount.get(i);
            if(currObject.getString("Username").equals(MainActivity.Username)){
                friendsList = currObject.getList("friends");

            }
        }


        if(friendsList != null){
            int nextid = R.id.scrollLinear ;
            for(int i = 0; i < friendsList.size(); i++) {
                LinearLayout vertical = new LinearLayout(this);
                TextView friendName = new TextView(this);
                Button sendMarker = new Button(this);
                Button removeBuddy = new Button(this);


                String currentFriend = (String) friendsList.get(i);
                sendMarker.setText("Send Marker");
                sendMarker.setTextSize(12f);
                removeBuddy.setText("Remove");
                removeBuddy.setTextSize(12f);
                friendName.setText(currentFriend);
                friendName.setTextColor(Color.WHITE);


                nextid++;
                vertical.setId(nextid);
                nextid++;
                sendMarker.setId(nextid);
                nextid++;
                friendName.setId(nextid);
                nextid++;
                removeBuddy.setId(nextid);

                final int sendId = (int) sendMarker.getId() + 1;
                final int removeId = (int) removeBuddy.getId() - 1;

                sendMarker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView userName = (TextView) findViewById(sendId);
                        sendMarker(userName.getText().toString());

                    }
                });
                removeBuddy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView userName = findViewById(removeId);
                        removeFriend(userName.getText().toString());
                    }
                });


                vertical.addView(friendName);
                vertical.addView(sendMarker);
                vertical.addView(removeBuddy);

                linearLayout.addView(vertical);

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int linearLayoutWidth = displayMetrics.widthPixels - 48;

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) friendName.getLayoutParams();
                params.width = linearLayoutWidth / 3;
                friendName.setLayoutParams(params);
                params = (LinearLayout.LayoutParams) sendMarker.getLayoutParams();
                params.width = linearLayoutWidth / 3;
                sendMarker.setLayoutParams(params);
                //params = (LinearLayout.LayoutParams)removeBuddy.getLayoutParams();
                //params.width = linearLayoutWidth/3;
                //removeBuddy.setLayoutParams(params);
            }

        }
        else{
            TextView nofriends = (TextView)findViewById(R.id.friendless);
            nofriends.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Starts the send Marker activity
     */
    public void sendMarker(String receiver){

        Intent intent = new Intent(this, sendMarker.class);
        intent.putExtra("receiver", receiver);
        startActivity(intent);
    }


    /**
     * Removes the friend from the user's friendslist, updates their parse account object and
     * restarts the activity
     */
    public void removeFriend(String removee){

        for(int i = 0; i < ParseConnect.serverAccount.size(); i++){
            if(ParseConnect.serverAccount.get(i).getString("Username").equals(MainActivity.Username)){
                List friends = ParseConnect.serverAccount.get(i).getList("friends");
                friends.remove(removee);
                ParseConnect.serverAccount.get(i).put("friends", friends);
                ParseConnect.serverAccount.get(i).saveInBackground();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
    }

    public void checkInbox(View view){
        Intent intent = new Intent(this, viewInbox.class);
        startActivity(intent);

    }

}
