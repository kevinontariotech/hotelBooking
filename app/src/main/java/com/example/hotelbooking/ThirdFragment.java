package com.example.hotelbooking;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.example.hotelbooking.databinding.FragmentThirdBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Date;
import java.util.Map;

public class ThirdFragment extends Fragment {
    private FragmentThirdBinding binding;

    private RecyclerView rv;
    private RoomsRVAdapter adapter;
    private ArrayList<RoomModal> roomModalList;

    String locationQuery;
    String sort = "";
    String order = "";
    String filter = "";
    Map<String, Boolean> amenities = new HashMap<String, Boolean>() {
        {
            put("Wifi", false);
            put("Parking", false);
            put("Food", false);
            put("Beach View", false);
            put("Pool", false);
        }
    };

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // connect to database
        DatabaseManager db = new DatabaseManager(getContext());

        // open date picker dialog
        binding.outlinedEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateRangePickerDialog(view, db);
            }
        });



        // delete all rooms
        db.deleteAllRooms();

        // create rooms
        db.addNewRoom("Standard Room", "250 sq ft", "2 Queen Beds", "Sleeps 4", "$199");
        db.addNewRoom("Deluxe Room", "350 sq ft", "1 King Bed", "Sleeps 2", "$249");
        db.addNewRoom("Junior Suite", "450 sq ft", "1 King Bed, 1 Sofa Bed", "Sleeps 3", "$299");
        db.addNewRoom("Suite", "550 sq ft", "1 King Bed, 1 Sofa Bed", "Sleeps 4", "$349");
        db.addNewRoom("Family Room", "600 sq ft", "2 Queen Beds, 2 Bunk Beds", "Sleeps 6", "$399");
        db.addNewRoom("Presidential Suite", "1000 sq ft", "1 King Bed, 2 Queen Beds", "Sleeps 6", "$499");

        // get notes
        getRoomsList(view, db);
    }

    private void getRoomsList(@NonNull View view, DatabaseManager db) {
        roomModalList = new ArrayList<>();

        switch (sort) {
            case "Descending By Price":
                filter = "price";
                order = "DESC";
                break;
            case "Ascending By Price":
                filter = "price";
                order = "ASC";
                break;
            default:
                break;
        }

        roomModalList = db.readRooms(filter, order);


        if (roomModalList.size() != 0) {
            for (RoomModal room : roomModalList) {
                Log.d("ID", String.valueOf(room.getId()));
            }

            // add notes to recycler view
            adapter = new RoomsRVAdapter(roomModalList, getContext());
            rv = view.findViewById(R.id.roomsRV);

            // setting layout manager for our recycler view.
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            rv.setLayoutManager(linearLayoutManager);
            rv.setAdapter(adapter);
        }
    }

    private void popupFilter(View view, DatabaseManager db) {
        LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.search_popup, null, false);
        PopupWindow pw = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pw.showAtLocation(getActivity().findViewById(R.id.main), Gravity.CENTER, 0, 0);

        EditText searchByLocation = (EditText) popupView.findViewById(R.id.searchByLocation);

        CardView wifi = (CardView) popupView.findViewById(R.id.amenitiesCard1);
        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!amenities.get("Wifi")) {
                    wifi.setBackgroundColor(Color.parseColor("#EAEDED"));
                    amenities.replace("Wifi", true);
                } else {
                    wifi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    amenities.replace("Wifi", false);
                }
            }
        });

        CardView parking = (CardView) popupView.findViewById(R.id.amenitiesCard2);
        parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!amenities.get("Parking")) {
                    parking.setBackgroundColor(Color.parseColor("#EAEDED"));
                    amenities.replace("Parking", true);
                } else {
                    parking.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    amenities.replace("Parking", false);
                }
            }
        });

        CardView food = (CardView) popupView.findViewById(R.id.amenitiesCard3);
        //wifi.setCardBackgroundColor(Color.parseColor("@android:color/blue"));
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!amenities.get("Food")) {
                    food.setBackgroundColor(Color.parseColor("#EAEDED"));
                    amenities.replace("Food", true);
                } else {
                    food.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    amenities.replace("Food", false);
                }
            }
        });

        CardView beachView = (CardView) popupView.findViewById(R.id.amenitiesCard4);
        //wifi.setCardBackgroundColor(Color.parseColor("@android:color/blue"));
        beachView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!amenities.get("Beach View")) {
                    beachView.setBackgroundColor(Color.parseColor("#EAEDED"));
                    amenities.replace("Beach View", true);
                } else {
                    beachView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    amenities.replace("Beach View", false);
                }
            }
        });

        CardView pool = (CardView) popupView.findViewById(R.id.amenitiesCard5);
        //wifi.setCardBackgroundColor(Color.parseColor("@android:color/blue"));
        pool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!amenities.get("Pool")) {
                    pool.setBackgroundColor(Color.parseColor("#EAEDED"));
                    amenities.replace("Pool", true);
                } else {
                    pool.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    amenities.replace("Pool", false);
                }
            }
        });

        Spinner sorter = (Spinner) popupView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(popupView.getContext(), R.array.sorting, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sorter.setAdapter(spinAdapter);


        Button searchButton = (Button) popupView.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationQuery = searchByLocation.getText().toString();
                sort = sorter.getSelectedItem().toString();
                getRoomsList(view, db);
                pw.dismiss();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void DateRangePickerDialog(View view, DatabaseManager db) {
        // create builder for selecting a date range
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a date range");

        // build date picker dialog
        MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();
        datePicker.addOnPositiveButtonClickListener(selection -> {

            // get selected start and end dates
            Long startDate = selection.first;
            Long endDate = selection.second;

            // format dates as strings
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String startDateString = sdf.format(new Date(startDate));
            String endDateString = sdf.format(new Date(endDate));

            // create date range string
            String selectedDateRange = startDateString + " - " + endDateString;

            // display selected date range
            binding.outlinedEditText.setText(selectedDateRange);
            popupFilter(view, db);

        });

        // show dialog
        datePicker.show(getChildFragmentManager(), "DATE_PICKER");
    }
}
