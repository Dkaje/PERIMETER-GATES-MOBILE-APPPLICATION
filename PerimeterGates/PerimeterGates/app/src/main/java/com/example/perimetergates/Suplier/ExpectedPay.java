package com.example.perimetergates.Suplier;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.Intel.EarnAda;
import com.example.perimetergates.Intel.EarnModel;
import com.example.perimetergates.Intel.PrinterMe;
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

public class ExpectedPay extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ArrayList<EarnModel> SubjectLi = new ArrayList<>();
    EarnAda suppAda;
    EarnModel suppFind;
    View layer;
    Button button2;
    TextView atxtname;
    SupSess blacSess;
    SupMod staffMod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Payment Notifications");
        setContentView(R.layout.activity_expected_pay);
        blacSess = new SupSess(getApplicationContext());
        staffMod = blacSess.getSupDetails();
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            suppFind = (EarnModel) parent.getItemAtPosition(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Payment Notification");
            Rect reco = new Rect();
            Window win = this.getWindow();
            win.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.paye, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.05f));
            button2 = layer.findViewById(R.id.demoBtn);
            button2.setText(getString(R.string.title) + "\n\n" + Html.fromHtml("<font><b>" + suppFind.getMpesa() + "</b></font>") + " Confirmed.\n KES" + suppFind.getAmount() + " was sent to phone " + staffMod.getContact() + "\nfor the good delivered to " + Html.fromHtml("<font><b><i><u>PERIMETER GATES LTD</u></i></b></font>") + "\nPlease check confirmation CODE " + suppFind.getMpesa() + "\non " + Html.fromHtml("<font><b><u>" + suppFind.getReg_date() + "</u></b></font>"));
            builder.setView(layer);
            builder.setNeutralButton("print", (dialog1, item) -> {
                print();
            });
            builder.setNegativeButton("exit", (dialog, idm) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });
        practical();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void print() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrinterMe(this, layer.findViewById(R.id.monti)), null);
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.getCooker,
                response -> {
                    try {
                        EarnModel subject;
                        SubjectLi = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String ind = jsonObject.getString("ind");
                                String mpesa = jsonObject.getString("mpesa");
                                String supplier = jsonObject.getString("supplier");
                                String amount = jsonObject.getString("amount");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new EarnModel(id, ind, mpesa, supplier, amount, reg_date);
                                SubjectLi.add(subject);
                            }
                            suppAda = new EarnAda(ExpectedPay.this, R.layout.sup, SubjectLi);
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
                params.put("supplier", staffMod.getSupp_id());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}