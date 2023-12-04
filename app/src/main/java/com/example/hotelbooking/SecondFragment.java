package com.example.hotelbooking;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hotelbooking.databinding.FragmentSecondBinding;

import java.util.Objects;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String name = getArguments().getString("Name");
        String price = getArguments().getString("Price");
        String tax = getArguments().getString("Tax");
        String total = getArguments().getString("Total");

        binding.textView.setText(name);
        binding.textView13.setText(price);
        binding.textView15.setText(tax);
        binding.textView11.setText(total);

        binding.dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });

        binding.tmpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_MainFragment);

                Toast.makeText(getContext(), "Payment Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(requireContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // disable title
        dialog.setContentView(R.layout.card_dialog);          // layout for dialog
        dialog.setCancelable(true);                           // cancel dialog by clicking outside

        // round corners
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.rounded_corners);

        // submit button
        Button button = (Button) dialog.findViewById(R.id.enterButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e1 = (EditText) dialog.findViewById(R.id.dialogNameEditText);
                EditText e2 = (EditText) dialog.findViewById(R.id.dialogNumberEditText);
                EditText e3 = (EditText) dialog.findViewById(R.id.dialogExpiryEditText);
                EditText e4 = (EditText) dialog.findViewById(R.id.dialogCVVEditText);

                String name = String.valueOf(e1.getText());
                String cardNum = String.valueOf(e2.getText());
                String exp = String.valueOf(e3.getText());
                String cvv = String.valueOf(e4.getText());

                // update card info
                if (!name.isEmpty() && !cardNum.isEmpty() && !exp.isEmpty() && !cvv.isEmpty()) {
                    binding.cardNumber.setText(cardNum);
                    binding.cardName.setText(name);
                    binding.cardExpiry.setText(exp);
                    binding.cardCVV.setText(cvv);
                }

                dialog.dismiss();
            }
        });


        // show dialog
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}