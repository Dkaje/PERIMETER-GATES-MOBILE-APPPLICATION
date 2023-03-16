package com.example.perimetergates.Customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.Intel.CartMode;
import com.example.perimetergates.Intel.CartexAda;
import com.example.perimetergates.Intel.CustMod;
import com.example.perimetergates.Intel.CustSess;
import com.example.perimetergates.Intel.InsiderAda;
import com.example.perimetergates.Intel.PayerMode;
import com.example.perimetergates.Intel.PrinterMe;
import com.example.perimetergates.Intel.RatedAda;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Delivery extends AppCompatActivity {
    SearchView searchView;
    Button button;
    ArrayList<PayerMode> viewUsers = new ArrayList<>();
    PayerMode payerMode;
    RatedAda viewAda;
    ListView listView, lister;
    ArrayList<CartMode> cartexes = new ArrayList<>();
    CartexAda cartexAda;
    View layer, layout;
    String myId;
    CustSess blacSess;
    CustMod staffMod;
    RadioGroup radioGroup;
    String myRating;
    Button submit;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Delivery & Feedback");
        setContentView(R.layout.activity_delivery);
        blacSess = new CustSess(getApplicationContext());
        staffMod = blacSess.getCustDetails();
        listView = findViewById(R.id.listedOne);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            payerMode = (PayerMode) parent.getItemAtPosition(position);
            showCategory(Delivery.this, position);
        });
        button = findViewById(R.id.btnPrint);
        button.setOnClickListener(v -> {
            printPdf();
        });
        practical();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.customerDelei,
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
                            viewAda = new RatedAda(Delivery.this, R.layout.minor, viewUsers);
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
                params.put("cust_id", staffMod.getCust_id());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showCategory(Delivery delivery, int position) {
        AlertDialog.Builder alerto = new AlertDialog.Builder(delivery);
        PayerMode product = viewUsers.get(position);
        Rect recto = new Rect();
        Window windowo = delivery.getWindow();
        windowo.getDecorView().getWindowVisibleDisplayFrame(recto);
        LayoutInflater layoutInflatero = (LayoutInflater) delivery.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = layoutInflatero.inflate(R.layout.nice_recipt, null);
        layout.setMinimumWidth((int) (recto.width() * 0.9f));
        layout.setMinimumHeight((int) (recto.height() * 0.05f));
        lister = layout.findViewById(R.id.listerS);
        lister.setTextFilterEnabled(true);
        button = layout.findViewById(R.id.verify);
        if (product.getCustomer().equals("Pending")){
            button.setText("CLICK TO CONFIRM DELIVERY");
        }else {
            button.setText("CLICK FOR MORE DETAILS");
        }

        myId = product.getEntry();
        viewMore();
        alerto.setTitle("Cart Details");
        alerto.setMessage("Customer :" + product.getName() + "\nPhone :" + product.getPhone() + "\nLocation :" + product.getLocation() + "\nDate :" + product.getReg_date());
        button.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(delivery);
            builder.setTitle("Delivery Details");
            builder.setMessage("Office Details: " + product.getStatus() + "\nDate :" + product.getReg_date() + "\n\nCustomer :" + product.getName() + "\nCustomerID :" + product.getCust_id() + "\nPhone :" + product.getPhone() + "\n\nLocation :" + product.getLocation() + "\nDriverNotification: " + product.getDrive() + "\nCustomerAcceptance: " + product.getCustomer());
            builder.setNegativeButton("close", (dilo, o9) -> {
                startActivity(new Intent(getApplicationContext(), Delivery.class));
            });
            if (product.getCustomer().equals("Pending")) {
                builder.setPositiveButton("confirm", (dilo, o9) -> {
                    AlertDialog.Builder builderf = new AlertDialog.Builder(delivery);
                    Rect rec = new Rect();
                    Window window = delivery.getWindow();
                    window.getDecorView().getWindowVisibleDisplayFrame(rec);
                    LayoutInflater inflat = (LayoutInflater) delivery.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    layer = inflat.inflate(R.layout.feed, null);
                    layer.setMinimumWidth((int) (rec.width() * 0.9f));
                    layer.setMinimumHeight((int) (rec.height() * 0.05f));
                    radioGroup = layer.findViewById(R.id.radioType);
                    builderf.setPositiveButton("submit", (dialogs, idf) -> {
                        myRating = ((RadioButton) layer.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                        if (myRating.equals("Check Status")) {
                            Toast.makeText(delivery, "Please select your rating", Toast.LENGTH_SHORT).show();
                        } else {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.customDeliv,
                                    response -> {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String msg = jsonObject.getString("message");
                                            int status = jsonObject.getInt("success");
                                            if (status == 1) {
                                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), Delivery.class));
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
                                    params.put("payid", product.getPayid());
                                    params.put("customer", myRating);
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(stringRequest);
                        }
                    });
                    builderf.setView(layer);
                    builderf.setTitle("Confirm Delivery");
                    builderf.setNegativeButton("exit", (dialogInterface17, i17) -> dialogInterface17.cancel());
                    final AlertDialog alertDialog = builderf.create();
                    alertDialog.show();

                });
            }
            /*builder.setNeutralButton("feedback", (dilo, o9) -> {
                AlertDialog.Builder builderf = new AlertDialog.Builder(delivery);
                Rect rec = new Rect();
                Window window = delivery.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rec);
                LayoutInflater inflat = (LayoutInflater) delivery.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = inflat.inflate(R.layout.feedback, null);
                layer.setMinimumWidth((int) (rec.width() * 0.9f));
                layer.setMinimumHeight((int) (rec.height() * 0.05f));
                radioGroup = layer.findViewById(R.id.radioType);
                submit = layer.findViewById(R.id.subPro);
                Button btn=layer.findViewById(R.id.subClose);
                editText = layer.findViewById(R.id.leavecomment);
                submit.setOnClickListener(vr -> {
                    String myLEav = editText.getText().toString().trim();
                    myRating = ((RadioButton) layer.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    if (myRating.equals("Check Your Rating")) {
                        Toast.makeText(delivery, "Please select your rating", Toast.LENGTH_SHORT).show();
                    } else if (myLEav.isEmpty()) {
                        Toast.makeText(delivery, "Leave some comment", Toast.LENGTH_SHORT).show();
                    } else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.addRating,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), Delivery.class));
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
                                params.put("customer", staffMod.getCust_id());
                                params.put("phone", staffMod.getContact());
                                params.put("name", staffMod.getFname());
                                params.put("rating", myRating);
                                params.put("message", myLEav);
                                return params;
                            }
                        };//payid,customer,phone,name,rating,message
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(stringRequest);
                    }
                });
                builderf.setView(layer);
                builderf.setTitle("Give us Feedback");
                final AlertDialog alertDialog = builderf.create();
                btn.setOnClickListener(v1 -> {
                    alertDialog.cancel();
                });
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            });*/
            AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });
        alerto.setView(layout);
        alerto.setNeutralButton("close", (dialogo, ido) -> {
            startActivity(new Intent(getApplicationContext(), Delivery.class));
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
                            cartexAda = new CartexAda(Delivery.this, R.layout.nice_to, cartexes);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void printPdf() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrinterMe(this, findViewById(R.id.relativeBoss)), null);
    }
}