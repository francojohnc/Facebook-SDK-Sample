package com.apkmarvel.facebooksdksample.helpers.FacebookApi;

import android.app.Activity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * Created by jcf on 3/1/2016.
 */

//https://developers.facebook.com/docs/facebook-login/permissions
public class LoginFB extends FBSdk {
    private Activity activity;
    public LoginFB(Activity activity){
        this.activity=activity;
        callbackManager = CallbackManager.Factory.create();
    }
    public void login(String persmissions[]){
        LoginManager.getInstance().logInWithReadPermissions(activity,Arrays.asList(persmissions));
    }
    public void registerCallback(FacebookCallback<LoginResult> callback){
        LoginManager.getInstance().registerCallback(callbackManager, callback);
    }
    public static boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
    public static void logout() {
        LoginManager.getInstance().logOut();
    }

}
