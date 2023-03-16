package com.example.perimetergates.Suplier;

import android.content.Context;
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
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.example.perimetergates.Intel.PrinterMe;
import com.example.perimetergates.Intel.StoreAda;
import com.example.perimetergates.Intel.StoreMode;
import com.example.perimetergates.Intel.SupMod;
import com.example.perimetergates.Intel.SupSess;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SuppHistory extends AppCompatActivity {
    StoreMode gateMode;
    ArrayList<StoreMode> SubjectList = new ArrayList<>();
    StoreAda gateAda;
    ListView listView;
    SearchView searchView;
    ImageView imageView;
    SupSess blacSess;
    SupMod staffMod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("My Supplies");
        setContentView(R.layout.activity_supp_history);
        blacSess = new SupSess(getApplicationContext());
        staffMod = blacSess.getSupDetails();
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.listedOne);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            gateMode = (StoreMode) parent.getItemAtPosition(position);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            Rect rect = new Rect();
            Window window = SuppHistory.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            LayoutInflater layoutInflater = (LayoutInflater) SuppHistory.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.faithful, null);
            layout.setMinimumWidth((int) (rect.width() * 0.9f));
            layout.setMinimumHeight((int) (rect.height() * 0.04f));
            imageView = layout.findViewById(R.id.circleView);
            Glide.with(SuppHistory.this).load(gateMode.getImage()).into(imageView);
            alert.setTitle("Product record");
            alert.setView(layout);
            alert.setMessage("MaximumQty " + gateMode.getQuantity() + " units\n\nStatus: "+gateMode.getStatus());
            alert.setNegativeButton("close", (dialog, idx) -> dialog.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        });

        practical();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.getPersonal,
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
                            gateAda = new StoreAda(SuppHistory.this, R.layout.insider, SubjectList);
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
                params.put("supplier", staffMod.getSupp_id());
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