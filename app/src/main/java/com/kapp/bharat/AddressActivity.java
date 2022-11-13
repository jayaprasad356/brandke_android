package com.kapp.bharat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddressActivity extends AppCompatActivity {

    TextView tvcontinuebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);


        tvcontinuebtn = findViewById(R.id.tvcontinuebtn);

        tvcontinuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this,CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }
}