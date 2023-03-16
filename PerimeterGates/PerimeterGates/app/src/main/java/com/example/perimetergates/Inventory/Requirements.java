package com.example.perimetergates.Inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.perimetergates.R;

import java.util.Objects;

public class Requirements extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Customer Requests");
        setContentView(R.layout.activity_requirements);
    }
}