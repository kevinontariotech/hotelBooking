package com.example.hotelbooking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hotelbooking.databinding.FragmentMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    RecyclerView recentRecycler;
    RecentRVAdapter recent;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<RecentModal> recentList = new ArrayList<>();
        recentList.add(new RecentModal("Cinque Terre", "Italy", "$250/night", R.drawable.hotel_main_1));
        recentList.add(new RecentModal("Macau", "China", "$200/night", R.drawable.hotel_main_2));
        recentList.add(new RecentModal("Santorini", "Greece", "$350/night", R.drawable.hotel_main_3));
        recentList.add(new RecentModal("Banff", "Canada", "$150/night", R.drawable.hotel_main_4));
        recentList.add(new RecentModal("Santorini", "Greece", "$300/night", R.drawable.hotel_main_5));

        // set rv
        recentRecycler = view.findViewById(R.id.recent_recyler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        recent = new RecentRVAdapter(getContext(), recentList);
        recentRecycler.setAdapter(recent);

        binding.accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainFragment.this).navigate(R.id.action_MainFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}