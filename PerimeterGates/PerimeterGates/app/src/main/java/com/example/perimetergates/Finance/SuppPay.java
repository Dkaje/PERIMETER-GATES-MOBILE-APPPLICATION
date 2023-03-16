package com.example.perimetergates.Finance;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.Intel.PrinterMe;
import com.example.perimetergates.Intel.SupPAda;
import com.example.perimetergates.Intel.SupPaModel;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SuppPay extends AppCompatActivity {
    ListView listView, listedOne;
    SearchView searchView, searchedOne;
    ArrayList<SupPaModel> SubjectLi = new ArrayList<>();
    SupPAda suppAda;
    SupPaModel suppFind;
    String suppId;
    View layer;
    Spinner spinner;
    EditText Account, Cheque;
    Button Choice, Send, Show, Hide;
    RelativeLayout relativeLayout;
    EditText Area, Street, House, MPESA;
    Button button1, button2, button3, button4;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Supply Payment");
        setContentView(R.layout.activity_supp_pay);
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            suppFind = (SupPaModel) parent.getItemAtPosition(position);
            suppId = suppFind.getSupplier();
            AlertDialog.Builder builderz = new AlertDialog.Builder(this);
            builderz.setTitle(Html.fromHtml("<font><b><i><u>Supplier_Payment</u></i></b></font>"));
            Rect reta = new Rect();
            Window window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(reta);
            LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = layoutInflater.inflate(R.layout.paye, null);
            layer.setMinimumWidth((int) (reta.width() * 0.9f));
            layer.setMinimumHeight((int) (reta.height() * 0.01f));
            button2 = layer.findViewById(R.id.demoBtn);
            builderz.setView(layer);
            if (suppFind.getDisburse().equals("Pending")) {
                button2.setText(getString(R.string.title) + "\n\nSupplierID: " + suppFind.getSupplier() + "\nAmount: KSH" + suppFind.getPayment() + "\nDisbursement: " + suppFind.getDisburse());
                builderz.setPositiveButton("make payment", (dialog, idm) -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Payment By MPESA");
                    builder.setMessage("SupplierID: " + suppFind.getSupplier() + "\nDisbursement: " + suppFind.getDisburse() + "\nPayableAmount: KSH" + suppFind.getPayment());
                    Rect retan = new Rect();
                    Window window8 = this.getWindow();
                    window8.getDecorView().getWindowVisibleDisplayFrame(retan);
                    LayoutInflater layoutInflater1 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    layer = layoutInflater1.inflate(R.layout.demo, null);
                    layer.setMinimumWidth((int) (retan.width() * 0.9f));
                    layer.setMinimumHeight((int) (retan.height() * 0.01f));
                    MPESA = layer.findViewById(R.id.mpesa);
                    button4 = layer.findViewById(R.id.btnSubmit);
                    Button btn=layer.findViewById(R.id.btnCan);
                    button4.setOnClickListener(veve -> {
                        final String muCode = MPESA.getText().toString().trim();
                        int len = muCode.length();
                        if (muCode.isEmpty()) {
                            Toast.makeText(this, "Mpesa needed", Toast.LENGTH_SHORT).show();
                        } else if (len < 10) {
                            Toast.makeText(this, "Mpesa invalid", Toast.LENGTH_SHORT).show();
                        } else {
                            StringRequest stringRequesting = new StringRequest(Request.Method.POST, TeaRoom.paySup,
                                    respon -> {
                                        try {
                                            JSONObject jsonOb = new JSONObject(respon);
                                            Log.e("response ", respon);
                                            String msg = jsonOb.getString("message");
                                            int statuses = jsonOb.getInt("success");
                                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                            if (statuses == 1) {
                                                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(getApplicationContext(), SuppPay.class));
                                                finish();
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
                                    params.put("supplier", suppFind.getSupplier());
                                    params.put("mpesa", muCode);
                                    params.put("amount", suppFind.getPayment());
                                    return params;
                                }
                            };//quantity,order,shipping,total,location,mpesa,customer,phone,reg
                            //disburse,shipper,shipment,customer
                            RequestQueue requestQueue = Volley.newRequestQueue(this);
                            requestQueue.add(stringRequesting);
                        }
                    });
                    builder.setView(layer);
                    AlertDialog alertDialog = builder.create();
                    btn.setOnClickListener(v -> {
                        alertDialog.cancel();
                    });
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                });
            } else {
                button2.setText(getString(R.string.title) + "\n\nConfirmed. \nKES" + suppFind.getPayment() + ",\nset to \nSupplier: " + suppFind.getSupplier());
                builderz.setPositiveButton("print", (dial, idm) -> {
                    print();
                });
            }
            builderz.setNegativeButton("close", (dialo, iof) -> {
                startActivity(new Intent(getApplicationContext(), SuppPay.class));
                finish();
            });
            AlertDialog alertDialog = builderz.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });
        practical();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void print() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrinterMe(this, layer.findViewById(R.id.monti)), null);
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.getSuppl,
                response -> {
                    try {
                        SupPaModel subject;
                        SubjectLi = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String supplier = jsonObject.getString("supplier");
                                String payment = jsonObject.getString("payment");
                                String disburse = jsonObject.getString("disburse");
                                subject = new SupPaModel(supplier, payment, disburse);
                                SubjectLi.add(subject);
                            }
                            suppAda = new SupPAda(SuppPay.this, R.layout.sup, SubjectLi);
                            listView.setAdapter(suppAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    suppAda.getFilter().filter(newText);
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