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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.Finance.FinaDash;
import com.example.perimetergates.Finance.FinaLog;
import com.example.perimetergates.Finance.Finareg;
import com.example.perimetergates.Intel.InveSess;
import com.example.perimetergates.Intel.StaffMod;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InvenLog extends AppCompatActivity {
    EditText Username, Password, ConfirmP;
    Button btLog, btnReg, btRese, btnSub;
    View layer;
    StaffMod staffMod;
    InveSess blacSess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Inventory Login");
        setContentView(R.layout.activity_inven_log);
        blacSess = new InveSess(getApplicationContext());
        staffMod = blacSess.getInventDetails();
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        btLog = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnRegister);
        btRese = findViewById(R.id.btnreset);
        btLog.setOnClickListener(v->{
            final String myUser=Username.getText().toString().trim();
            final String myPass=Password.getText().toString().trim();
            if (myUser.isEmpty()||myPass.isEmpty()){
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            }else{
                StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.logInvent,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Log.e("response", response);
                                String msg = jsonObject.getString("message");
                                String status = jsonObject.getString("success");
                                if (status.equals("1")) {

                                    JSONArray dataArray = jsonObject.getJSONArray("login");
                                    for (int i = 0; i < dataArray.length(); i++) {

                                        JSONObject dataobj = dataArray.getJSONObject(i);
                                        String staff_id = dataobj.getString("staff_id");
                                        String fname = dataobj.getString("fname");
                                        String lname = dataobj.getString("lname");
                                        String username = dataobj.getString("username");
                                        String contact = dataobj.getString("contact");
                                        String role = dataobj.getString("role");
                                        String email = dataobj.getString("email");
                                        String sstatus = dataobj.getString("status");
                                        String reg_date = dataobj.getString("reg_date");
                                        blacSess.logInven(staff_id,fname,lname,username,contact,role,email,sstatus,reg_date);
                                    }
                                    startActivity(new Intent(getApplicationContext(), InventDash.class));


                                } else if (status.equals("0")) {
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

                                } else if (status.equals("2")) {
                                    JSONArray dataArray = jsonObject.getJSONArray("login");
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject dataobj = dataArray.getJSONObject(i);
                                        String remarks = dataobj.getString("remarks").trim();
                                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                        Toast.makeText(getApplicationContext(), remarks, Toast.LENGTH_LONG).show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(InvenLog.this, e.toString(), Toast.LENGTH_SHORT).show();
                              //  Toast.makeText(InvenLog.this, "Operation Failed", Toast.LENGTH_SHORT).show();

                            }

                        }, error -> {
                    Toast.makeText(InvenLog.this, "Root connection error", Toast.LENGTH_SHORT).show();

                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", myUser);
                        params.put("password", myPass);

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
        btnReg.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), InventReg.class));
        });
        btRese.setOnClickListener(v -> {
            AlertDialog.Builder alarmed = new AlertDialog.Builder(this);
            Rect rec = new Rect();
            Window window = InvenLog.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rec);
            LayoutInflater inflat = (LayoutInflater) InvenLog.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflat.inflate(R.layout.rester, null);
            layer.setMinimumWidth((int) (rec.width() * 0.9f));
            layer.setMinimumHeight((int) (rec.height() * 0.3f));
            Username = layer.findViewById(R.id.username);
            Password = layer.findViewById(R.id.password);
            ConfirmP = layer.findViewById(R.id.confirmPas);
            btnSub = layer.findViewById(R.id.btnSubb);
            btnSub.setOnClickListener(view -> {
                final String MYFname = Username.getText().toString().trim();
                final String MYLname = Password.getText().toString().trim();
                final String MYEmail = ConfirmP.getText().toString().trim();
                if (MYFname.isEmpty() || MYLname.isEmpty() || MYEmail.isEmpty()) {
                    Toast.makeText(this, "All Fields are required", Toast.LENGTH_SHORT).show();
                } else if (!MYEmail.equals(MYLname)) {
                    Toast.makeText(this, "Your passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.updtInve,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.e("response ", response);
                                    String msg = jsonObject.getString("message");
                                    int status = jsonObject.getInt("success");
                                    if (status == 1) {
                                        Toast.makeText(InvenLog.this, msg, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), InvenLog.class));

                                    } else if (status == 0) {
                                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                                }

                            }, error -> {
                        Toast.makeText(this, "Oops Check your root connection", Toast.LENGTH_SHORT).show();

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", MYFname);
                            params.put("password", MYLname);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequest);
                }
            });
            alarmed.setTitle("Reset Password");
            alarmed.setView(layer);
            alarmed.setNegativeButton("Close", (dialogInterface, i) -> dialogInterface.cancel());
            AlertDialog alertDialog2 = alarmed.create();
            alertDialog2.show();
            alertDialog2.setCanceledOnTouchOutside(false);
        });
    }
}