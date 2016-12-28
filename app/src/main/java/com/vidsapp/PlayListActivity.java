package com.vidsapp;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vidsapp.util.VidsAppAds;
import com.vidsapp.util.VidsApplUtil;

import java.util.List;


public class PlayListActivity extends BaseActivity  {

    private List<YoutubePlayListItemEntity> playListItmeArrayList = null;
    private List<Long> playListItmeCount = null;
    private YoutubePlayListEntity playListEntity = null;
    private RecyclerView mRecyclerView;
    private YoutubePlayListsAdapter mDoclevelListAdapter;
    private String vidsType, vidsIds;
    private Context mContext;
    private static final String TAG = "PlayListFragment.class";
    private ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_playlists_fragment);
        
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
            setActionbarTitle("PlayList",false,R.id.toolbar);

        }        mContext = PlayListActivity.this;


        mRecyclerView = (RecyclerView) findViewById(R.id.youtube_playlists_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mDoclevelListAdapter = new YoutubePlayListsAdapter(mContext);
        pBar = (ProgressBar) findViewById(R.id.progressbar);
        if (VidsApplUtil.isTablet(this)) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, ApplicationConstants.PLAYLIST_NUM_COLUMNS));
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, R.dimen.margin_10dp);
            mRecyclerView.addItemDecoration(itemDecoration);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        } else  {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        }

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

    private class YoutubeTask extends AsyncTask<String, Integer, YoutubePlayListEntity> {
        protected YoutubePlayListEntity doInBackground(String... urls) {
            ApiYoutube a=new ApiYoutube();
//            YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("video","cQcSkiOX4c8,wspLLHypZ4M,qYCIci0BHc4,hYorcTW9apA");
            //YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("channel","UCDS9hpqUEXsXUIcf0qDcBIA");
            YoutubePlayListEntity youtubePlayListEntity=null;

            //for reference
           // youtubePlayListEntity = a.intiateAPICallPlayListCustom("playlistcustom","LLA34Z3lq8FozSQzDHsSLcmQ,PLbF9OWJ9PNmbx3Mzqd7re6hdaEWonx3wI" );


            if(VidsApplUtil.TYPE_CHANNEL.equals(vidsType)){
                youtubePlayListEntity = a.intiateAPICallPlayList("playlist",vidsIds );

            }
            else if(VidsApplUtil.TYPE_PLAYLIST.equals(vidsType)){
                youtubePlayListEntity = a.intiateAPICallPlayListCustom("playlistcustom",vidsIds );

            }
            return youtubePlayListEntity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pBar.setVisibility(View.VISIBLE);
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(YoutubePlayListEntity result) {
            playListEntity = result;
            pBar.setVisibility(View.GONE);
            if (playListEntity != null) {
                playListItmeArrayList = playListEntity.getItems();
                if (playListItmeArrayList != null && playListItmeArrayList.size() > 0) {
                    mDoclevelListAdapter.setDataList(playListItmeArrayList);

                    mRecyclerView.setAdapter(mDoclevelListAdapter);

                } else {
                    Toast.makeText(mContext, "videoListItmeArrayList is null", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "videoListEntity is null", Toast.LENGTH_SHORT).show();
            }

            //  Log.i("logs for youtube",result.getNextPageToken());

        }
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
