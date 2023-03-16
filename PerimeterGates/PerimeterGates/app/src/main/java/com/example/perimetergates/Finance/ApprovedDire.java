package com.example.perimetergates.Finance;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.Intel.Javling;
import com.example.perimetergates.Intel.PayerAda;
import com.example.perimetergates.Intel.PayerMode;
import com.example.perimetergates.Intel.PrinterMe;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApprovedDire extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ArrayList<PayerMode> viewUsers = new ArrayList<>();
    PayerMode payerMode;
    Javling viewAda;
    Button btn;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Approved Payments");
        setContentView(R.layout.activity_approved_dire);
        listView = findViewById(R.id.listedOne);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            payerMode = (PayerMode) parent.getItemAtPosition(position);
            showCategory(ApprovedDire.this, position);
        });
        practical();
        button = findViewById(R.id.btnPrint);
        button.setOnClickListener(v -> {
            printPdf();
        });
    }

    private void showCategory(ApprovedDire finaDash, int position) {
        PayerMode product = viewUsers.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(finaDash);
        builder.setTitle("Payment Details");
        if (product.getShip().equals("0")){
            builder.setMessage("MPESA :" + product.getMpesa() + "\nOrders :KES" + product.getOrders() + "\nTotal :KES" + product.getAmount() + "\nDate :" + product.getReg_date() + "\n\nCustomer :" + product.getName() + "\nCustomerID :" + product.getCust_id() + "\nPhone :" + product.getPhone() + "\n\nApproval_Status :" + product.getStatus());
        }else{
            builder.setMessage("MPESA :" + product.getMpesa() + "\nOrders :KES" + product.getOrders() + "\nShipping :KES" + product.getShip() + "\nTotal :KES" + product.getAmount() + "\nDate :" + product.getReg_date() + "\n\nCustomer :" + product.getName() + "\nCustomerID :" + product.getCust_id() + "\nPhone :" + product.getPhone() + "\nLocation :" + product.getLocation() + "\n\nApproval_Status :" + product.getStatus());
        }
        builder.setNegativeButton("close", (dilo, o9) -> dilo.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void printPdf() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrinterMe(this, findViewById(R.id.relativeBoss)), null);
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.approvedPay,
                response -> {
                    try {
                        PayerMode subject;
                        viewUsers = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String payid = jsonObject.getString("payid");
                                String entry = jsonObject.getString("entry");
                                String mpesa = jsonObject.getString("mpesa");
                                String amount = jsonObject.getString("amount");
                                String orders = jsonObject.getString("orders");
                                String ship = jsonObject.getString("ship");
                                String cust_id = jsonObject.getString("cust_id");
                                String name = jsonObject.getString("name");
                                String phone = jsonObject.getString("phone");
                                String mode = jsonObject.getString("mode");
                                String location = jsonObject.getString("location");
                                String status = jsonObject.getString("status");
                                String driver = jsonObject.getString("driver");
                                String drive = jsonObject.getString("drive");
                                String customer = jsonObject.getString("customer");
                                String comment = jsonObject.getString("comment");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new PayerMode(payid, entry, mpesa, amount, orders, ship, cust_id, name, phone, mode, location, status, driver, drive, customer, comment, reg_date);
                                viewUsers.add(subject);
                            }
                            button.setVisibility(View.VISIBLE);
                            viewAda = new Javling(ApprovedDire.this, R.layout.minor, viewUsers);
                            listView.setAdapter(viewAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    viewAda.getFilter().filter(newText);
                                    return false;
                                }
                            });
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            Toast.makeText(this, "Poor connection", Toast.LENGTH_SHORT).show();

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}