package com.apkmarvel.facebooksdksample.helpers;

import android.app.Activity;
import android.net.Uri;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by JCF on 3/1/2016.
 */
//https://developers.facebook.com/docs/sharing/android
public class ShareFB extends FacebookSdk{
    private ShareDialog shareDialog;
    public ShareFB(Activity activity){
        shareDialog = new ShareDialog(activity);
        callbackManager = CallbackManager.Factory.create();
    }
    public void shareLink(String title,String description,String url){
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            // add content you want to share
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(title)
                    .setContentDescription(description)
                    .setContentUrl(Uri.parse(url))
                    .build();
            shareDialog.show(linkContent);
        }
    }
    public void registerCallback(FacebookCallback<Sharer.Result> callback){
        shareDialog.registerCallback(callbackManager,callback);
    }
}
