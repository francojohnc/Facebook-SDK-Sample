package com.apkmarvel.facebooksdksample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apkmarvel.facebooksdksample.helpers.FacebookApi.FBSdk;
import com.apkmarvel.facebooksdksample.helpers.FacebookApi.LoginFB;
import com.apkmarvel.facebooksdksample.helpers.FacebookApi.ShareFB;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnCheckLogin;
    private Button btnLogOut;
    private Button btnShare;
    private ShareFB shareFB;
    private LoginFB loginFB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //facebook
        FBSdk.printHashKey(this);
        FBSdk.init(this);
        //
        cast();
        registerListener();
        //for login
        loginFB = new LoginFB(this);
        loginFB.registerCallback(loginResult);
        //for share
        shareFB = new ShareFB(this);
        shareFB.registerCallback(shareResult);
    }

    private FacebookCallback<Sharer.Result> shareResult = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onSuccess(Sharer.Result result) {
            Toast.makeText(MainActivity.this, "onSuccess postId: " + result.getPostId(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel() {
            Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(FacebookException e) {
            Toast.makeText(MainActivity.this, "onError: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    private FacebookCallback<LoginResult> loginResult = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                //public profile info
                Log.e(TAG, "getId:" + profile.getId());
                Log.e(TAG, "getFirstName:" + profile.getFirstName());
                Log.e(TAG, "getMiddleName:" + profile.getMiddleName());
                Log.e(TAG, "getLastName:" + profile.getLastName());
                Log.e(TAG, "getName:" + profile.getName());
//             profile.getProfilePictureUri(400, 400).toString()//image url
            }
            Toast.makeText(MainActivity.this, "Getting other info...", Toast.LENGTH_SHORT).show();
//            getPrivateInfo(loginResult);
        }
        private void getPrivateInfo(LoginResult loginResult) {
            // get email
            //set parameter needs
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,first_name, last_name, email,gender, user_birthday");
            //pas param to graph request
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.e(TAG, "object:" + object.toString());
                        }
                    });
            request.setParameters(parameters);
            request.executeAsync();
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
        btnCheckLogin.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }

    private void cast() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCheckLogin = (Button) findViewById(R.id.btnCheckLogin);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnShare = (Button) findViewById(R.id.btnShare);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                Log.e(TAG, "btnLogin");
                String loginPermission[]={"public_profile"};
                loginFB.login(loginPermission);
                break;
            case R.id.btnCheckLogin:
                Toast.makeText(MainActivity.this, "isLoggedIn: " + LoginFB.isLoggedIn(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnLogOut:
                LoginFB.logout();
                break;
            case R.id.btnShare:
                String title = "Hello Facebook";
                String description = "The 'Hello Facebook' sample  showcases simple Facebook integration";
                String url = "http://developers.facebook.com/android";
                shareFB.shareLink(title, description, url);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginFB.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        shareFB.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

}
