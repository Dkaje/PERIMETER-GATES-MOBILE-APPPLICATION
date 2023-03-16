package com.example.perimetergates.Customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.perimetergates.Intel.CartYangu;
import com.example.perimetergates.Intel.CustMod;
import com.example.perimetergates.Intel.CustSess;
import com.example.perimetergates.Intel.PrinterMe;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.Intel.YanguAda;
import com.example.perimetergates.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cart extends AppCompatActivity {
    CustSess blacSess;
    CustMod staffMod;
    YanguAda produAda;
    ArrayList<CartYangu> SubjectList = new ArrayList<>();
    CartYangu prodMode;
    ListView listView;
    SearchView searchView;
    ImageView imageView;
    EditText editText, MPesa, village;
    Button button, btn, reduce, drop, exit, leave;
    ImageView add, minus;
    TextView qty;
    String orders, shipping, total;
    View layer, view;
    Spinner spinner;
    int sumAdd;
    AlertDialog.Builder alert, builder;
    Rect rect, rect1;
    Window window, window1;
    LayoutInflater layoutInflater, layoutInflater1;
    AlertDialog alertDialog, base;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Cart");
        setContentView(R.layout.activity_cart);
        blacSess = new CustSess(getApplicationContext());
        staffMod = blacSess.getCustDetails();
        listView = findViewById(R.id.availableGrid);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        button = findViewById(R.id.myPay);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            prodMode = (CartYangu) parent.getItemAtPosition(position);
            showCategory(Cart.this, position);
        });
        practical();
        getPay();
    }

    private void getPay() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.getPayer,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                orders = jsonObject.getString("orders");
                                shipping = jsonObject.getString("shipping");
                                total = jsonObject.getString("total");
                                button.setText("pay-->OrderCost  KSHs" + orders);
                                button.setOnClickListener(v -> {
                                    payUS();
                                });
                            }
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {


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

    private void payUS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
        builder.setTitle("Order Cost");
        builder.setMessage("Cart Cost KSHs" + orders + "\n\nHow do you want to get your product(s)");
        builder.setPositiveButton("Shipped", (dialog, o9) -> {
            AlertDialog.Builder builder3 = new AlertDialog.Builder(Cart.this);
            builder3.setTitle("Shipped Order");
            builder3.setMessage("Our checkout & Shipment process is within two working days\nShipment costs KSHs" + shipping + "\n\nCost of your Cart KSHs" + orders + "\n\nWhole Cost =KES" + total + "\n\nClick SHIP to proceed with directions to your delivery point");
            builder3.setPositiveButton("ship", (dialoga, o1) -> {
                AlertDialog.Builder builder4 = new AlertDialog.Builder(Cart.this);
                builder4.setTitle("Help us know your current Location");
                Rect rectd = new Rect();
                Window windowd = Cart.this.getWindow();
                windowd.getDecorView().getWindowVisibleDisplayFrame(rectd);
                LayoutInflater layoutInflaterd = (LayoutInflater) Cart.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = layoutInflaterd.inflate(R.layout.location, null);
                layer.setMinimumWidth((int) (rectd.width() * 0.9f));
                layer.setMinimumHeight((int) (rectd.height() * 0.05f));
                spinner = layer.findViewById(R.id.selectCurrent);
                editText = layer.findViewById(R.id.landmark);
                village = layer.findViewById(R.id.finalSupport);
                button = layer.findViewById(R.id.btnTrue);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.City, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String mSpinner = spinner.getSelectedItem().toString();
                        if (!mSpinner.equals("Select Location")) {
                            editText.setVisibility(View.VISIBLE);
                            editText.addTextChangedListener(new TextWatcher() {
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    String myEdit = editText.getText().toString();
                                    if (!myEdit.isEmpty()) {
                                        village.setVisibility(View.VISIBLE);
                                        village.addTextChangedListener(new TextWatcher() {
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                            }

                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                String myVillage = village.getText().toString();
                                                if (!myVillage.isEmpty()) {
                                                    button.setVisibility(View.VISIBLE);
                                                } else {
                                                    button.setVisibility(View.GONE);
                                                }
                                            }

                                            public void afterTextChanged(Editable s) {
                                            }
                                        });
                                    } else {
                                        village.setVisibility(View.GONE);
                                        button.setVisibility(View.GONE);
                                    }
                                }

                                public void afterTextChanged(Editable s) {
                                }
                            });
                        } else {
                            editText.setVisibility(View.GONE);
                            village.setVisibility(View.GONE);
                            button.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                button.setOnClickListener(vi -> {
                    final String myLocation = spinner.getSelectedItem().toString().trim();
                    final String myLand = editText.getText().toString().trim();
                    final String myVilage = village.getText().toString().trim();
                    AlertDialog.Builder builder6 = new AlertDialog.Builder(Cart.this);
                    builder6.setTitle("MPESA Payment");
                    builder6.setMessage("Buy Goods & Services\nBusiness Number 992340\n\nPayable Amount KSHs" + total + "\nEnter the MPESA code in the next screen");
                    builder6.setPositiveButton("next_screen", (dialo, i9) -> {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Cart.this);
                        alert.setTitle("MPESA CODE");
                        Rect rectx = new Rect();
                        Window windowx = Cart.this.getWindow();
                        windowx.getDecorView().getWindowVisibleDisplayFrame(rectx);
                        LayoutInflater layoutInflaterx = (LayoutInflater) Cart.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View layout = layoutInflaterx.inflate(R.layout.mpesa, null);
                        layout.setMinimumWidth((int) (rectx.width() * 0.9f));
                        layout.setMinimumHeight((int) (rectx.height() * 0.05f));
                        MPesa = layout.findViewById(R.id.mpesa);
                        button = layout.findViewById(R.id.btnSend);
                        MPesa.addTextChangedListener(new TextWatcher() {
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                String myMpesa = MPesa.getText().toString();
                                int pay = myMpesa.length();
                                if (myMpesa.isEmpty()) {
                                    button.setVisibility(View.GONE);
                                } else if (pay >= 10) {
                                    button.setVisibility(View.VISIBLE);
                                } else {
                                    button.setVisibility(View.GONE);
                                    MPesa.setError("Enter 10 Characters");
                                    MPesa.requestFocus();
                                }
                            }

                            public void afterTextChanged(Editable s) {
                            }
                        });
                        button.setOnClickListener(vf -> {
                            String myMPESA = MPesa.getText().toString().trim();
                            StringRequest stringRequesting = new StringRequest(Request.Method.POST, TeaRoom.payAll,
                                    respon -> {
                                        try {
                                            JSONObject jsonOb = new JSONObject(respon);
                                            Log.e("response ", respon);
                                            String msg = jsonOb.getString("message");
                                            int statuses = jsonOb.getInt("success");
                                            if (statuses == 1) {
                                                Toast.makeText(Cart.this, msg, Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(getApplicationContext(), Cart.class));
                                            } else if (statuses == 0) {
                                                Toast.makeText(Cart.this, msg, Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(Cart.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                        }
                                    }, error -> {
                                Toast.makeText(Cart.this, "Failed to connect", Toast.LENGTH_SHORT).show();
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("mpesa", myMPESA);
                                    params.put("amount", total);
                                    params.put("orders", orders);
                                    params.put("ship", shipping);
                                    params.put("cust_id", staffMod.getCust_id());
                                    params.put("name", staffMod.getFname() + " " + staffMod.getLname());
                                    params.put("phone", staffMod.getContact());
                                    params.put("mode", "Shipped");
                                    params.put("location", myLocation + "~~~" + myLand + "~~" + myVilage);
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(this);
                            requestQueue.add(stringRequesting);
                        });
                        alert.setView(layout);
                        alert.setNegativeButton("exit", (dialo7, i99) -> dialo7.cancel());
                        AlertDialog alertDialog = alert.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    });
                    builder6.setNegativeButton("exit", (dialo, i9) -> dialo.cancel());
                    AlertDialog alertDialog = builder6.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                });
                builder4.setView(layer);
                builder4.setNegativeButton("close", (dialo, i9) -> dialo.cancel());
                AlertDialog alertDialog = builder4.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            });
            builder3.setNegativeButton("exit", (dialo, i9) -> dialo.cancel());
            AlertDialog alertDialog = builder3.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });
        builder.setNeutralButton("not shipped", (dialog, o9) -> {
            AlertDialog.Builder builder3 = new AlertDialog.Builder(Cart.this);
            builder3.setTitle("MPESA Payment");
            builder3.setMessage("Buy Goods & Services\nBusiness Number 992340\n\nPayable Amount KSHs" + orders + "\nEnter the MPESA code in the next screen");
            builder3.setPositiveButton("next_screen", (dialo, i9) -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(Cart.this);
                alert.setTitle("MPESA CODE");
                Rect rectx = new Rect();
                Window windowx = Cart.this.getWindow();
                windowx.getDecorView().getWindowVisibleDisplayFrame(rectx);
                LayoutInflater layoutInflaterx = (LayoutInflater) Cart.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflaterx.inflate(R.layout.mpesa, null);
                layout.setMinimumWidth((int) (rectx.width() * 0.9f));
                layout.setMinimumHeight((int) (rectx.height() * 0.05f));
                MPesa = layout.findViewById(R.id.mpesa);
                button = layout.findViewById(R.id.btnSend);
                MPesa.addTextChangedListener(new TextWatcher() {
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String myMpesa = MPesa.getText().toString();
                        int pay = myMpesa.length();
                        if (myMpesa.isEmpty()) {
                            button.setVisibility(View.GONE);
                        } else if (pay >= 10) {
                            button.setVisibility(View.VISIBLE);
                        } else {
                            button.setVisibility(View.GONE);
                            MPesa.setError("Enter 10 Characters");
                            MPesa.requestFocus();
                        }
                    }

                    public void afterTextChanged(Editable s) {
                    }
                });
                button.setOnClickListener(vf -> {
                    final String myPESA = MPesa.getText().toString().trim();
                    StringRequest stringRequesting = new StringRequest(Request.Method.POST, TeaRoom.payAll,
                            respon -> {
                                try {
                                    JSONObject jsonOb = new JSONObject(respon);
                                    Log.e("response ", respon);
                                    String msg = jsonOb.getString("message");
                                    int statuses = jsonOb.getInt("success");
                                    if (statuses == 1) {
                                        Toast.makeText(Cart.this, msg, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), Cart.class));
                                    } else if (statuses == 0) {
                                        Toast.makeText(Cart.this, msg, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(Cart.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                }
                            }, error -> {
                        Toast.makeText(Cart.this, "Failed to connect", Toast.LENGTH_SHORT).show();
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("mpesa", myPESA);
                            params.put("amount", orders);
                            params.put("orders", orders);
                            params.put("ship", "0");
                            params.put("cust_id", staffMod.getCust_id());
                            params.put("name", staffMod.getFname() + " " + staffMod.getLname());
                            params.put("phone", staffMod.getContact());
                            params.put("mode", "No Shipment");
                            params.put("location", "Direct");
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequesting);
                });
                alert.setView(layout);
                alert.setNegativeButton("close", (dialo7, i99) -> dialo7.cancel());
                AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            });
            builder3.setNegativeButton("close", (dialo, i9) -> dialo.cancel());
            AlertDialog alertDialog = builder3.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });
        builder.setNegativeButton("exit", (dialo, i9) -> dialo.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.viewCart,
                response -> {
                    try {
                        CartYangu subject;
                        SubjectList = new ArrayList<>();
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
                                subject = new CartYangu(reg, serial, cust_id, product, category, type, price, quantity, imagery, status, reg_date);
                                SubjectList.add(subject);
                            }
                            produAda = new YanguAda(Cart.this, R.layout.view_cart, SubjectList);
                            listView.setAdapter(produAda);
                            button.setVisibility(View.VISIBLE);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    produAda.getFilter().filter(newText);
                                    return false;
                                }
                            });
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

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

    private void showCategory(Cart cart, int position) {
        alert = new AlertDialog.Builder(cart);
        CartYangu productMode = SubjectList.get(position);
        alert.setTitle(productMode.getCategory());
        alert.setMessage("Type :" + productMode.getType() + "\nQuantity :" + productMode.getQuantity() + "\nPrice :KES" + productMode.getPrice());
        rect = new Rect();
        window = cart.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        layoutInflater = (LayoutInflater) cart.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layer = layoutInflater.inflate(R.layout.cart_edit, null);
        layer.setMinimumWidth((int) (rect.width() * 0.9f));
        layer.setMinimumHeight((int) (rect.height() * 0.05f));
        imageView = layer.findViewById(R.id.imaged);
        reduce = layer.findViewById(R.id.btnReduce);
        drop = layer.findViewById(R.id.btnDrop);
        exit = layer.findViewById(R.id.btnClose);
        Glide.with(cart).load(productMode.getImage()).into(imageView);
        /*alert.setNeutralButton("reduce", (dialo, ik) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
            builder.setTitle("Reduce Cart Quantity");
            final EditText editText1 = new EditText(Cart.this);
            editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(editText1);
            builder.setPositiveButton("submit", (dialox, idx) -> {
                String myQuant = editText1.getText().toString().trim();
                if (myQuant.isEmpty()) {
                    Toast.makeText(cart, "Quantity is required", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequesting = new StringRequest(Request.Method.POST, TeaRoom.reduceCart,
                            respon -> {
                                try {
                                    JSONObject jsonOb = new JSONObject(respon);
                                    Log.e("response ", respon);
                                    String msg = jsonOb.getString("message");
                                    int statuses = jsonOb.getInt("success");
                                    if (statuses == 1) {
                                        Toast.makeText(Cart.this, msg, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), Cart.class));
                                    } else if (statuses == 0) {
                                        Toast.makeText(Cart.this, msg, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(Cart.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                }

                            }, error -> {
                        Toast.makeText(Cart.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("quantity", myQuant);
                            params.put("qty", productMode.getQuantity());
                            params.put("product", productMode.getProduct());
                            params.put("reg", productMode.getReg());
                            return params;
                        }
                    };//quantity,product,reg
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequesting);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });*/
        alert.setView(layer);
        alertDialog = alert.create();
        reduce.setOnClickListener(v -> {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Current Qty: (" + productMode.getQuantity() + ")");
            rect1 = new Rect();
            window1 = this.getWindow();
            window1.getDecorView().getWindowVisibleDisplayFrame(rect1);
            layoutInflater1 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater1.inflate(R.layout.qty_edit, null);
            view.setMinimumHeight((int) (rect1.height() * 0.01));
            view.setMinimumWidth((int) (rect1.width() * 0.99));
            qty = view.findViewById(R.id.myEdit);
            add = view.findViewById(R.id.btnAdd);
            minus = view.findViewById(R.id.btnMinus);
            leave = view.findViewById(R.id.btnClose);
            btn = view.findViewById(R.id.btnSave);
            builder.setView(view);
            sumAdd = Integer.parseInt(productMode.getQuantity());
            add.setOnClickListener(v1 -> {
                sumAdd++;
                qty.setText("" + sumAdd);
            });
            minus.setOnClickListener(v1 -> {
                if (sumAdd <= 1) {
                    sumAdd = 1;
                } else {
                    sumAdd--;
                }
                qty.setText("" + sumAdd);
            });
            base = builder.create();
            btn.setOnClickListener(v1 -> {
                final String myQty = qty.getText().toString().trim();
                if (myQty.equals("qty")) {
                    Toast.makeText(cart, "You edited nothing", Toast.LENGTH_SHORT).show();
                } else if (myQty.equals(productMode.getQuantity())) {
                    Toast.makeText(cart, "Up to now you have edited nothing", Toast.LENGTH_SHORT).show();
                } else {
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(new StringRequest(Request.Method.POST, TeaRoom.modify,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int stat = jsonObject.getInt("status");
                                    String msg = jsonObject.getString("message");
                                    Toast.makeText(cart, msg, Toast.LENGTH_SHORT).show();
                                    if (stat == 1) {
                                        startActivity(new Intent(getApplicationContext(), Cart.class));
                                        finish();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(cart, "an error occurred", Toast.LENGTH_SHORT).show();
                                }
                            }, error -> {
                        Toast.makeText(cart, "connection not established", Toast.LENGTH_SHORT).show();
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> para = new HashMap<>();
                            para.put("quota", myQty);//quota,quantity,product,reg
                            para.put("quantity", productMode.getQuantity());
                            para.put("product", productMode.getProduct());
                            para.put("reg", productMode.getReg());
                            return para;
                        }
                    });
                }
            });
            leave.setOnClickListener(vv -> {
                base.cancel();
            });
            base.setCanceledOnTouchOutside(false);
            base.setCancelable(false);
            base.show();
        });
        exit.setOnClickListener(v -> {
            alertDialog.cancel();
        });
        drop.setOnClickListener(v -> {
            StringRequest stringRequesting = new StringRequest(Request.Method.POST, TeaRoom.removeCart,
                    respon -> {
                        try {
                            JSONObject jsonOb = new JSONObject(respon);
                            Log.e("response ", respon);
                            String msg = jsonOb.getString("message");
                            int statuses = jsonOb.getInt("success");
                            if (statuses == 1) {
                                Toast.makeText(Cart.this, msg, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), Cart.class));
                            } else if (statuses == 0) {
                                Toast.makeText(Cart.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Cart.this, "An error occurred", Toast.LENGTH_SHORT).show();
                        }

                    }, error -> {
                Toast.makeText(Cart.this, error.toString(), Toast.LENGTH_SHORT).show();

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("quantity", productMode.getQuantity());
                    params.put("product", productMode.getProduct());
                    params.put("reg", productMode.getReg());
                    return params;
                }
            };//quantity,product,reg
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequesting);
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    /*public void increment(View v) {
        sumAdd++;
        qty.setText("" + sumAdd);
    }

    public void decrement(View v) {
        if (sumAdd <= 1) {
            sumAdd = 1;
        } else {
            sumAdd--;
        }
        qty.setText("" + sumAdd);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.print, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.printMe:
                printPdf();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void printPdf() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrinterMe(this, findViewById(R.id.relativeBoss)), null);
    }*/
}