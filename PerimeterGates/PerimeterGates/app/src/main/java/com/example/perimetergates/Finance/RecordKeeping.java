package com.example.perimetergates.Finance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.perimetergates.R;

import java.util.Objects;

public class RecordKeeping extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Account Book");
        setContentView(R.layout.activity_record_keeping);
    }
}