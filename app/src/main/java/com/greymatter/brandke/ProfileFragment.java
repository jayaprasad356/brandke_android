package com.greymatter.brandke;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.greymatter.brandke.helper.Session;

public class ProfileFragment extends Fragment {

    TextView order_txt;
    TextView cart_txt;
    TextView notification_txt;
    TextView password_txt;
    TextView wallet_txt;
    TextView contact_txt,tvlogout;
    Activity activity;
    Session session;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_profile, container, false);

        activity = getActivity();
        session = new Session(activity);

        order_txt = rootview.findViewById(R.id.order_txt);
        cart_txt = rootview.findViewById(R.id.cart_txt);
        notification_txt = rootview.findViewById(R.id.notification_txt);
        password_txt = rootview.findViewById(R.id.password_txt);
        wallet_txt = rootview.findViewById(R.id.wallet_txt);
        contact_txt = rootview.findViewById(R.id.contact_txt);
        tvlogout = rootview.findViewById(R.id.tvlogout);

        order_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OrderActivity.class);
                startActivity(intent);
            }
        });

        cart_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CartActivity.class);
                startActivity(intent);
            }
        });

        notification_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NotificationActivity.class);
                startActivity(intent);
            }
        });

        password_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                bottomSheetDialog.setContentView(R.layout.bottom_password_change);
                bottomSheetDialog.show();
            }
        });

        wallet_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WalletActivity.class);
                startActivity(intent);
            }
        });

        contact_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),contactusActivity.class);
                startActivity(intent);
            }
        });
        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser(activity);

            }
        });

        return rootview;
    }
}