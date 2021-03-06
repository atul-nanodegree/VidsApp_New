package com.wowapps.vidsapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.wowapps.vidsapp.util.VidsAppAds;
import com.wowapps.vidsapp.util.VidsApplUtil;

import java.util.List;


public class VideoListActivity extends BaseActivity  {

//    private Spinner categorySpinner, subCategorySpinner;
    private  CoordinatorLayout mMainCoordinatorLayout;
    private List<YoutubeNtOVideosListItemEntity> videoListItmeArrayList = null;
    private YoutubeNtOVideosListEntity           videoListEntity        = null;
    public static String                         pageToken              = null;

    private RecyclerView mRecyclerView;
    private YoutubeNewTOldVideosListAdapter      mDoclevelListAdapter;

    private Context mContext;
    private static final String                  TAG                    = "VideoListF.class";

    // private View rootView;

    private String vidsType, vidsIds;

    private ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_ntovideolists_fragment);
        
        //Displaying banner ads at bottom of screen
        VidsAppAds vidsAppAds = new VidsAppAds(this);
        vidsAppAds.bannerAds(getResources().getString(R.string.banner_home_footer_video));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getIntent().getStringExtra(APITags.TYPE) != null) {

           vidsType=getIntent().getStringExtra(APITags.TYPE);
        }
        if (getIntent().getStringExtra(APITags.FORMATEDLIST) != null) {
           vidsIds=getIntent().getStringExtra(APITags.FORMATEDLIST);
        }
        if (getIntent().getStringExtra(APITags.TITLE) != null) {

            setActionbarTitle(getIntent().getStringExtra(APITags.TITLE),false,R.id.toolbar);
        }
        else{
            setActionbarTitle("VideoList",false,R.id.toolbar);

        }//        toolbar.setLogo(R.drawable.icon);
        mContext=VideoListActivity.this;
        mMainCoordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatelayout);


        mRecyclerView = (RecyclerView) findViewById(R.id.youtube_videolists_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mDoclevelListAdapter = new YoutubeNewTOldVideosListAdapter(mContext);
        if (VidsApplUtil.isTablet(getBaseContext())) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, ApplicationConstants.PLAYLIST_NUM_COLUMNS));
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, R.dimen.margin_10dp);
            mRecyclerView.addItemDecoration(itemDecoration);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        pBar = (ProgressBar) findViewById(R.id.progressbar);

        new YoutubeTask().execute();

    }



    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private class YoutubeTask extends AsyncTask<String, Integer, YoutubeNtOVideosListEntity> {
        protected YoutubeNtOVideosListEntity doInBackground(String... urls) {
            ApiYoutube a=new ApiYoutube();
           // YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("video","cQcSkiOX4c8,wspLLHypZ4M,qYCIci0BHc4,hYorcTW9apA");
            //YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("channel","UCDS9hpqUEXsXUIcf0qDcBIA");
            YoutubeNtOVideosListEntity youtubeNtOVideosListEntity = a.intiateAPICall(vidsType, vidsIds);

            return youtubeNtOVideosListEntity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pBar.setVisibility(View.VISIBLE);
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(YoutubeNtOVideosListEntity result) {
            videoListEntity = result;
            pBar.setVisibility(View.GONE);
            if (videoListEntity != null) {
                videoListItmeArrayList = videoListEntity.getItems();

                if (videoListItmeArrayList != null && videoListItmeArrayList.size() > 0) {


                    mDoclevelListAdapter.setDataList(videoListItmeArrayList);

                    mRecyclerView.setAdapter(mDoclevelListAdapter);


                } else {
                    Toast.makeText(mContext, "Something went wrong. Please try later", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "Something went wrong. Please try later", Toast.LENGTH_SHORT).show();
            }

            //  Log.i("logs for youtube",result.getNextPageToken());

        }
    }

    public YoutubeNewTOldVideosListAdapter getAdapter() {
        return mDoclevelListAdapter;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            return true;

        }
        return super.onOptionsItemSelected(menuItem);
    }
}
