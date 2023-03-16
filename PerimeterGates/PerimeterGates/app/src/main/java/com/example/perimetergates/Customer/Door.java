package com.example.perimetergates.Customer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.perimetergates.Intel.CustMod;
import com.example.perimetergates.Intel.CustSess;
import com.example.perimetergates.Intel.GateAda;
import com.example.perimetergates.Intel.GateMode;
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

public class Door extends AppCompatActivity {
    CustSess blacSess;
    CustMod staffMod;
    GateMode gateMode;
    ArrayList<GateMode> SubjectList = new ArrayList<>();
    GateAda gateAda;
    GridView listView;
    SearchView searchView;
    ImageView imageView;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Duty Doors");
        setContentView(R.layout.activity_door);
        blacSess = new CustSess(getApplicationContext());
        staffMod = blacSess.getCustDetails();
        listView = findViewById(R.id.availableGrid);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            gateMode = (GateMode) parent.getItemAtPosition(position);
            showCategory(Door.this, position);
        });
        practical();
    }

    private void showCategory(Door grills, int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(grills);
        Rect rect = new Rect();
        Window window = grills.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        LayoutInflater layoutInflater = (LayoutInflater) grills.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.carting, null);
        layout.setMinimumWidth((int) (rect.width() * 0.9f));
        layout.setMinimumHeight((int) (rect.height() * 0.4f));
        imageView = layout.findViewById(R.id.myImagind);
        editText = layout.findViewById(R.id.cartOrder);
        button = layout.findViewById(R.id.btnCart);
        GateMode product = SubjectList.get(position);
        Glide.with(grills).load(product.getImage()).into(imageView);
        alert.setTitle("Add " + product.getCategory()+" to Cart");
        alert.setMessage("Type: " + product.getType() + "\nDescription: " + product.getDescription() + "\nQuantity: " + product.getQuantity() + " doors\nUnit Price: KSHs. " + product.getPrice());
        //id, category, description, imagery, quantity, price, reg_date
        button.setOnClickListener(v -> {
            final String myQuantity = editText.getText().toString().trim();
            if (myQuantity.isEmpty()) {
                Toast.makeText(grills, "Enter quantity", Toast.LENGTH_SHORT).show();
            }else{
                StringRequest stringRequesting = new StringRequest(Request.Method.POST, TeaRoom.addCart,
                        respon -> {
                            try {
                                JSONObject jsonOb = new JSONObject(respon);
                                Log.e("response ", respon);
                                String msg = jsonOb.getString("message");
                                int statuses = jsonOb.getInt("success");
                                if (statuses == 1) {
                                    Toast.makeText(grills, msg, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), Cart.class));
                                } else if (statuses == 0) {
                                    Toast.makeText(grills, msg, Toast.LENGTH_SHORT).show();
                                } else if (statuses == 9) {
                                    Toast.makeText(grills, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(grills, "An error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }, error -> {
                    Toast.makeText(grills, "Failed to connect", Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("quantity", myQuantity);
                        params.put("quant", product.getQuantity());
                        params.put("price", product.getPrice());
                        params.put("product", product.getId());
                        params.put("category", product.getCategory());
                        params.put("type", product.getType());
                        params.put("cust_id", staffMod.getCust_id());
                        return params;
                    }
                };//reg,serial,cust_id,product,category,price,quantity,quant,image,status,reg_date
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequesting);
            }
        });
        alert.setView(layout);
        alert.setNegativeButton("close", (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }
    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.getGates,
                response -> {
                    try {
                        GateMode subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String category = jsonObject.getString("category");
                                String type = jsonObject.getString("type");
                                String description = jsonObject.getString("description");
                                String image = jsonObject.getString("image");
                                String imagery = TeaRoom.img + image;
                                String qty = jsonObject.getString("qty");
                                String quantity = jsonObject.getString("quantity");
                                String price = jsonObject.getString("price");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new GateMode(id, category, type, description, imagery, qty, quantity, price, reg_date);
                                SubjectList.add(subject);
                            }
                            gateAda = new GateAda(Door.this, R.layout.instilled, SubjectList);
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
                params.put("category", "Doors");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
    }
}