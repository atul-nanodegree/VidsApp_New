package com.vidsapp;

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
import com.vidsapp.util.VidsApplUtil;

import java.util.List;

/**
 * Created by somendra on 30-Dec-16.
 */
public class VidsMyVideoAdapter  extends RecyclerView.Adapter<VidsMyVideoAdapter.ViewHolder>{


    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<YoutubeNtOVideosListItemEntity> mYoutubePlaylistsList;

    public VidsMyVideoAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    @Override
    public VidsMyVideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.vids_my_video_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VidsMyVideoAdapter.ViewHolder holder, int position) {
        if (mYoutubePlaylistsList != null) {

            holder.mYoutubePlaySubTitle.setText(mYoutubePlaylistsList.get(position).getTitle());
            Picasso.with(mContext).load(mYoutubePlaylistsList.get(position).getThumbnailURL()).into(holder.mYoutubeThumb);
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
        private ImageView mYoutubeMyVideoRemove = null;

        public ViewHolder(View itemView) {
            super(itemView);

            mYoutubeShare = (ImageView) itemView.findViewById(R.id.share);
            mYoutubePlaySubTitle = (TextView) itemView.findViewById(R.id.youtube_sub_title);
            mYoutubeThumb = (ImageView) itemView.findViewById(R.id.youtube_playlist_thumb);
            mPlay = (ImageView) itemView.findViewById(R.id.play);
            mYoutubeMyVideoRemove = (ImageView) itemView.findViewById(R.id.my_vid_remove);
            mPlay.setOnClickListener(this);
            mYoutubeShare.setOnClickListener(this);
            mYoutubeMyVideoRemove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.play) {
                Intent intent = new Intent(mContext, YoutubePlayerActivity.class);
                intent.putExtra("VIDEO_ID", mYoutubePlaylistsList.get(getAdapterPosition()).getId());
                mContext.startActivity(intent);
            } else if (v.getId() == R.id.share) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v="+mYoutubePlaylistsList.get(getAdapterPosition()).getId()+"\n"+"\n"+"VidsApp Link"+"\n"+"https://play.google.com/store/apps/details?id=com.whatsapp&hl=en");
                shareIntent.setType("text/plain");
                mContext.startActivity(Intent.createChooser(shareIntent, "Share this Video with..."));

            } else if (v.getId() == R.id.my_vid_remove) {
                // remove the video from internal file storage and from list
                removeFromMyVideo();
            }
        }

        private void removeFromMyVideo() {
            String videoId = mYoutubePlaylistsList.get(getAdapterPosition()).getId();
            String currentData = VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.MY_VIDEO_FILE_NAME);
            String finalData = currentData.replace("," + videoId, "");
            VidsApplUtil.writeDataInFile(mContext, VidsApplUtil.MY_VIDEO_FILE_NAME,
                    finalData);
            Toast.makeText(mContext, mContext.getResources().getString(
                    R.string.remove_my_videos), Toast.LENGTH_SHORT).show();
            Log.i("VidsActivity", "My Video file data = " + VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.MY_VIDEO_FILE_NAME));
            mYoutubePlaylistsList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), mYoutubePlaylistsList.size());

            if (mYoutubePlaylistsList.size() == 0) {
                VidsActivity vidsActivity = (VidsActivity) mContext;
                vidsActivity.getEmptyMyVideoViewLayout().setVisibility(View.VISIBLE);
                vidsActivity.getAddMyVideoGenericButton().setVisibility(View.GONE);
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
