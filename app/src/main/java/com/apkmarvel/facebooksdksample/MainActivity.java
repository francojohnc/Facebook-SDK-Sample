package com.apkmarvel.facebooksdksample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.apkmarvel.facebooksdksample.utils.UtilFacebook;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Button btnLogin;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cast();
        registerListener();
        UtilFacebook.printHashKey(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        //
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, loginResult);
    }
    private FacebookCallback<LoginResult> loginResult =  new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                //logs
                Log.d(TAG, "getId:" + profile.getId());
                Log.d(TAG, "getFirstName:" + profile.getFirstName());
                Log.d(TAG, "getMiddleName:" + profile.getMiddleName());
                Log.d(TAG, "getLastName:" + profile.getLastName());
                Log.d(TAG, "getName:" + profile.getName());
//                            profile.getProfilePictureUri(400, 400).toString()
            }
            Toast.makeText(MainActivity.this, "Getting other info...", Toast.LENGTH_SHORT).show();
//            // App code
//            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
//                    new GraphRequest.GraphJSONObjectCallback() {
//                        @Override
//                        public void onCompleted(JSONObject object, GraphResponse response) {
//                            Log.d(TAG, "object:" + object.toString());
//                        }
//                    });
//            Bundle parameters = new Bundle();
//            parameters.putString("fields", "id,first_name, last_name, email,gender, user_birthday");
//            request.setParameters(parameters);
//            request.executeAsync();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "onCancel", Toast.LENGTH_SHORT).show();
            LoginManager.getInstance().logOut();
        }

        @Override
        public void onError(FacebookException error) {
            Toast.makeText(getApplicationContext(), "onError:" + error, Toast.LENGTH_SHORT).show();
        }
    };
    private void registerListener() {
        btnLogin.setOnClickListener(this);
    }

    private void cast() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                Log.e(TAG,"btnLogin");
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
