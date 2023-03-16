package com.example.perimetergates.Inventory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.perimetergates.Intel.StoreAda;
import com.example.perimetergates.Intel.StoreMode;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Supply extends AppCompatActivity {
    Button button, Send, Hits;
    EditText Quantity;
    Spinner productType, Typed;
    String form;
    StoreMode gateMode;
    ArrayList<StoreMode> SubjectList = new ArrayList<>();
    StoreAda gateAda;
    ListView listView;
    SearchView searchView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Manage Supplies");
        setContentView(R.layout.activity_supply);
        button = findViewById(R.id.inMake);
        button.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(Supply.this);
            Rect rect = new Rect();
            Window window = Supply.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            LayoutInflater layoutInflater = (LayoutInflater) Supply.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.order_sup, null);
            layout.setMinimumWidth((int) (rect.width() * 0.9f));
            layout.setMinimumHeight((int) (rect.height() * 0.04f));
            Quantity = layout.findViewById(R.id.quantityAmount);
            productType = layout.findViewById(R.id.spnCate);
            Typed = layout.findViewById(R.id.spnType);
            Send = layout.findViewById(R.id.btnPost);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Product, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            productType.setAdapter(adapter);
            productType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String mSpinner = productType.getSelectedItem().toString();
                    if (mSpinner.equals("Gates") || mSpinner.equals("Doors") || mSpinner.equals("Windows")) {
                        Typed.setVisibility(View.VISIBLE);
                        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(Supply.this, R.array.Majors, android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Typed.setAdapter(adapter2);
                    } else if (mSpinner.equals("Grills")) {
                        Typed.setVisibility(View.VISIBLE);
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(Supply.this, R.array.Grills, android.R.layout.simple_spinner_item);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Typed.setAdapter(adapter1);
                    } else if (mSpinner.equals("Shoe Rack")) {
                        Typed.setVisibility(View.VISIBLE);
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(Supply.this, R.array.Shoe, android.R.layout.simple_spinner_item);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Typed.setAdapter(adapter1);
                    } else {
                        Typed.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            Send.setOnClickListener(vv -> {
                final String myQnty = Quantity.getText().toString().trim();
                final String myCate = productType.getSelectedItem().toString().trim();
                if (!myCate.equals("Select Category")) {
                    form = Typed.getSelectedItem().toString().trim();
                }
                if (myQnty.isEmpty()) {
                    Toast.makeText(this, "Order Quantity is required", Toast.LENGTH_SHORT).show();
                } else if (myCate.equals("Select Category")) {
                    Toast.makeText(this, "Please select Category", Toast.LENGTH_SHORT).show();
                } else if (form.equals("Select Type")) {
                    Toast.makeText(this, "Type not selected", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.addTend,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String mess = jsonObject.getString("message");
                                    int Status = jsonObject.getInt("success");
                                    if (Status == 1) {
                                        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Supply.class));
                                        finish();
                                    } else if (Status == 0) {
                                        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                                }
                            }, error -> {
                        Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show();
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("category", myCate);
                            params.put("quantity", myQnty);
                            params.put("type", form);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            });
            alert.setTitle("Post Purchase Order");
            alert.setView(layout);
            alert.setNegativeButton("close", (dialog, id) -> dialog.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        });
        searchView = findViewById(R.id.search);
        Hits = findViewById(R.id.showMe);
        listView = findViewById(R.id.listedOne);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            gateMode = (StoreMode) parent.getItemAtPosition(position);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            Rect rect = new Rect();
            Window window = Supply.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            LayoutInflater layoutInflater = (LayoutInflater) Supply.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.faithful, null);
            layout.setMinimumWidth((int) (rect.width() * 0.9f));
            layout.setMinimumHeight((int) (rect.height() * 0.04f));
            imageView = layout.findViewById(R.id.circleView);
            Glide.with(Supply.this).load(gateMode.getImage()).into(imageView);
            alert.setTitle("Verify Product");
            alert.setView(layout);
            alert.setMessage("MaximumQty " + gateMode.getQuantity() + " units");
            alert.setPositiveButton("approve", (dialog, idx) -> {
                AlertDialog.Builder aler = new AlertDialog.Builder(this);
                aler.setTitle("Enter New Price tag");
                final EditText editText = new EditText(this);
                editText.setHint("price tag");
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                aler.setView(editText);
                aler.setPositiveButton("submit", (oi, i) -> {
                    final String myP = editText.getText().toString().trim();
                    if (myP.isEmpty()) {
                        Toast.makeText(this, "Price tag required", Toast.LENGTH_SHORT).show();
                    } else if (Float.parseFloat(gateMode.getPrice()) < Float.parseFloat(myP)) {
                        Toast.makeText(this, "Price tag is smaller than supplier price", Toast.LENGTH_SHORT).show();
                    } else {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(new StringRequest(Request.Method.POST, TeaRoom.transitional,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String mess = jsonObject.getString("message");
                                        int Status = jsonObject.getInt("success");
                                        if (Status == 1) {
                                            Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), Supply.class));
                                            finish();
                                        } else if (Status == 0) {
                                            Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                                    }
                                }, error -> {
                            Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show();
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("category", gateMode.getCategory());
                                params.put("type", gateMode.getType());
                                params.put("quantity", gateMode.getQuantity());
                                params.put("description", gateMode.getDescription());
                                params.put("price", myP);
                                params.put("stock_id", gateMode.getStock_id());
                                params.put("pur_id", gateMode.getPur_id());
                                params.put("status", "Approved");;
                                return params;
                            }
                        });
                    }
                });
                aler.setNegativeButton("close", (oi, i) -> oi.cancel());
                AlertDialog baby = aler.create();
                baby.setCancelable(false);
                baby.setCanceledOnTouchOutside(false);
                baby.show();
            });
            alert.setNeutralButton("reject", (dialog, idx) -> {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.updateStock,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String mess = jsonObject.getString("message");
                                int Status = jsonObject.getInt("success");
                                if (Status == 1) {
                                    Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Supply.class));
                                    finish();
                                } else if (Status == 0) {
                                    Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }, error -> {
                    Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show();
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("stock_id", gateMode.getStock_id());
                        params.put("quantity", gateMode.getQuantity());
                        params.put("pur_id", gateMode.getPur_id());
                        params.put("category", gateMode.getCategory());
                        params.put("type", gateMode.getType());
                        params.put("price", gateMode.getPrice());
                        params.put("status", "Rejected");
                        return params;
                    }//category,type,image,quantity,price
                };   //category,type,qty,pur_id,quantity,price,description,image
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            });
            alert.setNegativeButton("close", (dialog, idx) -> dialog.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        });
        Hits.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SupplyHist.class));
        });
        practical();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.getSupp,
                response -> {
                    try {
                        StoreMode subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String stock_id = jsonObject.getString("stock_id");
                                String pur_id = jsonObject.getString("pur_id");
                                String category = jsonObject.getString("category");
                                String type = jsonObject.getString("type");
                                String quantity = jsonObject.getString("quantity");
                                String price = jsonObject.getString("price");
                                String description = jsonObject.getString("description");
                                String image = jsonObject.getString("image");
                                String imagery = TeaRoom.img + image;
                                String supplier = jsonObject.getString("supplier");
                                String status = jsonObject.getString("status");
                                String pay = jsonObject.getString("pay");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new StoreMode(stock_id, pur_id, category, type, quantity, price, description, imagery, supplier, status, pay, reg_date);
                                SubjectList.add(subject);
                            }
                            gateAda = new StoreAda(Supply.this, R.layout.insider, SubjectList);
                            listView.setAdapter(gateAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    gateAda.getFilter().filter(newText);
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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}