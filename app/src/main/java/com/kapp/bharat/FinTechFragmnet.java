package com.kapp.bharat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kapp.bharat.fintech.AadhaarValidationActivity;
import com.kapp.bharat.fintech.PanCardValidationActivity;

public class FinTechFragmnet extends Fragment {

   Button btnAadhaar,btnPancard;

    public FinTechFragmnet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_fin_tech_fragmnet, container, false);
        btnAadhaar = root.findViewById(R.id.btnAadhaar);
        btnPancard = root.findViewById(R.id.btnPancard);

        btnAadhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AadhaarValidationActivity.class);
                startActivity(intent);
            }
        });
        btnPancard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PanCardValidationActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}