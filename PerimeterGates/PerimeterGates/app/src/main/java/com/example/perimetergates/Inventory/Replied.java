package com.example.perimetergates.Inventory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.Intel.BoomDemo;
import com.example.perimetergates.Intel.FeedAda;
import com.example.perimetergates.Intel.FeedMode;
import com.example.perimetergates.Intel.FireWork;
import com.example.perimetergates.Intel.NewMess;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Replied extends AppCompatActivity {
    String myCust;
    Button imageButton;
    ArrayList<FeedMode> viewUsers = new ArrayList<>();
    private List<FeedMode> list;
    private List<FireWork> lister;
    FeedMode payerMode;
    FireWork fireWork;
    FeedAda viewAda;
    NewMess newMess;
    BoomDemo boomDemo;
    EditText editText, message;
    RecyclerView recyclerView, recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent inte = getIntent();
        if (inte != null) {
            myCust = inte.getStringExtra("customer");
        }
        Objects.requireNonNull(getSupportActionBar()).setTitle("SenderID:" +myCust);
        setContentView(R.layout.activity_replied);
        imageButton = findViewById(R.id.submit_button1);
        message = findViewById(R.id.reviewing);
        recyclerView = findViewById(R.id.britLitovsk);
        recycle=findViewById(R.id.crystal);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getApplicationContext(), 1);
        recycle.setLayoutManager(layoutManager1);
        lister = new ArrayList<>();
        message.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ourMess = message.getText().toString();
                if (ourMess.isEmpty()) {
                    imageButton.setVisibility(View.GONE);
                } else {
                    imageButton.setVisibility(View.VISIBLE);
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });
        practical();
        boom();
        imageButton.setOnClickListener(v -> {
            final String myMess=message.getText().toString().trim();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.replyMe,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("message");
                            int status = jsonObject.getInt("success");
                            if (status == 1) {
                                message.setText("");
                                practical();
                                boom();
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
                    params.put("customer", payerMode.getCustomer());
                    params.put("phone", payerMode.getPhone());
                    params.put("name", payerMode.getName());
                    params.put("message", myMess);
                    return params;
                }
            };//payid,customer,phone,name,rating,message
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        });
    }

    private void boom() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.getAllRep,
                response -> {
                    try {
                        Log.e("RESPONSE ", response);
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("trust");
                        if (status == 1) {
                            lister.clear();
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String customer = jsonObject.getString("customer");
                                String phone = jsonObject.getString("phone");
                                String name = jsonObject.getString("name");
                                String reply = jsonObject.getString("reply");
                                String reply_date = jsonObject.getString("reply_date");
                                fireWork = new FireWork(id, customer, phone, name, reply, reply_date);
                                lister.add(fireWork);

                            }
                            boomDemo = new BoomDemo(getApplicationContext(), lister);
                            recycle.setAdapter(boomDemo);
                            recycle.scrollToPosition(lister.size() - 1);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }, error -> {
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("customer", myCust);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.begged,
                response -> {
                    try {
                        Log.e("RESPONSE ", response);
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("trust");
                        if (status == 1) {
                            list.clear();
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String customer = jsonObject.getString("customer");
                                String phone = jsonObject.getString("phone");
                                String name = jsonObject.getString("name");
                                String rating = jsonObject.getString("rating");
                                String message = jsonObject.getString("message");
                                String reg_date = jsonObject.getString("reg_date");
                                String reply = jsonObject.getString("reply");
                                String reply_date = jsonObject.getString("reply_date");
                                payerMode = new FeedMode(id, customer, phone, name, rating, message, reg_date, reply, reply_date);
                                list.add(payerMode);

                            }
                            newMess = new NewMess(getApplicationContext(), list);
                            recyclerView.setAdapter(newMess);
                            recyclerView.scrollToPosition(list.size() - 1);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }, error -> {
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("customer", myCust);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}