package com.example.perimetergates.Finance;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.perimetergates.Intel.FinaSess;
import com.example.perimetergates.Intel.Javling;
import com.example.perimetergates.Intel.PayerMode;
import com.example.perimetergates.Intel.StaffMod;
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

public class FinaDash extends AppCompatActivity {
    FinaSess blacSess;
    StaffMod staffMod;
    LinearLayout layer1, layer2, layer3, layer4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blacSess = new FinaSess(getApplicationContext());
        staffMod = blacSess.getFinaDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + staffMod.getFname());
        setContentView(R.layout.activity_fina_dash);
        layer1 = findViewById(R.id.myPays);
        layer2 = findViewById(R.id.cusOrders);
        layer3 = findViewById(R.id.PaySupplier);
        layer4 = findViewById(R.id.myAcocunt);
        layer4.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecordKeeping.class));
        });
        layer3.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SuppPay.class));
        });
        layer2.setOnClickListener(v -> {
            CharSequence[] items = {"Pending", "Approved", "Rejected"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Payment");
            dialog.setItems(items, (dialog1, item) -> {
                if (item == 0) {
                    startActivity(new Intent(getApplicationContext(), OrderYes.class));
                }else if (item == 1) {
                    startActivity(new Intent(getApplicationContext(), OrderApproved.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), OrderHistory.class));
                }
            });
            dialog.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
            final AlertDialog alertDialog = dialog.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();

        });
        /*layer3.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PayHistory.class));
        });*/
        layer1.setOnClickListener(v -> {
            CharSequence[] items = {"Pending", "Approved", "Rejected"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Payment");
            dialog.setItems(items, (dialog1, item) -> {
                if (item == 0) {
                    startActivity(new Intent(getApplicationContext(), NewActivity.class));
                }else if (item == 1) {
                    startActivity(new Intent(getApplicationContext(), ApprovedDire.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), RejectedPay.class));
                }
            });
            dialog.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
            final AlertDialog alertDialog = dialog.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fina_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.prof:
                AlertDialog.Builder builder = new AlertDialog.Builder(FinaDash.this);
                builder.setTitle("My Profile");
                builder.setMessage("Serial: " + staffMod.getStaff_id() + "\nFname: " + staffMod.getFname() + "\nLname: " + staffMod.getLname() + "\nUsername: " + staffMod.getUsername() + "\nEmail: " + staffMod.getEmail() + "\nContact: " + staffMod.getContact() + "\nRole: " + staffMod.getRole());
                builder.show();
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setGravity(Gravity.TOP);
                break;
            case R.id.logger:
                blacSess.logoutFina();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}