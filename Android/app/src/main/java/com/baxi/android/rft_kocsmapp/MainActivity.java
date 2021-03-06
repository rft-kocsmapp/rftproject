package com.baxi.android.rft_kocsmapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    private AccessToken accessToken;
    private Profile profile;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if(oldProfile == null){
                    MainActivity.this.profile = currentProfile;
                }
                else{
                    MainActivity.this.profile = oldProfile;
                }

            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(oldAccessToken == null){
                    MainActivity.this.accessToken = currentAccessToken;
                }
                else {
                    MainActivity.this.accessToken = oldAccessToken;
                }

            }
        };

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void showPubs(View view) {
        Intent intent = new Intent(this, ShowPubsActivity.class);
        startActivity(intent);
    }

    public void showMap(View view) {
        Intent intent = new Intent(this, ShowMapActivity.class);
        startActivity(intent);
    }

    public void suggestOne(View view) {
        Intent intent = new Intent(this, SuggestOneActivity.class);
        startActivity(intent);
    }

    public void exitApp(View view) {
        showExitDialog();
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    public void showExitDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.exit_dialog);
        dialog.setTitle("");

        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_beer_spill);

        TextView tv = (TextView) dialog.findViewById(R.id.exittext);
        tv.setText("Biztosan ki akarsz lépni?");

        Button yesButton = (Button) dialog.findViewById(R.id.dialogButtonYes);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        Button noButton = (Button) dialog.findViewById(R.id.dialogButtonNo);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
        accessTokenTracker.stopTracking();
    }
}



