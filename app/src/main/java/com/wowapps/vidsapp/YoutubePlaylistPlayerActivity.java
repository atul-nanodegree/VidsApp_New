package com.wowapps.vidsapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.wowapps.vidsapp.util.VidsApplUtil;

/**
 * Created by atul.
 */
public class YoutubePlaylistPlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView mPlayerView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_playlist_player);

        mPlayerView = (YouTubePlayerView) findViewById(R.id.playlist_player_view);
        mPlayerView.initialize(ApplicationConstants.YOUTUBE_DEVELOPER_BROWSER_KEY, this);

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult result) {
        Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
        if(VidsApplUtil.isVersionLess(this)){
            Intent intent = new Intent(YoutubePlaylistPlayerActivity.this, ErrorActivity.class);
            startActivity(intent);
        }


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean restored) {
        if (!restored) {
            player.cuePlaylist(getIntent().getStringExtra("PLAY_LIST_ID"));
            player.setFullscreen(true);
        }

    }
}
