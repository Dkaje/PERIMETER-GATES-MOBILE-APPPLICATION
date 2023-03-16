package com.example.perimetergates.Customer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perimetergates.R;

import java.util.Objects;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Payment History");
        setContentView(R.layout.activity_payment);
    }
}