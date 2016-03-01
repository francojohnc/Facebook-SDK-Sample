package com.apkmarvel.facebooksdksample.helpers.FacebookApi;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JCF on 3/1/2016.
 */
public abstract class FBSdk {
    public static final String TAG = FBSdk.class.getSimpleName();
    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
    protected CallbackManager callbackManager;
    public static void printHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e(TAG, "keyHash: " + keyHash);
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }
    public static  void init(Context context){
        FacebookSdk.sdkInitialize(context);
    }
}
