package com.example.perimetergates.Inventory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.Finance.FinaDash;
import com.example.perimetergates.Intel.CartMode;
import com.example.perimetergates.Intel.CartexAda;
import com.example.perimetergates.Intel.InsiderAda;
import com.example.perimetergates.Intel.Javling;
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

public class Shipping extends AppCompatActivity {
    Button btn;
    SearchView searchView;
    ArrayList<PayerMode> viewUsers = new ArrayList<>();
    PayerMode payerMode;
    InsiderAda viewAda;
    ListView listView, lister;
    ArrayList<CartMode> cartexes = new ArrayList<>();
    CartexAda cartexAda;
    View view, layout;
    String myId, Twede;
    Button button;
    ArrayList<String> drivers = new ArrayList<>();
    ArrayAdapter<String> driverAdapter;
    RequestQueue requestQueue;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Manage Shipment");
        setContentView(R.layout.activity_shipping);
        btn = findViewById(R.id.inMake);
        listView = findViewById(R.id.listedOne);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        btn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ShipHistory.class));
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            payerMode = (PayerMode) parent.getItemAtPosition(position);
            showCategory(Shipping.this, position);
        });
        button = findViewById(R.id.btnPrint);
        button.setOnClickListener(v -> {
            printPdf();
        });
        practical();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void printPdf() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrinterMe(this, findViewById(R.id.relativeBoss)), null);
    }

    private void showCategory(Shipping shipping, int position) {
        AlertDialog.Builder alerto = new AlertDialog.Builder(shipping);
        PayerMode product = viewUsers.get(position);
        Rect recto = new Rect();
        Window windowo = shipping.getWindow();
        windowo.getDecorView().getWindowVisibleDisplayFrame(recto);
        LayoutInflater layoutInflatero = (LayoutInflater) shipping.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = layoutInflatero.inflate(R.layout.nice_recipt, null);
        layout.setMinimumWidth((int) (recto.width() * 0.9f));
        layout.setMinimumHeight((int) (recto.height() * 0.05f));
        lister = layout.findViewById(R.id.listerS);
        lister.setTextFilterEnabled(true);
        button = layout.findViewById(R.id.verify);
        button.setText("CLICK TO ATTACH DRIVER");
        myId = product.getEntry();
        viewMore();
        alerto.setTitle("Products Ordered");
        if (product.getShip().equals("0")) {
            Twede = "Direct";
        } else {
            Twede = product.getLocation();
        }
        alerto.setMessage("Customer :" + product.getName() + "\nPhone :" + product.getPhone() + "\nLocation :" + Twede + "\nDate :" + product.getReg_date());
        button.setOnClickListener(v -> {
            requestQueue = Volley.newRequestQueue(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(shipping);
            builder.setTitle("Attach Driver");
            builder.setMessage("Payment Details: " + product.getStatus() + "\nDate :" + product.getReg_date() + "\n\nCustomer :" + product.getName() + "\nCustomerID :" + product.getCust_id() + "\nPhone :" + product.getPhone() + "\n\nLocation :" + product.getLocation() + "\n\nSelect Driver ID below");
            final Spinner spinned = new Spinner(shipping);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, TeaRoom.getDri, null,
                    response -> {
                        try {
                            JSONArray jsonArray = response.getJSONArray("driver");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String driverEmail = jsonObject.optString("driver_id");
                                drivers.add(driverEmail);
                                driverAdapter = new ArrayAdapter<>(Shipping.this,
                                        android.R.layout.simple_spinner_item, drivers);
                                driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinned.setAdapter(driverAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(shipping, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }, error -> {
                Toast.makeText(shipping, error.toString(), Toast.LENGTH_SHORT).show();
            });
            requestQueue.add(jsonObjectRequest);
            builder.setView(spinned);
            if (product.getDriver().equals("Pending")) {
                builder.setPositiveButton("Assign", (dilo, di) -> {
                    final String Driver = spinned.getSelectedItem().toString().trim();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.assignPay,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String msg = jsonObject.getString("message");
                                    int status = jsonObject.getInt("success");
                                    if (status == 1) {
                                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Shipping.class));
                                        finish();
                                    } else if (status == 0) {
                                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                                }
                            }, error -> {
                        Toast.makeText(this, "There was a connection error", Toast.LENGTH_SHORT).show();

                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("driver", Driver);
                            params.put("payid", product.getPayid());
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                });
            }
            builder.setNegativeButton("close", (dilo, o9) -> {
                startActivity(new Intent(getApplicationContext(), Shipping.class));
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });
        alerto.setView(layout);
        alerto.setNeutralButton("close", (dialogo, ido) -> {
            startActivity(new Intent(getApplicationContext(), Shipping.class));
        });
        AlertDialog alertDialog = alerto.create();
        alertDialog.show();
    }

    private void viewMore() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.getInsider,
                response -> {
                    try {
                        CartMode subject;
                        cartexes = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String reg = jsonObject.getString("reg");
                                String serial = jsonObject.getString("serial");
                                String cust_id = jsonObject.getString("cust_id");
                                String product = jsonObject.getString("product");
                                String category = jsonObject.getString("category");
                                String type = jsonObject.getString("type");
                                String price = jsonObject.getString("price");
                                String quantity = jsonObject.getString("quantity");
                                String image = jsonObject.getString("image");
                                String imagery = TeaRoom.img + image;
                                String status = jsonObject.getString("status");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new CartMode(reg, serial, cust_id, product, category, type, price, quantity, imagery, status, reg_date);
                                cartexes.add(subject);
                            }
                            cartexAda = new CartexAda(Shipping.this, R.layout.nice_to, cartexes);
                            lister.setAdapter(cartexAda);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("serial", myId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.babaShipper,
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
                            viewAda = new InsiderAda(Shipping.this, R.layout.minor, viewUsers);
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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}