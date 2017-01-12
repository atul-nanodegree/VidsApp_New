package com.wowapps.vidsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.wowapps.vidsapp.util.VidsAppAds;

/**
 * Created by somendra.
 */
public class AboutActivity extends BaseActivity {


    private TextView versionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

       //Displaying banner ads at bottom of screen
        VidsAppAds vidsAppAds=new VidsAppAds(this);
        vidsAppAds.bannerAds(getResources().getString(R.string.banner_home_footer_favouritevideo));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActionbarTitle("About", false, R.id.toolbar);
        versionText = (TextView) findViewById(R.id.version);
        versionText.setText("Version: " + getVersionName(this));
    }

    private String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {

        }
        return versionName;
    }

}
