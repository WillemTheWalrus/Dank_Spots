package com.julianlucas.dataprac_julian;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.parse.ParseObject;

import java.util.Collection;
import java.util.List;

import static com.julianlucas.dataprac_julian.ParseConnect.*;


public class MainActivity extends Activity implements View.OnClickListener {
    private ViewGroup mListView;

    public static String Username;
    public static String Password;
    public static ParseObject userAccountObject;


    Button b1, b2;
    EditText ed1, ed2;
    List<MyItem> markerList = ClusteringActivity.items;

    TextView tx1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        new ParseConnect().getAccounts();

        b1 = (Button) findViewById(R.id.login);
        b2 = (Button) findViewById(R.id.accept);
        ed1 = (EditText) findViewById(R.id.username);
        ed2 = (EditText) findViewById(R.id.password);
        tx1 = (TextView) findViewById(R.id.text1);
    }



    public void submitButton(View v) {
        Username = ed1.getText().toString().trim();
        Password = ed2.getText().toString().trim();
        int iterator = 0;

        String serverUsername = null;
        String serverPassword = null;

        for (int i = 0; i < serverAccount.size(); i++) {

            if (serverAccount.get(i).getString("Username").equals(Username)) {
                serverUsername = serverAccount.get(i).getString("Username");
                serverPassword = serverAccount.get(i).getString("Password");
                iterator = i;

            }
        }
            if(serverUsername == null){
                Toast.makeText(this, getString(R.string.no_account), Toast.LENGTH_LONG).show();
            }
            else if(!serverPassword.equals(Password)){
                Toast.makeText(this, getString(R.string.wrong_password), Toast.LENGTH_LONG).show();
            }
            else{

                userAccountObject = serverAccount.get(iterator);

                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                setContentView(R.layout.activity_warning);


            }
    }

    public void createAccount(View view){
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }


    public void acceptButton(View v) {

        setContentView(R.layout.activity_markers);


    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View view) {
        if(isNetworkAvailable()) {
            Intent intent = new Intent(this, ClusteringActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Need internet connection to start", Toast.LENGTH_LONG);
        }
    }
}



