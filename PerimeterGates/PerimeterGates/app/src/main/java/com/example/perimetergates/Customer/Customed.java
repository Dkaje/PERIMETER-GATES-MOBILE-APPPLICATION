package com.example.perimetergates.Customer;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.Intel.CustMod;
import com.example.perimetergates.Intel.CustSess;
import com.example.perimetergates.Intel.OrderMode;
import com.example.perimetergates.Intel.OrderUpp;
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

public class Customed extends AppCompatActivity {
    CustSess blacSess;
    CustMod staffMod;
    ListView listView;
    SearchView searchView;
    ArrayList<OrderMode> viewUsers = new ArrayList<>();
    OrderMode payerMode;
    OrderUpp viewAda;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Order Specifications");
        setContentView(R.layout.activity_customed);
        blacSess = new CustSess(getApplicationContext());
        staffMod = blacSess.getCustDetails();
        listView = findViewById(R.id.listedOne);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            payerMode = (OrderMode) parent.getItemAtPosition(position);
            showCategory(Customed.this, position);
        });
        button = findViewById(R.id.btnPrint);
        button.setOnClickListener(v -> {
            printPdf();
        });
        practical();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.basement,
                response -> {
                    try {
                        OrderMode subject;
                        viewUsers = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String order_id = jsonObject.getString("order_id");
                                String mpesa = jsonObject.getString("mpesa");
                                String amount = jsonObject.getString("amount");
                                String ship = jsonObject.getString("ship");
                                String cost = jsonObject.getString("cost");
                                String quantity = jsonObject.getString("quantity");
                                String unit = jsonObject.getString("unit");
                                String cust_id = jsonObject.getString("cust_id");
                                String name = jsonObject.getString("name");
                                String phone = jsonObject.getString("phone");
                                String location = jsonObject.getString("location");
                                String category = jsonObject.getString("category");
                                String type = jsonObject.getString("type");
                                String material = jsonObject.getString("material");
                                String coating = jsonObject.getString("coating");
                                String dimension = jsonObject.getString("dimension");
                                String set_date = jsonObject.getString("set_date");
                                String mode = jsonObject.getString("mode");
                                String finance = jsonObject.getString("finance");
                                String remarks = jsonObject.getString("remarks");
                                String blacksmith = jsonObject.getString("blacksmith");
                                String dispatcher = jsonObject.getString("dispatcher");
                                String driver = jsonObject.getString("driver");
                                String drive = jsonObject.getString("drive");
                                String customer = jsonObject.getString("customer");
                                String date = jsonObject.getString("date");
                                subject = new OrderMode(order_id, mpesa, amount, ship, cost, quantity, unit, cust_id, name, phone, location, category, type, material, coating, dimension, set_date, mode,
                                        finance, remarks, blacksmith, dispatcher, driver, drive, customer, date);
                                viewUsers.add(subject);
                            }
                            button.setVisibility(View.VISIBLE);
                            viewAda = new OrderUpp(Customed.this, R.layout.minor, viewUsers);
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

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("customer", staffMod.getCust_id());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showCategory(Customed orderYes, int position) {
        OrderMode product = viewUsers.get(position);
        String mess="";
        if (product.getCategory().equals("Grills")){
            mess=product.getQuantity()+" grills";
        }else if (product.getCategory().equals("Gates")){
            mess=product.getQuantity()+" gates";
        }else if (product.getCategory().equals("Doors")){
            mess=product.getQuantity()+" doors";
        }else if (product.getCategory().equals("Windows")){
            mess=product.getQuantity()+" window (S)";
        }else if (product.getCategory().equals("Shoe Rack")){
            mess=product.getQuantity()+" shoe(s)";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(orderYes);
        builder.setTitle("Order Specifications");
        if (product.getShip().equals("0")){
            builder.setMessage("MPESA :" + product.getMpesa() + "\nUnitCost :KES" + product.getUnit() + "\nQuantity :" + mess + "\nTotalOrder :KES" + product.getCost() + "\nTotalAmount : KES" + product.getAmount() +"\n\nCategory :" + product.getCategory() + "\nType :" + product.getType() + "\nQuantity :" + mess + "\nMaterial :" + product.getMaterial() + "\nCoating :" + product.getCoating() + "\n\nCustomer :" + product.getName() + "\nCustomerID :" + product.getCust_id() + "\nPhone :" + product.getPhone() + "\nDeadline: " + product.getSet_date() + "\nStatus: " + product.getFinance());
        }else{
            builder.setMessage("MPESA :" + product.getMpesa() + "\nUnitCost :KES" + product.getUnit() + "\nQuantity :" + mess + "\nTotalOrder :KES" + product.getCost() + "\nShipping : KES" + product.getShip() +  "\nTotalAmount : KES" + product.getAmount() +"\n\nCategory :" + product.getCategory() + "\nType :" + product.getType() + "\nQuantity :" + mess + "\nMaterial :" + product.getMaterial() + "\nCoating :" + product.getCoating() + "\n\nCustomer :" + product.getName() + "\nCustomerID :" + product.getCust_id() + "\nPhone :" + product.getPhone() + "\nLocation :" + product.getLocation() + "\nDeadline: " + product.getSet_date() + "\nStatus: " + product.getFinance());
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
}