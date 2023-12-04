package com.example.hotelbooking;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_VIDEO = 1;

    private List<String> eventList = Arrays.asList("0", "1", "2", "3", "4");

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? TYPE_IMAGE : TYPE_VIDEO;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_IMAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_cell_image, parent, false);
            return new ImageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_cell_video, parent, false);
            return new VideoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageViewHolder) {
            int[] imageResIds = new int[]{R.drawable.hotel_1, R.drawable.hotel_2, R.drawable.hotel_3};
            ((ImageViewHolder) holder).imageView.setBackgroundResource(imageResIds[position % imageResIds.length]);
        } else if (holder instanceof VideoViewHolder) {
            int[] videoResIds = new int[]{R.raw.hotel_video_3, R.raw.hotel_video_2, R.raw.hotel_video_1};
            String packageName = holder.itemView.getContext().getPackageName();
            Uri videoUri = Uri.parse("android.resource://" + packageName + "/" + videoResIds[position % videoResIds.length]);
            VideoView videoView = ((VideoViewHolder) holder).videoView;
            ((VideoViewHolder) holder).videoUri = videoUri;
            videoView.setVideoURI(videoUri);
            videoView.start();
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    videoView.start();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        MediaPlayer mediaPlayer;
        Uri videoUri; // Add this line

        VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video_view);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer = mp;
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    videoView.setVideoURI(videoUri);
                    videoView.start();
                }
            });
        }
    }
}
