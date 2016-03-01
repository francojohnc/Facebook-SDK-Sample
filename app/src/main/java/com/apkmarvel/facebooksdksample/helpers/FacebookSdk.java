package com.apkmarvel.facebooksdksample.helpers;

import android.app.Activity;

import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by JCF on 3/1/2016.
 */
public abstract class  FacebookSdk {
    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
    protected CallbackManager callbackManager;
}
