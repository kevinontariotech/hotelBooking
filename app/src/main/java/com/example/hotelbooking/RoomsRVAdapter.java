package com.example.hotelbooking;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.text.DecimalFormat;

public class RoomsRVAdapter extends RecyclerView.Adapter<RoomsRVAdapter.ViewHolder> {
    private ArrayList<RoomModal> roomModals;
    private Context context;
    private int counter = -1;

    // constructor
    public RoomsRVAdapter(ArrayList<RoomModal> roomModals, Context context) {
        this.roomModals = roomModals;
        this.context = context;
    }

    @NonNull
    @Override
    // inflate layout
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_recycleview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    // set data to recycler view item
    public void onBindViewHolder(@NonNull ViewHolder holder, int index) {
        // initialize views
        RoomModal modal = roomModals.get(index);
        holder.roomName.setText(modal.getRoomName());
        holder.squareFeetLabel.setText(modal.getSquareFeet());
        holder.bedText.setText(modal.getBeds());
        holder.peopleText.setText(modal.getPeople());
        holder.price.setText(modal.getPrice());
        holder.id = modal.getId();

        int[] images = {
                R.drawable.hotel_wide_1,
                R.drawable.hotel_wide_2,
                R.drawable.hotel_wide_3,
                R.drawable.hotel_wide_4,
                R.drawable.hotel_wide_5,
                R.drawable.hotel_wide_6};

        // change alternating images
        counter = (counter + 1) % images.length;
        holder.banner.setImageResource(images[counter]);

        // parse price to int
        String tmpStr = modal.getPrice().substring(1);
        int tmpInt = Integer.parseInt(tmpStr);

        // calculate total price
        tmpInt = (int)(tmpInt + (tmpInt * 0.13));
        tmpStr = String.format("$%d total", tmpInt);

        // update view
        holder.totalPrice.setText(tmpStr);

        // data to be sent
        tmpStr = modal.getPrice().substring(1);
        double price = Double.parseDouble(tmpStr);
        double tax = price * 0.13;
        double total = price + tax;

        // format data
        DecimalFormat df = new DecimalFormat("$#.00");
        holder.sPrice = df.format(price);
        holder.sTax = df.format(tax);
        holder.sTotal = df.format(total);
    }

    @Override
    public int getItemCount() {
        return roomModals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView roomName, squareFeetLabel, bedText, peopleText, price, totalPrice;
        private Button reserveButton;
        private ImageView banner;
        private int id;
        private String sPrice;
        private String sTax;
        private String sTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // get views
            roomName = itemView.findViewById(R.id.roomName);
            squareFeetLabel = itemView.findViewById(R.id.squareFeetLabel);
            bedText = itemView.findViewById(R.id.bedText);
            peopleText = itemView.findViewById(R.id.peopleText);
            price = itemView.findViewById(R.id.price);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            reserveButton = itemView.findViewById(R.id.reserveButton);
            banner = itemView.findViewById(R.id.roomImage);

            // move to second fragment
            reserveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Name", (String) roomName.getText());
                    bundle.putString("Price", sPrice);
                    bundle.putString("Tax", sTax);
                    bundle.putString("Total", sTotal);

                    Navigation.findNavController(v).navigate(R.id.action_ThirdFragment_to_SecondFragment, bundle);
                }
            });
        }
    }
}
