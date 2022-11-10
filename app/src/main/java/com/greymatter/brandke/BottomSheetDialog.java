package com.greymatter.brandke;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.greymatter.brandke.helper.ApiConfig;
import com.greymatter.brandke.helper.Constant;
import com.greymatter.brandke.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BottomSheetDialog extends BottomSheetDialogFragment {


    EditText etoldPassword,etNewpassword,etConfirmpassword;
    Activity activity;
    Session session;
    ImageView algo_button;
    Button course_button;

    public BottomSheetDialog(Activity activity) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.bottom_password_change,
                container, false);

        activity = getActivity();
        session = new Session(getActivity());

         algo_button = v.findViewById(R.id.closebtn);
        course_button = v.findViewById(R.id.btchange);
        etoldPassword = v.findViewById(R.id.etoldPassword);
         etNewpassword = v.findViewById(R.id.etNewpassword);
         etConfirmpassword = v.findViewById(R.id.etConfirmpassword);

        algo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                Toast.makeText(getActivity(),
//                                "Algorithm Shared", Toast.LENGTH_SHORT)
//                        .show();
                dismiss();
            }
        });

        course_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                Toast.makeText(getActivity(),
//                                "Course Shared", Toast.LENGTH_SHORT)
//                        .show();

                if (etoldPassword.getText().toString().trim().equals("")){
                    etoldPassword.setError("empty");
                }
                else if (etNewpassword.getText().toString().trim().equals("")){
                    etNewpassword.setError("empty");
                }
                else if (etConfirmpassword.getText().toString().trim().equals("")){
                    etConfirmpassword.setError("empty");
                }
                else if (!etConfirmpassword.getText().toString().trim().equals(etNewpassword.getText().toString().trim())){
                    Toast.makeText(activity, "Password Does Not Match", Toast.LENGTH_SHORT).show();
                }
                else {

                    changePassword();
                }



            }
        });
        return v;
    }

    private void changePassword() {


        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.OLDPASSWORD,etoldPassword.getText().toString().trim());
        params.put(Constant.NEWPASSWORD,etNewpassword.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(activity, ProfileFragment.class));
                    }
                    else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.CHANGE_PASSWORD_URL, params,true);



    }

}

