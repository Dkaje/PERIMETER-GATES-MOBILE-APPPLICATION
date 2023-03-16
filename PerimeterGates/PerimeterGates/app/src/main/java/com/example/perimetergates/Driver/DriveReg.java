package com.example.perimetergates.Driver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.R;
import com.example.perimetergates.Suplier.SuppLog;
import com.example.perimetergates.Suplier.SuppReg;
import com.santalu.maskedittext.MaskEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DriveReg extends AppCompatActivity {
    EditText fname, lname, username, email, password;
    MaskEditText contact;
    Button btn;
    int Cont = 15;
    int Pasy = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Driver Registration");
        setContentView(R.layout.activity_drive_reg);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        contact = findViewById(R.id.phone);
        btn = findViewById(R.id.btnRegister);
        btn.setOnClickListener(v -> {
            final String myName = fname.getText().toString().trim();
            final String myLname = lname.getText().toString().trim();
            final String myUser = username.getText().toString().trim();
            final String myEmail = email.getText().toString().trim();
            final String mypas = password.getText().toString().trim();
            final String myPhon = contact.getText().toString().trim();
            int lent = mypas.length();
            int phn = myPhon.length();
            String emailPattern = "[a-z0-9._%+-]+@gmail+\\.[a-z]{2,4}";
            String emailPattern2 = "[a-z0-9._%+-]+@yahoo+\\.[a-z]{2,4}";
            if (myName.isEmpty() || myLname.isEmpty() || myUser.isEmpty() || myEmail.isEmpty() || mypas.isEmpty() || myPhon.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DriveReg.this);
                builder.setTitle("Failed!!");
                builder.setMessage("Enter all fields");
                builder.show();
            } else if (!myEmail.matches(emailPattern) & !myEmail.matches(emailPattern2)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DriveReg.this);
                builder.setTitle("Failed!!");
                builder.setMessage("Enter a valid email address");
                builder.show();
            } else if (lent < Pasy) {
                Toast.makeText(this, "Your password is weak", Toast.LENGTH_SHORT).show();
            } else if (phn < Cont) {
                Toast.makeText(this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
            }else{
                StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.regDri,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Log.e("response ", response);
                                String msg = jsonObject.getString("message");
                                int status = jsonObject.getInt("success");
                                if (status==1) {
                                    Toast.makeText(DriveReg.this, msg, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), DriveLog.class));

                                } else if (status==0) {
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(this, "Failed to link the database", Toast.LENGTH_SHORT).show();
                            }

                        }, error -> {
                    Toast.makeText(this, "Failed!! your connection is weak", Toast.LENGTH_SHORT).show();

                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("fname", myName);
                        params.put("lname", myLname);
                        params.put("username", myUser);
                        params.put("email", myEmail);
                        params.put("phone", myPhon);
                        params.put("password", mypas);
                        return params;
                    }
                };//fullname,username,mobile,role,email,password
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
        });
    }
}