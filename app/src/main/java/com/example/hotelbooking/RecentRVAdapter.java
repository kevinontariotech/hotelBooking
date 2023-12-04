package com.example.hotelbooking;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecentRVAdapter extends RecyclerView.Adapter<RecentRVAdapter.RecentViewHolder> {
    Context context;
    List<RecentModal> recentList;

    public RecentRVAdapter(Context context, List<RecentModal> recentList) {
        this.context = context;
        this.recentList = recentList;
    }

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_recycleview_item, parent, false);
        return new RecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, int position) {
        holder.countryName.setText(recentList.get(position).getCountry());
        holder.placeName.setText(recentList.get(position).getPlace());
        holder.price.setText(recentList.get(position).getPrice());
        holder.placeImage.setImageResource(recentList.get(position).getImageURL());
    }

    @Override
    public int getItemCount() {
        return recentList.size();
    }

    public static final class RecentViewHolder extends RecyclerView.ViewHolder {
        ImageView placeImage;
        TextView placeName, countryName, price;

        public RecentViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.imageView);
            placeName = itemView.findViewById(R.id.placeName);
            countryName = itemView.findViewById(R.id.countryName);
            price = itemView.findViewById(R.id.price);

            // move to second fragment
            placeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_MainFragment_to_FirstFragment);
                }
            });
        }
    }
}
