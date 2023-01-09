package com.kapp.bharat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kapp.bharat.Adapter.CartAdapter;
import com.kapp.bharat.Models.Cart;
import com.kapp.bharat.helper.ApiConfig;
import com.kapp.bharat.helper.Constant;
import com.kapp.bharat.helper.Session;
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

public class CheckoutActivity extends AppCompatActivity implements PaymentStatusListener {
    TextView tvName, tvAddress, tvMobile, tvSubtotal, tvDeliverycharges, tvGrandTotal;
    RecyclerView cartRecycleView;
    Activity activity;
    Session session;
    ImageView backbtn;
    CartAdapter cartAdapter;
    Button btnProceed;
    RadioButton rdCOD,rdUpi,rdWallet;
    String DeliveryCharges = "";
    String Address = "";
    String Mobile = "";
    String GrandTotal = "";
    String GrandTotal_ = "";
    TextView tvDiscount;
    boolean discountapplied = false;
    final int UPI_PAYMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        activity = CheckoutActivity.this;
        session = new Session(activity);

        tvName = findViewById(R.id.tvName);
        tvName.setText(session.getData(Constant.NAME));
        tvAddress = findViewById(R.id.tvAddress);
        tvAddress.setText(session.getData(Constant.ADDRESS));

        tvMobile = findViewById(R.id.tvMobile);
        tvMobile.setText(session.getData(Constant.MOBILE));

        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvDeliverycharges = findViewById(R.id.tvDeliverycharges);
        tvGrandTotal = findViewById(R.id.tvGrandTotal);
        cartRecycleView = findViewById(R.id.cartRecycleView);
        backbtn = findViewById(R.id.backbtn);
        btnProceed = findViewById(R.id.btnProceed);
        rdCOD = findViewById(R.id.rdCOD);
        rdUpi = findViewById(R.id.rdUpi);
        rdWallet = findViewById(R.id.rdWallet);
        tvDiscount = findViewById(R.id.tvDiscount);

        rdWallet.setText("Wallet"+"(₹ "+session.getData(Constant.BALANCE)+")");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        cartRecycleView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        cartList();

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Method = "";
                if (rdCOD.isChecked()){
                    Method = "COD";

                }else if(rdWallet.isChecked()){
                    Method = "Wallet";
                }
                else{
                    Method = "UPI";
                }
                int bal = Integer.parseInt(session.getData(Constant.BALANCE));
                int gt = Integer.parseInt(GrandTotal);
                if (Method.equals("Wallet") && bal < gt){
                    Toast.makeText(activity, "You Haven't Enough Balance", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (Method.equals("UPI")){
                    try {
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
                        String transcId = df.format(c);
                        payUsingUpi(""+Double.parseDouble(GrandTotal), Constant.UPI_ID_VAL, Constant.UPI_ID_VAL,transcId);


                    }catch (Exception e){
                        Log.d("PAYMENT_GATEWAY",e.getMessage());

                    }

                }else {
                    orderProduct(Method);
                }

            }
        });

        rdCOD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setDiscount();
            }
        });
        rdWallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setDiscount();
            }
        });
        rdUpi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setDiscount();
            }
        });


    }
    void payUsingUpi(String amount, String upiId, String name, String transcId) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)                  // YOUR UPI ID
                .appendQueryParameter("pn", upiId)                  // USE YOUR UPI ID NOT YOUR NAME
                .appendQueryParameter("mc", "1234")
                .appendQueryParameter("tid", transcId)
                .appendQueryParameter("tr", "asgdfuyas3153")
                .appendQueryParameter("am", amount)
                .appendQueryParameter("mam", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        //Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        //Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (ApiConfig.isConnected(activity)) {
            String str = data.get(0);
            //Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(activity, "Transaction successful.", Toast.LENGTH_SHORT).show();
                // Log.d("UPI", "responseStr: "+approvalRefNo);
                Toast.makeText(activity, "YOUR ORDER HAS BEEN PLACED\n THANK YOU AND ORDER AGAIN", Toast.LENGTH_LONG).show();
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(activity, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(activity, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }



    private void setDiscount()
    {
        if (rdCOD.isChecked()){
            discountapplied = false;
            GrandTotal = GrandTotal_;
            tvDiscount.setText("0");
            tvGrandTotal.setText(GrandTotal);

        }else {
            if (!discountapplied){
                discountapplied = true;
                double amount = Double.parseDouble(GrandTotal);
                double res = (amount / 100.0f) * 1;
                GrandTotal = (Integer.parseInt(GrandTotal) - Math.round(res)) + "";
                tvDiscount.setText(Math.round(res) + "");
                tvGrandTotal.setText(GrandTotal);

            }


        }
    }

    private void orderProduct(String method)
    {


        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        params.put(Constant.METHOD, method);
        params.put(Constant.DELIVERY_CHARGES, DeliveryCharges);
        params.put(Constant.ADDRESS, Address);
        params.put(Constant.MOBILE, Mobile);
        params.put(Constant.GRAND_TOTAL, GrandTotal);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(activity,MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();


                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.PLACE_ORDER_URL, params, true);


    }

    public void cartList() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        DeliveryCharges = jsonObject.getString(Constant.DELIVERY_CHARGES);
                        Mobile = jsonObject.getString(Constant.MOBILE);
                        Address = jsonObject.getString(Constant.NAME) + "," + jsonObject.getString(Constant.ADDRESS);
                        GrandTotal = jsonObject.getString(Constant.GRAND_TOTAL);
                        GrandTotal_ = jsonObject.getString(Constant.GRAND_TOTAL);
                        tvSubtotal.setText(jsonObject.getString(Constant.SUB_TOTAL));
                        tvDeliverycharges.setText(DeliveryCharges);
                        tvName.setText(session.getData(Constant.NAME));
                        tvAddress.setText(jsonObject.getString(Constant.ADDRESS));
                        tvMobile.setText(session.getData(Constant.MOBILE));
                        tvGrandTotal.setText(GrandTotal);
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.CART_ITEMS);
                        Gson g = new Gson();
                        ArrayList<Cart> carts = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                Cart group = g.fromJson(jsonObject1.toString(), Cart.class);
                                carts.add(group);
                            } else {
                                break;
                            }
                        }
                        cartAdapter = new CartAdapter(activity, carts,"checkout");
                        cartRecycleView.setAdapter(cartAdapter);

                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.CHECKOUT_URL, params, true);


    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {

    }

    @Override
    public void onTransactionSuccess() {
        orderProduct("UPI");

    }

    @Override
    public void onTransactionSubmitted() {

    }

    @Override
    public void onTransactionFailed() {
        Toast.makeText(activity, "Payment Failed", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onTransactionCancelled() {

    }

    @Override
    public void onAppNotFound() {

    }
}