package com.julianlucas.dataprac_julian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseAnalytics;


import static com.julianlucas.dataprac_julian.ParseConnect.serverAccount;

public class CreateAccount extends AppCompatActivity {

    private EditText newUsername;
    private EditText newPassword1;
    private EditText newPassword2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        new ParseConnect().getAccounts();

        newUsername = findViewById(R.id.newUsername);
        newPassword1 = findViewById(R.id.newpassword1);
        newPassword2 = findViewById(R.id.newPassword2);

    }

    public void createAccount(View view){
        String username = newUsername.getText().toString();
        String password1 = newPassword1.getText().toString();
        String password2 = newPassword2.getText().toString();
        boolean usernameExists = false;
        for(int i = 0; i < serverAccount.size(); i++){
            if(serverAccount.get(i).getString("Username").equals(username)){
                usernameExists = true;

            }
        }

        Log.i("passwords", password1 + " " + password2);
        if(usernameExists){
            Toast.makeText(this, getString(R.string.username_not_unique), Toast.LENGTH_LONG).show();
        }
        else if(!password1.equals(password2)){
            Toast.makeText(this, getString(R.string.match_passwords), Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Account created succesfully", Toast.LENGTH_LONG).show();
            ParseConnect.upLoadAccount(username, password1);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }




    }




}
