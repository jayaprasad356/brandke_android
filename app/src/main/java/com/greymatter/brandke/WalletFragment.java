package com.greymatter.brandke;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greymatter.brandke.Adapter.WalletAdapter;
import com.greymatter.brandke.Models.Wallet;
import com.greymatter.brandke.helper.ApiConfig;
import com.greymatter.brandke.helper.Constant;
import com.greymatter.brandke.helper.Session;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class WalletFragment extends Fragment  implements PaymentStatusListener{


    private RecyclerView recyclerView;
    Activity activity;
    Session session;
    WalletAdapter walletAdapter;
    private CardView cvRecharge,CvCancel,CvRechargeInDialog;
    private EditText etAmount,etMessage;
    TextView tvBalance,tvWalletbtn;
    AlertDialog alertDialog;
    String Amount = "";

    ImageView backbtn;



    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);


        activity = getActivity();
        session = new Session(activity);



        tvWalletbtn = root.findViewById(R.id.tvWalletbtn);
        tvBalance = root.findViewById(R.id.tvBalance);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));


        tvBalance.setText("₹ "+session.getData(Constant.BALANCE));



        tvWalletbtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            ViewGroup viewGroup = root.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog, viewGroup, false);
            EditText etAmount = dialogView.findViewById(R.id.etAmount);
            CardView btnRecharge = dialogView.findViewById(R.id.cvRecharge);
            CardView btnCancel = dialogView.findViewById(R.id.cvCancel);
            builder.setView(dialogView);
            alertDialog = builder.create();

            btnRecharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!etAmount.getText().toString().trim().equals("")){
                        Amount = etAmount.getText().toString().trim();
                        try {
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
                            String transcId = df.format(c);
                            makePayment(""+Double.parseDouble(Amount), Constant.UPI_ID_VAL, session.getData(Constant.NAME), "Amount", transcId);


                        }catch (Exception e){
                            Log.d("PAYMENT_GATEWAY",e.getMessage());

                        }
                    }


                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });


            alertDialog.show();
        });
        walletList();



        return root;
    }

    private void makePayment(String amount, String upi, String name, String desc, String transactionId) {
        // on below line we are calling an easy payment method and passing
        // all parameters to it such as upi id,name, description and others.
        final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                .with(getActivity())
                // on below line we are adding upi id.
                .setPayeeVpa(upi)
                // on below line we are setting name to which we are making oayment.
                .setPayeeName(name)
                // on below line we are passing transaction id.
                .setTransactionId(transactionId)
                // on below line we are passing transaction ref id.
                .setTransactionRefId(transactionId)
                // on below line we are adding description to payment.
                .setDescription(desc)
                // on below line we are passing amount which is being paid.
                .setAmount(amount)
                // on below line we are calling a build method to build this ui.
                .build();
        // on below line we are calling a start
        // payment method to start a payment.
        easyUpiPayment.startPayment();

        // on below line we are calling a set payment
        // status listener method to call other payment methods.
        easyUpiPayment.setPaymentStatusListener((PaymentStatusListener) this);
    }


    private void walletList()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Wallet> wallets = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                Wallet group = g.fromJson(jsonObject1.toString(), Wallet.class);
                                wallets.add(group);
                            } else {
                                break;
                            }
                        }
                        walletAdapter = new WalletAdapter(activity, wallets);
                        recyclerView.setAdapter(walletAdapter);

                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.WALLET_LIST, params, true);

    }


    private void rechargeWallet()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.TYPE,"credit");
        params.put(Constant.AMOUNT,Amount);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        alertDialog.dismiss();
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        session.setData(Constant.BALANCE,jsonArray.getJSONObject(0).getString(Constant.BALANCE));
                        tvBalance.setText("₹ "+session.getData(Constant.BALANCE));
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, activity, Constant.WALLET_URL, params,true);
    }


    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {

    }

    @Override
    public void onTransactionSuccess() {
        rechargeWallet();

    }

    @Override
    public void onTransactionSubmitted() {

    }

    @Override
    public void onTransactionFailed() {

    }

    @Override
    public void onTransactionCancelled() {

    }

    @Override
    public void onAppNotFound() {

    }

}