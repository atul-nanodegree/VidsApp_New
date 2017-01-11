package com.vidsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vidsapp.util.VidsAppAds;
import com.vidsapp.util.VidsApplUtil;

import java.util.List;

/**
 * Created by somendra.
 */
public class RadioFmActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar pBar;
    private Context ctx;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radiofm);
        this.ctx = this;
       //Displaying banner ads at bottom of screen
        VidsAppAds vidsAppAds=new VidsAppAds(this);
        vidsAppAds.bannerAds(getResources().getString(R.string.banner_home_footer_favouritevideo));
        VidsAppAds iAds=new VidsAppAds(ctx);
        iAds.initInterstialAds(getResources().getString(R.string.interstitial_full_screen));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActionbarTitle("Radio", false, R.id.toolbar);
        pBar = (ProgressBar) findViewById(R.id.progressbar);

        webView = (WebView) findViewById(R.id.webView);

        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.setBackgroundColor(0x00000000);
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });


        webView.loadUrl("http://www.onlineradios.in");

        // check for radio ad display count
        int count = VidsPreference.getInstance(this).getRadioAdCounter();
        if (count == 3) {
            iAds.loadInterstialAds();
            // reset the radio ad count to 0
            VidsPreference.getInstance(this).setRadioAdCounter(0);
        }

    }


}
