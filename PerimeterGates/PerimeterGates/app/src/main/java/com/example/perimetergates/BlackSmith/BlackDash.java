package com.example.perimetergates.BlackSmith;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.perimetergates.Finance.OrderHistory;
import com.example.perimetergates.Intel.BlacSess;
import com.example.perimetergates.Intel.StaffMod;
import com.example.perimetergates.MainActivity;
import com.example.perimetergates.R;

import java.util.Objects;

public class BlackDash extends AppCompatActivity {
    BlacSess blacSess;
    StaffMod staffMod;
    LinearLayout button, boot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blacSess = new BlacSess(getApplicationContext());
        staffMod = blacSess.getBlackDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + staffMod.getFname());
        setContentView(R.layout.activity_black_dash);
        boot = findViewById(R.id.myPays);
        button = findViewById(R.id.bkHist);
        button.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), BlackHistory.class));
        });
        boot.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), NewOrders.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.prof:
                AlertDialog.Builder builder = new AlertDialog.Builder(BlackDash.this);
                builder.setTitle("My Profile");
                builder.setMessage("Serial: " + staffMod.getStaff_id() + "\nFname: " + staffMod.getFname() + "\nLname: " + staffMod.getLname() + "\nUsername: " + staffMod.getUsername() + "\nEmail: " + staffMod.getEmail() + "\nContact: " + staffMod.getContact() + "\nRole: " + staffMod.getRole());
                builder.show();
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setGravity(Gravity.TOP);
                break;
            case R.id.logger:
                blacSess.logoutBlack();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}