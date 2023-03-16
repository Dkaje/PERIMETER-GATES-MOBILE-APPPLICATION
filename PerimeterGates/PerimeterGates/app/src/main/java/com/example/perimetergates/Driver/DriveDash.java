package com.example.perimetergates.Driver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.Intel.CartMode;
import com.example.perimetergates.Intel.CartexAda;
import com.example.perimetergates.Intel.DriMod;
import com.example.perimetergates.Intel.DriSess;
import com.example.perimetergates.Intel.InsiderAda;
import com.example.perimetergates.Intel.PayerMode;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.MainActivity;
import com.example.perimetergates.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DriveDash extends AppCompatActivity {
    DriSess blacSess;
    DriMod staffMod;
    LinearLayout pace1, pace2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blacSess = new DriSess(getApplicationContext());
        staffMod = blacSess.getDriDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + staffMod.getFname());
        setContentView(R.layout.activity_drive_dash);
        pace1 = findViewById(R.id.myPays);
        pace2 = findViewById(R.id.bkHist);
        pace1.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ReadyShip.class));
        });
        pace2.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SpeciedShip.class));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(DriveDash.this);
                builder.setTitle("My Profile");
                builder.setMessage("REG_ID: " + staffMod.getDriver_id() + "\nFname: " + staffMod.getFname() + "\nLname: " + staffMod.getLname() + "\nUsername: " + staffMod.getUsername() + "\nEmail: " + staffMod.getEmail() + "\nContact: " + staffMod.getContact());
                builder.show();
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setGravity(Gravity.TOP);
                break;
            case R.id.logger:
                blacSess.logoutDri();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}