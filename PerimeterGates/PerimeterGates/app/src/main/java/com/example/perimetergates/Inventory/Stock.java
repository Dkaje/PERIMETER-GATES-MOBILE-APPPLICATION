package com.example.perimetergates.Inventory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.perimetergates.Intel.MoreAda;
import com.example.perimetergates.Intel.StoreAda;
import com.example.perimetergates.Intel.StoreMo;
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

public class Stock extends AppCompatActivity {
    Button button, Send;
    EditText Quantity;
    Spinner productType, typedForm;
    String form;
    StoreMo gateMode;
    ArrayList<StoreMo> SubjectList = new ArrayList<>();
    MoreAda gateAda;
    ListView listView;
    SearchView searchView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Inventory Management");
        setContentView(R.layout.activity_stock);

        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.listedOne);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            gateMode = (StoreMo) parent.getItemAtPosition(position);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            Rect rect = new Rect();
            Window window = Stock.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            LayoutInflater layoutInflater = (LayoutInflater) Stock.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.faithful, null);
            layout.setMinimumWidth((int) (rect.width() * 0.9f));
            layout.setMinimumHeight((int) (rect.height() * 0.04f));
            imageView = layout.findViewById(R.id.circleView);
            Glide.with(Stock.this).load(gateMode.getImage()).into(imageView);
            alert.setTitle("Inventory Record");
            alert.setView(layout);
            alert.setMessage("MaximumQty " + gateMode.getQuantity() + " units");
            alert.setNegativeButton("close", (dialog, idx) -> dialog.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        });
        practical();
    }
        private void practical() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.getStore,
                    response -> {
                        try {
                            StoreMo subject;
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
                                    String price = jsonObject.getString("price");
                                    String image = jsonObject.getString("image");
                                    String imagery = TeaRoom.img + image;
                                    String quantity = jsonObject.getString("quantity");
                                    String reg_date = jsonObject.getString("reg_date");
                                    subject = new StoreMo(id,category,type,imagery,quantity,price,reg_date);
                                    SubjectList.add(subject);
                                }
                                gateAda = new MoreAda(Stock.this, R.layout.insider, SubjectList);
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