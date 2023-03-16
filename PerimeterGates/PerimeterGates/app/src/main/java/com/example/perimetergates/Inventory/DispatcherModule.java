package com.example.perimetergates.Inventory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perimetergates.R;

import java.util.Objects;

public class DispatcherModule extends AppCompatActivity {
    LinearLayout ready, specified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Shipping & Delivery");
        setContentView(R.layout.activity_dispatcher_module);
        ready = findViewById(R.id.ready);
        specified = findViewById(R.id.specified);
        ready.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Shipping.class));
        });
        specified.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SpecifiedOrder.class));
        });
    }
}