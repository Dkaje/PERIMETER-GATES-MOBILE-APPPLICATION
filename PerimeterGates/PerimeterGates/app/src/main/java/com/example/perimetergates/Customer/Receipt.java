package com.example.perimetergates.Customer;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.Intel.BudaAda;
import com.example.perimetergates.Intel.CartYangu;
import com.example.perimetergates.Intel.CustMod;
import com.example.perimetergates.Intel.CustSess;
import com.example.perimetergates.Intel.PayerMode;
import com.example.perimetergates.Intel.PrinterMe;
import com.example.perimetergates.Intel.ReceiptAda;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Receipt extends AppCompatActivity {
    ListView listView, lister;
    SearchView searchView;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Button imgd;
    ReceiptAda adapterPro;
    ArrayList<PayerMode> SubjectList = new ArrayList<>();
    PayerMode payerMode;
    CustSess blacSess;
    BudaAda produAda;
    ArrayList<CartYangu> SubjectLie = new ArrayList<>();
    CartYangu prodMode;
    CustMod staffMod;
    String identity;
    TextView textView, paying;
    String req;
    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        listView = findViewById(R.id.listedGrid);
        listView.setTextFilterEnabled(true);
        toolbar = findViewById(R.id.mussaMaimuna);
        drawer = findViewById(R.id.readySerial);
        searchView = findViewById(R.id.KambiFarashuu);
        toolbar.setTitle("Receipts");
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        setSupportActionBar(toolbar);
        imgd = findViewById(R.id.printMe);
        blacSess = new CustSess(getApplicationContext());
        staffMod = blacSess.getCustDetails();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            payerMode = (PayerMode) parent.getItemAtPosition(position);
            PayerMode product = SubjectList.get(position);
            identity = product.getEntry();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            Rect rect = new Rect();
            Window window = Receipt.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            LayoutInflater layoutInflater = (LayoutInflater) Receipt.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = layoutInflater.inflate(R.layout.receipt, null);
            layout.setMinimumWidth((int) (rect.width() * 0.9f));
            layout.setMinimumHeight((int) (rect.height() * 0.08f));
            textView = layout.findViewById(R.id.myTexter);
            paying = layout.findViewById(R.id.shipper);
            lister = layout.findViewById(R.id.listerS);
            lister.setTextFilterEnabled(true);
            practicalS();
            alert.setView(layout);
            paying.setText("Orders KES" + product.getOrders() + "\nShipping: KES" + product.getShip() + "\nTotal KES" + product.getAmount());
            textView.setText("Customer: " + staffMod.getFname() + " " + staffMod.getLname() + "\nphone: " + product.getPhone() + "\nDate: " + product.getReg_date());
            alert.setPositiveButton("print pdf", (dialog, ids) -> {
                printThis();
            });
            alert.setNegativeButton("close", (dialog, ids) -> {
                startActivity(new Intent(getApplicationContext(), Receipt.class));
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        });
        PracticalPra();
        imgd.setOnClickListener(v -> {
            printPdf();
        });
    }

    private void practicalS() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.finarec,
                response -> {
                    try {
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
                                prodMode = new CartYangu(reg, serial, cust_id, product, category, type, price, quantity, imagery, status, reg_date);
                                SubjectLie.add(prodMode);
                            }
                            produAda = new BudaAda(Receipt.this, R.layout.follow_up, SubjectLie);
                            lister.setAdapter(produAda);
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
                params.put("identity", identity);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printThis() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrinterMe(this, layout.findViewById(R.id.titled)), null);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void printPdf() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrinterMe(this, findViewById(R.id.relativeBoss)), null);
    }

    private void PracticalPra() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.reci,
                response -> {
                    try {
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
                                payerMode = new PayerMode(payid, entry, mpesa, amount, orders, ship, cust_id, name, phone, mode, location, status, driver, drive, customer, comment, reg_date);
                                SubjectList.add(payerMode);

                            }
                            imgd.setVisibility(View.VISIBLE);
                            adapterPro = new ReceiptAda(Receipt.this, R.layout.rece, SubjectList);
                            listView.setAdapter(adapterPro);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    adapterPro.getFilter().filter(newText);
                                    return false;
                                }
                            });

                        } else if (success == 0) {

                            String msg = jsonObject.getString("mine");
                            new AlertDialog.Builder(Receipt.this)
                                    .setMessage(msg)
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        new AlertDialog.Builder(Receipt.this)
                                .setMessage("Error Occurred!!!")
                                .show();

                    }

                }, error -> {
            new AlertDialog.Builder(Receipt.this)
                    .setTitle("Network Error!!!")
                    .setMessage("reboot your devices if the problem persists")
                    .show();
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
}