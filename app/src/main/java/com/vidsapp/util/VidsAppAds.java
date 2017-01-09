package com.vidsapp.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;



/**
 * Created by atul on 24-12-2016.
 */

public class VidsAppAds {

    private AdView mAdView;
    private Activity mActivity;
    private InterstitialAd mInterstitialAd;

    public VidsAppAds(Activity activity) {
        mAdView = new AdView(activity);
        mActivity = activity;
    }

    public VidsAppAds(Context ctx) {
        mInterstitialAd = new InterstitialAd(ctx);
    }


    public void bannerAds(String bannerId) {
        if (mAdView != null) {
            if (VidsApplUtil.isTablet(mActivity)) {
                mAdView.setAdSize(AdSize.SMART_BANNER);
            } else {
                mAdView.setAdSize(AdSize.BANNER);
            }
            mAdView.setAdUnitId(bannerId);

            final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.BOTTOM;
            // layoutParams.gravity = isTopPosition ? Gravity.TOP : Gravity.BOTTOM;
            if (mActivity != null) {
                mActivity.addContentView(mAdView, layoutParams);
            }

            AdRequest adRequest = new AdRequest.Builder()
                    // Add a test device to show Test Ads
                    //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    //.addTestDevice("501CA82BA12681A250E422CC4BF70A13") //Random Text
                    .build();
            mAdView.loadAd(adRequest);
        }
    }


    public void initInterstialAds(String interstialId) {
        if (mInterstitialAd != null) {
            mInterstitialAd.setAdUnitId(interstialId);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    showInterstitial();
                }
            });
        }
    }

    private void showInterstitial() {
        if (mInterstitialAd != null) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
    }

    public void loadInterstialAds() {

        if (mInterstitialAd != null) {
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            mInterstitialAd.loadAd(adRequest);
        }

    }


    public void makingInvisibleAdview() {
        if(mAdView!=null){
            mAdView.setVisibility(View.GONE);
        }
    }
    public void makingVisibleAdview() {
        if(mAdView!=null){
            mAdView.setVisibility(View.VISIBLE);
        }
    }

}
