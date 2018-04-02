package com.julianlucas.dataprac_julian;

import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    Button b1,b2;
    EditText ed1,ed2;

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


        public void submitButton(View v)
    {
        setContentView(R.layout.activity_warning);

    }


    public void acceptButton(View v)
    {

        setContentView(R.layout.activity_markers);

    }
}



