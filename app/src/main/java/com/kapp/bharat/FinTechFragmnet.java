package com.kapp.bharat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kapp.bharat.fintech.AadhaarValidationActivity;
import com.kapp.bharat.fintech.PanCardValidationActivity;

public class FinTechFragmnet extends Fragment {

   LinearLayout laadhaar,lpan;

    public FinTechFragmnet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_fin_tech_fragmnet, container, false);
        laadhaar = root.findViewById(R.id.laadhaar);
        lpan = root.findViewById(R.id.lpan);

        laadhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AadhaarValidationActivity.class);
                startActivity(intent);
            }
        });
        lpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PanCardValidationActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}