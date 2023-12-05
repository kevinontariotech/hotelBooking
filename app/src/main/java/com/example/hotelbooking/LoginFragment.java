package com.example.hotelbooking;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.hotelbooking.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    EditText email;
    EditText pass;
    Button loginBTN;
    boolean valid;
    boolean emptyData;
    DatabaseManager databaseManager;
    VideoView videoView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginBTN = view.findViewById(R.id.loginButton);
        videoView = view.findViewById(R.id.vid);
        databaseManager = new DatabaseManager(requireContext());
        emptyData = databaseManager.isHotelTableEmpty();
        if(emptyData) {
            databaseManager.insertHotel(1, "Hilton Toronto", "43.650074606202445", "-79.38558647373958", "145 Richmond St W, Toronto, ON M5H 2L2",
                    "(416) 869-3456", 3);
            databaseManager.insertHotel(2, "Novotel Toronto Centre", "43.64826638444023", "-79.37594523891353",
                    "45 The Esplanade, Toronto, ON M5E 1W2",
                    "+14163678900", 2);
            databaseManager.insertHotel(3, "La Quinta Inn & Suites by Wyndham Oshawa", "43.900849604566716", "-78.8621463175336",
                    "63 King St E, Oshawa, ON L1H 1B4",
                    "+19055711333", 3);
            databaseManager.insertHotel(4, "Courtyard by Marriott Oshawa", "43.89453689589726", "-78.81724963908988",
                    "1011 Bloor St E, Oshawa, ON L1H 7K6",
                    "+19055765101", 2);
            databaseManager.insertUser(3, "mar@gmail.com", "1234", "Mar",
                    "Koval", "99883344");
        }

        Uri videoUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.login_video);
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(mp -> {
            mp.setVolume(0, 0);
        });

        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.setVolume(0, 0);
            }
        });

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = view.findViewById(R.id.email);
                pass = view.findViewById(R.id.password);
                valid = databaseManager.isValidUser(email.getText().toString(),pass.getText().toString());
                if(email.getText().toString().equals("") || pass.getText().toString().equals(""))
                {
                    email.setError("please fill the feilds");
                }else{
                    if(valid){
                        NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_LoginFragment_to_MainFragment);
                        Toast.makeText(requireContext(),"Login successful",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(requireContext(),"Login faild",Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}