package com.example.hotelbooking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hotelbooking.databinding.FragmentThirdBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Date;

public class ThirdFragment extends Fragment {
    private FragmentThirdBinding binding;

    private RecyclerView rv;
    private RoomsRVAdapter adapter;
    private ArrayList<RoomModal> roomModalList;

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

        // open date picker dialog
        binding.outlinedEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateRangePickerDialog();
            }
        });

        // connect to database
        DatabaseManager db = new DatabaseManager(getContext());

        // delete all rooms
        //db.deleteAllRooms();

        // create rooms
        //db.addNewRoom("Standard Room", "250 sq ft", "2 Queen Beds", "Sleeps 4", "$199");
        //db.addNewRoom("Deluxe Room", "350 sq ft", "1 King Bed", "Sleeps 2", "$249");
        //db.addNewRoom("Junior Suite", "450 sq ft", "1 King Bed, 1 Sofa Bed", "Sleeps 3", "$299");
        //db.addNewRoom("Suite", "550 sq ft", "1 King Bed, 1 Sofa Bed", "Sleeps 4", "$349");
        //db.addNewRoom("Family Room", "600 sq ft", "2 Queen Beds, 2 Bunk Beds", "Sleeps 6", "$399");
        //db.addNewRoom("Presidential Suite", "1000 sq ft", "1 King Bed, 2 Queen Beds", "Sleeps 6", "$499");

        // get notes
        roomModalList = new ArrayList<>();
        roomModalList = db.readRooms();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void DateRangePickerDialog() {
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
        });

        // show dialog
        datePicker.show(getChildFragmentManager(), "DATE_PICKER");
    }
}