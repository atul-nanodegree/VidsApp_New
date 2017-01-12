package com.wowapps.vidsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wowapps.vidsapp.util.VidsAppAds;
import com.wowapps.vidsapp.util.VidsApplUtil;


import java.util.List;

/**
 * Created by Somendra.
 */
public class VideoSearchsListAdapter extends RecyclerView.Adapter<VideoSearchsListAdapter.ViewHolder> {

    /**
     * calling Context
     */
    private final Context mContext;

    /**
     * infiater fo inflate the list item
     */
    private final LayoutInflater mLayoutInflater;

    private List<YoutubeNtOVideosListItemEntity> mYoutubePlaylistsList;

    private VidsAppAds mVidsAppAds;

    public VideoSearchsListAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.video_search_list_item, null, false);
        mVidsAppAds = new VidsAppAds(mContext);
        mVidsAppAds.initInterstialAds(mContext.getResources().getString(R.string.interstitial_full_screen));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (mYoutubePlaylistsList != null) {
            holder.mYoutubePlaySubTitle.setText(mYoutubePlaylistsList.get(position).getTitle());
            Picasso.with(mContext).load(mYoutubePlaylistsList.get(position).getThumbnailURL()).into(holder.mYoutubeThumb);
            holder.setAppropriateIcon(mYoutubePlaylistsList.get(position).getId());
        }
    }

    @Override
    public int getItemCount() {
        if (mYoutubePlaylistsList != null) {
            return mYoutubePlaylistsList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mYoutubeShare = null;
        private TextView mYoutubePlaySubTitle = null;
        private ImageView mYoutubeThumb = null;
        private ImageView mPlay = null;
        private ImageView mYoutubeAddYes = null;
        private ImageView mYoutubeAddNo = null;

        public ViewHolder(View itemView) {
            super(itemView);
            mYoutubeShare = (ImageView) itemView.findViewById(R.id.share);
            mYoutubePlaySubTitle = (TextView) itemView.findViewById(R.id.youtube_sub_title);
            mYoutubeThumb = (ImageView) itemView.findViewById(R.id.youtube_playlist_thumb);
            mPlay = (ImageView) itemView.findViewById(R.id.play);
            mYoutubeAddYes = (ImageView) itemView.findViewById(R.id.add_yes);
            mYoutubeAddNo = (ImageView) itemView.findViewById(R.id.add_no);
            mPlay.setOnClickListener(this);
            mYoutubeShare.setOnClickListener(this);
            mYoutubeAddYes.setOnClickListener(this);
            mYoutubeAddNo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.play) {

                if (getAdapterPosition() % 2 != 0) {
                    if (mVidsAppAds != null) {
                        mVidsAppAds.loadInterstialAds();
                    }
                }

                Intent intent = new Intent(mContext, YoutubePlayerActivity.class);
                intent.putExtra("VIDEO_ID", mYoutubePlaylistsList.get(getAdapterPosition()).getId());
                mContext.startActivity(intent);


            } else if (v.getId() == R.id.share) {
                if (getAdapterPosition() % 2 != 0) {
                    if (mVidsAppAds != null) {
                        mVidsAppAds.loadInterstialAds();
                    }
                }
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v="+mYoutubePlaylistsList.get(getAdapterPosition()).getId()+"\n"+"\n"+
                        "For more such cool videos, install VidsApp"+"\n"+ "https://play.google.com/store/apps/details?id=" + VidsActivity.PACKAGE_NAME);
                shareIntent.setType("text/plain");
                mContext.startActivity(Intent.createChooser(shareIntent, "Share this Video with..."));

            } else if (v.getId() == R.id.add_no) {
                if (getAdapterPosition() % 4 == 0) {
                    if (mVidsAppAds != null) {
                        mVidsAppAds.loadInterstialAds();
                    }
                }
                // persist the video id in internal file storage
                addToMyVideos();
            } else if (v.getId() == R.id.add_yes) {
                if (getAdapterPosition() % 5 == 0) {
                    if (mVidsAppAds != null) {
                        mVidsAppAds.loadInterstialAds();
                    }
                }
                // remove the video from internal file storage
                removeFromMyVideos();
            }
        }

        private void addToMyVideos() {
            String videoId = mYoutubePlaylistsList.get(getAdapterPosition()).getId();
            String currentData = VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.MY_VIDEO_FILE_NAME);
            String finalData = currentData + "," + videoId;
            VidsApplUtil.writeDataInFile(mContext, VidsApplUtil.MY_VIDEO_FILE_NAME,
                    finalData);
            notifyDataSetChanged();
            Toast.makeText(mContext, mContext.getResources().getString(
                    R.string.add_my_video), Toast.LENGTH_SHORT).show();
            Log.i("VidsActivity", "my video file data = " + VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.MY_VIDEO_FILE_NAME));
        }

        private void removeFromMyVideos() {
            String videoId = mYoutubePlaylistsList.get(getAdapterPosition()).getId();
            String currentData = VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.MY_VIDEO_FILE_NAME);
            String finalData = currentData.replace("," + videoId, "");
            VidsApplUtil.writeDataInFile(mContext, VidsApplUtil.MY_VIDEO_FILE_NAME,
                    finalData);
            notifyDataSetChanged();
            Toast.makeText(mContext, mContext.getResources().getString(
                    R.string.remove_my_videos), Toast.LENGTH_SHORT).show();
            Log.i("VidsActivity", "My video file data = " + VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.MY_VIDEO_FILE_NAME));
        }

        private void setAppropriateIcon(String videoId) {
            String vidId = null;
            vidId = VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.MY_VIDEO_FILE_NAME);
            if (vidId == null || vidId == "" || !vidId.contains(videoId)) {
                mYoutubeAddYes.setVisibility(View.GONE);
                mYoutubeAddNo.setVisibility(View.VISIBLE);
            } else if (vidId.contains(videoId)) {
                mYoutubeAddYes.setVisibility(View.VISIBLE);
                mYoutubeAddNo.setVisibility(View.GONE);
            }
        }
    }

    public void setDataList(List<YoutubeNtOVideosListItemEntity> docsList) {

        if (mYoutubePlaylistsList == null) {
            mYoutubePlaylistsList = docsList;
        } else {
            mYoutubePlaylistsList.clear();
            mYoutubePlaylistsList.addAll(docsList);
        }
        notifyDataSetChanged();
    }
}

