package com.example.perimetergates.Customer;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.Intel.CustMod;
import com.example.perimetergates.Intel.CustSess;
import com.example.perimetergates.Intel.GateAda;
import com.example.perimetergates.Intel.GateMode;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.Intel.myDates;
import com.example.perimetergates.MainActivity;
import com.example.perimetergates.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class CustDash extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    CustSess blacSess;
    CustMod staffMod;
    GateMode gateMode;
    ArrayList<GateMode> SubjectList = new ArrayList<>();
    GateAda gateAda;
    GridView listView;
    SearchView searchView;
    Button Gat, Gril, Doo, Wind, Shoe, Serv, Sub, cost, close, checkDate, plusyes, plusno;
    ImageView imageView;
    View layout;
    Spinner Cate, Typed, Material, Coating;
    EditText height, length, quantity, MPesa;
    TextView Charge, total, Dat;
    RequestQueue requestQueue;
    float myhe, myLe, myQty/*, floated*/;
    String gateType, pesa, mpesaDK, additional, Location, address, mpesa, stress;
    RelativeLayout relativeLayout;
    double computed, summed, doubled;
    private static int MINIMUM_LENGTH = 10;
    String encodedimageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blacSess = new CustSess(getApplicationContext());
        staffMod = blacSess.getCustDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + staffMod.getFname());
        setContentView(R.layout.activity_cust_dash);
        Gat = findViewById(R.id.gates);
        Gril = findViewById(R.id.grills);
        Doo = findViewById(R.id.door);
        Wind = findViewById(R.id.windows);
        Shoe = findViewById(R.id.shoe);
        Serv = findViewById(R.id.service);
        Gat.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Gates.class));
        });
        Gril.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Grills.class));
        });
        Doo.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Door.class));
        });
        Wind.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Windows.class));
        });
        Shoe.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ShoeRack.class));
        });
        Serv.setOnClickListener(v -> {
            request(this);
        });
        //getCart();
    }

    private void request(CustDash custDash) {
        AlertDialog.Builder alert = new AlertDialog.Builder(custDash);
        Rect rect = new Rect();
        Window window = custDash.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        LayoutInflater layoutInflater = (LayoutInflater) custDash.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = layoutInflater.inflate(R.layout.special, null);
        layout.setMinimumWidth((int) (rect.width() * 0.9f));
        layout.setMinimumHeight((int) (rect.height() * 0.03f));
        Cate = layout.findViewById(R.id.spnCategory);
        Typed = layout.findViewById(R.id.sprType);
        Material = layout.findViewById(R.id.sprMAterial);
        Coating = layout.findViewById(R.id.sprCoating);
        height = layout.findViewById(R.id.heightDimension);
        length = layout.findViewById(R.id.lengthDimension);
        Sub = layout.findViewById(R.id.btnPay);
        Dat = layout.findViewById(R.id.bataText);
        cost = layout.findViewById(R.id.btnSubmit);
        relativeLayout = layout.findViewById(R.id.mkuu);
        total = layout.findViewById(R.id.totalCharge);
        quantity = layout.findViewById(R.id.enterQty);
        Charge = layout.findViewById(R.id.myCharges);
        close = layout.findViewById(R.id.btnClose);
        checkDate = layout.findViewById(R.id.btnCheck);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Best, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cate.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Metal, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Material.setAdapter(adapter1);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Coating, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Coating.setAdapter(adapter2);
        Cate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mSpinner = Cate.getSelectedItem().toString();
                if (mSpinner.equals("Gates") || mSpinner.equals("Doors") || mSpinner.equals("Windows")) {
                    Typed.setVisibility(View.VISIBLE);
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(CustDash.this, R.array.Majors, android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Typed.setAdapter(adapter2);
                } else if (mSpinner.equals("Grills")) {
                    Typed.setVisibility(View.VISIBLE);
                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(CustDash.this, R.array.Grills, android.R.layout.simple_spinner_item);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Typed.setAdapter(adapter1);
                } else {
                    Typed.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Dat.setOnClickListener(v -> {
            DialogFragment datePicker = new myDates();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });
        cost.setOnClickListener(v -> {
            final String len = length.getText().toString().trim();
            final String hei = height.getText().toString().trim();
            final String qty = quantity.getText().toString().trim();
            final String myCate = Cate.getSelectedItem().toString().trim();
            final String myMaterial = Material.getSelectedItem().toString().trim();
            final String myCoating = Coating.getSelectedItem().toString().trim();
            if (myCate.equals("Select Category")) {
                relativeLayout.setVisibility(View.GONE);
                Toast.makeText(custDash, "Category needed", Toast.LENGTH_SHORT).show();
            } else {
                Typed.setVisibility(View.VISIBLE);
                gateType = Typed.getSelectedItem().toString().trim();
                if (gateType.equals("Select Type")) {
                    relativeLayout.setVisibility(View.GONE);
                    Toast.makeText(custDash, "Select Type", Toast.LENGTH_SHORT).show();
                } else if (myMaterial.equals("Select Material")) {
                    relativeLayout.setVisibility(View.GONE);
                    Toast.makeText(custDash, "Select Material", Toast.LENGTH_SHORT).show();
                } else if (myCoating.equals("Select Coating")) {
                    relativeLayout.setVisibility(View.GONE);
                    Toast.makeText(custDash, "Select Coating", Toast.LENGTH_SHORT).show();
                } else if (len.isEmpty()) {
                    length.setError("length?");
                    length.requestFocus();
                    relativeLayout.setVisibility(View.GONE);
                } else if (hei.isEmpty()) {
                    relativeLayout.setVisibility(View.GONE);
                    height.setError("height?");
                    height.requestFocus();
                } else if (qty.isEmpty()) {
                    quantity.setError("How many do you want?");
                    quantity.requestFocus();
                    relativeLayout.setVisibility(View.GONE);
                } else {
                    myhe = Float.parseFloat(hei);
                    myLe = Float.parseFloat(len);
                    myQty = Float.parseFloat(qty);
                    if (myhe > 15) {
                        relativeLayout.setVisibility(View.GONE);
                        height.setError("Enter 15 Ft maximum");
                        height.requestFocus();
                    } else if (myLe > 30) {
                        relativeLayout.setVisibility(View.GONE);
                        length.setError("Enter 30 Ft maximum");
                        length.requestFocus();
                    } else {
                        computed = myhe * myLe;
                        relativeLayout.setVisibility(View.VISIBLE);
                        if (myCate.equals("Gates")) {
                            if (computed < 10) {
                                Charge.setText("5000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 5000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else if (computed >= 10 & computed < 100) {
                                Charge.setText("25000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 25000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else if (computed >= 100 & computed < 200) {
                                Charge.setText("70000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 70000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else if (computed >= 200 & computed < 300) {
                                Charge.setText("100000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 100000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else {
                                Charge.setText("150000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 150000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            }
                        } else if (myCate.equals("Doors")) {
                            if (computed < 10) {
                                Charge.setText("6000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 6000 * myQty;
                                String pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else if (computed >= 10 & computed < 100) {
                                Charge.setText("18000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 18000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else if (computed >= 100 & computed < 200) {
                                Charge.setText("41000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 41000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else if (computed >= 200 & computed < 300) {
                                Charge.setText("67000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 67000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else {
                                Charge.setText("90000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 90000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            }
                        } else if (myCate.equals("Windows")) {
                            if (computed < 10) {
                                Charge.setText("4000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 4000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else if (computed >= 10 & computed < 100) {
                                Charge.setText("15000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 15000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else if (computed >= 100 & computed < 200) {
                                Charge.setText("32000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 32000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else if (computed >= 200 & computed < 300) {
                                Charge.setText("44000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 44000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else {
                                Charge.setText("60000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 60000 * myQty;
                                String pesa = String.valueOf(summed);
                                total.setText(pesa);
                            }
                        } else if (myCate.equals("Grills")) {
                            if (computed < 10) {
                                Charge.setText("2000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 2000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else if (computed >= 10 & computed < 100) {
                                Charge.setText("9000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 9000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else if (computed >= 100 & computed < 200) {
                                Charge.setText("54000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 54000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else if (computed >= 200 & computed < 300) {
                                Charge.setText("90000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 90000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            } else {
                                Charge.setText("120000");
                                //floated = Float.parseFloat(String.valueOf(Charge));
                                summed = 120000 * myQty;
                                pesa = String.valueOf(summed);
                                total.setText(pesa);
                            }
                        }
                        checkDate.setOnClickListener(v1 -> {
                            final String myDate = Dat.getText().toString().trim();
                            if (myDate.equals("Collection Date")) {
                                Toast.makeText(custDash, "You have not set date", Toast.LENGTH_SHORT).show();
                            } else {
                                requestQueue = Volley.newRequestQueue(getApplicationContext());
                                requestQueue.add(new StringRequest(Request.Method.POST, TeaRoom.check_date,
                                        response -> {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                int stat = jsonObject.getInt("status");
                                                String msg = jsonObject.getString("message");
                                                Toast.makeText(custDash, msg, Toast.LENGTH_SHORT).show();
                                                if (stat == 1) {
                                                    Sub.setVisibility(View.VISIBLE);
                                                    Sub.setOnClickListener(v2 -> {
                                                        final String myCost = total.getText().toString().trim();
                                                        AlertDialog.Builder alex = new AlertDialog.Builder(this);
                                                        alex.setTitle("Optional Field");
                                                        alex.setMessage("Do you want to add a image?");
                                                        alex.setPositiveButton("No", (uk, kl) -> {
                                                            AlertDialog.Builder alerting = new AlertDialog.Builder(this);
                                                            alerting.setTitle("Payment Option");
                                                            alerting.setMessage("Order Cost: KES" + pesa + "\nDo you require Shipping?");
                                                            alerting.setPositiveButton(Html.fromHtml("<font color='#000000'><i><b>Ship</b</i></font>"), (dialog, id) -> {
                                                                AlertDialog.Builder men = new AlertDialog.Builder(custDash);
                                                                men.setTitle("Shipping");
                                                                men.setMessage("Shipping cost is KES 500" + "\nShipping within 48hrs from purchase time\nDo you want to proceed?");
                                                                men.setPositiveButton(Html.fromHtml("<font color='#15911A'><i>yes</i></font>"), (dialog12, which) -> {
                                                                    AlertDialog.Builder mydialog1 = new AlertDialog.Builder(custDash);
                                                                    final Spinner spinning = new Spinner(custDash);
                                                                    ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(this, R.array.Location, android.R.layout.simple_spinner_item);
                                                                    adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                    spinning.setAdapter(adapt);
                                                                    mydialog1.setView(spinning);
                                                                    mydialog1.setTitle("Select your Current Location Below");
                                                                    doubled = summed + 500;
                                                                    stress = String.valueOf(doubled);
                                                                    mydialog1.setMessage("Order Cost KES " + pesa + "\nShipping Cost KES 500\nTotal Cost KES " + stress);
                                                                    mydialog1.setPositiveButton("proceed", (dialogInterface12, i12) -> {
                                                                        Location = spinning.getSelectedItem().toString();
                                                                        if (Location.equals("Current Location")) {
                                                                            Toast.makeText(custDash, "Current Location required", Toast.LENGTH_LONG).show();
                                                                        } else {
                                                                            AlertDialog.Builder mydial = new AlertDialog.Builder(custDash);
                                                                            mydial.setTitle("Enter a known town");
                                                                            final EditText namer = new EditText(custDash);
                                                                            namer.setInputType(InputType.TYPE_CLASS_TEXT);
                                                                            namer.setHint(Html.fromHtml("<font><i>known town<i></font>"));
                                                                            mydial.setView(namer);
                                                                            mydial.setPositiveButton("send", (dialogInterface17, i17) -> {
                                                                                address = namer.getText().toString();

                                                                                if (address.isEmpty()) {
                                                                                    Toast.makeText(custDash, "Town required", Toast.LENGTH_LONG).show();
                                                                                } else {
                                                                                    AlertDialog.Builder dailing = new AlertDialog.Builder(custDash);
                                                                                    dailing.setTitle("Add Known Landmark near Your Location");
                                                                                    final EditText named = new EditText(custDash);
                                                                                    named.setInputType(InputType.TYPE_CLASS_TEXT);
                                                                                    named.setHint(Html.fromHtml("<font><i>some landmark to your location<i></font>"));
                                                                                    dailing.setView(named);
                                                                                    dailing.setPositiveButton("next", (fork, jembe) -> {
                                                                                        additional = named.getText().toString();
                                                                                        if (additional.isEmpty()) {
                                                                                            Toast.makeText(custDash, "some additional information required", Toast.LENGTH_LONG).show();
                                                                                        } else {
                                                                                            AlertDialog.Builder dialog1 = new AlertDialog.Builder(custDash);
                                                                                            Rect rectx = new Rect();
                                                                                            Window windowx = custDash.getWindow();
                                                                                            windowx.getDecorView().getWindowVisibleDisplayFrame(rectx);
                                                                                            LayoutInflater layoutInflaterx = (LayoutInflater) custDash.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                                            View layout = layoutInflaterx.inflate(R.layout.testing, null);
                                                                                            layout.setMinimumWidth((int) (rectx.width() * 0.9f));
                                                                                            layout.setMinimumHeight((int) (rectx.height() * 0.15f));
                                                                                            MPesa = layout.findViewById(R.id.mpesa);
                                                                                            dialog1.setTitle("Payment");
                                                                                            dialog1.setView(layout);
                                                                                            dialog1.setPositiveButton("send", (dialogInterface19, i19) -> {
                                                                                                mpesa = MPesa.getText().toString();
                                                                                                int lengthen = mpesa.length();
                                                                                                Pattern upperCase = Pattern.compile(getString(R.string.upper));
                                                                                                Pattern digitCase = Pattern.compile(getString(R.string.numbers));

                                                                                                if (mpesa.isEmpty()) {
                                                                                                    Toast.makeText(custDash, "MPESA Required", Toast.LENGTH_LONG).show();
                                                                                                } else if (lengthen < MINIMUM_LENGTH) {
                                                                                                    Toast.makeText(custDash, "MPESA has 10 characters", Toast.LENGTH_LONG).show();

                                                                                                } else {
                                                                                                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                                                                                                    requestQueue.add(new StringRequest(Request.Method.POST, TeaRoom.my_order,
                                                                                                            response1 -> {
                                                                                                                try {
                                                                                                                    JSONObject jsonObject1 = new JSONObject(response1);
                                                                                                                    int statu = jsonObject1.getInt("success");
                                                                                                                    String meso = jsonObject1.getString("message");
                                                                                                                    Toast.makeText(custDash, meso, Toast.LENGTH_SHORT).show();
                                                                                                                    if (statu == 1) {
                                                                                                                        startActivity(new Intent(getApplicationContext(), CustDash.class));
                                                                                                                        finish();
                                                                                                                    }
                                                                                                                } catch (Exception e) {
                                                                                                                    e.printStackTrace();
                                                                                                                    Toast.makeText(custDash, "An error occurred", Toast.LENGTH_SHORT).show();
                                                                                                                }
                                                                                                            }, error -> {
                                                                                                        Toast.makeText(custDash, error.toString(), Toast.LENGTH_LONG).show();
                                                                                                    }) {
                                                                                                        @Nullable
                                                                                                        @Override
                                                                                                        protected Map<String, String> getParams() throws AuthFailureError {
                                                                                                            Map<String, String> params = new HashMap<>();
                                                                                                            params.put("mpesa", mpesa);
                                                                                                            params.put("amount", stress);
                                                                                                            params.put("ship", "500");
                                                                                                            params.put("cost", myCost);
                                                                                                            params.put("cust_id", staffMod.getCust_id());
                                                                                                            params.put("name", staffMod.getFname() + " " + staffMod.getLname());
                                                                                                            params.put("phone", staffMod.getContact());
                                                                                                            params.put("location", Location + "-" + address + "-" + additional);
                                                                                                            params.put("category", myCate);
                                                                                                            params.put("type", gateType);
                                                                                                            params.put("material", myMaterial);
                                                                                                            params.put("coating", myCoating);
                                                                                                            params.put("dimension", "Height: " + hei + " ft By Length: " + len + " ft");
                                                                                                            params.put("quantity", qty);
                                                                                                            params.put("set_date", myDate);
                                                                                                            params.put("mode", "Shipped");
                                                                                                            return params;
                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            });//mpesa,amount,ship,cost,cust_id,name,phone,location,category,type,material,coating,dimension,quantity,set_date,mode
                                                                                            dialog1.setNegativeButton("exit", (dialogInterface19, i19) -> dialogInterface19.cancel());
                                                                                            final AlertDialog alertDialog = dialog1.create();
                                                                                            alertDialog.setCanceledOnTouchOutside(false);
                                                                                            alertDialog.setCancelable(false);
                                                                                            alertDialog.show();

                                                                                        }
                                                                                    });
                                                                                    dailing.setNegativeButton("exit", (fork, jembe) -> fork.cancel());
                                                                                    final AlertDialog alertx = dailing.create();
                                                                                    alertx.setCanceledOnTouchOutside(false);
                                                                                    alertx.setCancelable(false);
                                                                                    alertx.show();
                                                                                }

                                                                            });
                                                                            mydial.setNegativeButton("exit", (dialogInterface17, i17) -> dialogInterface17.cancel());
                                                                            final AlertDialog alertDialog = mydial.create();
                                                                            alertDialog.setCanceledOnTouchOutside(false);
                                                                            alertDialog.setCancelable(false);
                                                                            alertDialog.show();

                                                                        }
                                                                    });
                                                                    mydialog1.setNegativeButton("exit", (dialogInterface17, i17) -> dialogInterface17.cancel());
                                                                    final AlertDialog alertDialog = mydialog1.create();
                                                                    alertDialog.setCanceledOnTouchOutside(false);
                                                                    alertDialog.setCancelable(false);
                                                                    alertDialog.show();
                                                                });
                                                                men.setNegativeButton(Html.fromHtml("<font color='#DD190B'><i>cancel</i></font>"), (dialog13, which) -> dialog13.cancel());
                                                                Dialog dialog1 = men.create();
                                                                dialog1.setCancelable(false);
                                                                dialog1.setCanceledOnTouchOutside(false);
                                                                dialog1.show();

                                                            });
                                                            alerting.setNeutralButton(Html.fromHtml("<font color='#000000'><i><b>No Ship</b</i></font>"), (dialog, id) -> {
                                                                AlertDialog.Builder mydialog1 = new AlertDialog.Builder(custDash);
                                                                Rect rect0 = new Rect();
                                                                Window window0 = custDash.getWindow();
                                                                window0.getDecorView().getWindowVisibleDisplayFrame(rect0);
                                                                LayoutInflater layoutInflatero = (LayoutInflater) custDash.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                View layout = layoutInflatero.inflate(R.layout.testing, null);
                                                                layout.setMinimumWidth((int) (rect0.width() * 0.9f));
                                                                layout.setMinimumHeight((int) (rect0.height() * 0.15f));
                                                                MPesa = layout.findViewById(R.id.mpesa);
                                                                mydialog1.setTitle("Payment");
                                                                mydialog1.setView(layout);
                                                                mydialog1.setPositiveButton("send", (dialogInterface12, i12) -> {
                                                                    mpesaDK = MPesa.getText().toString();
                                                                    int lengthen = mpesaDK.length();
                                                                    Pattern upperCase = Pattern.compile(getString(R.string.upper));
                                                                    Pattern digitCase = Pattern.compile(getString(R.string.numbers));

                                                                    if (mpesaDK.isEmpty()) {
                                                                        Toast.makeText(custDash, "MPESA Required", Toast.LENGTH_LONG).show();
                                                                    } else if (lengthen < MINIMUM_LENGTH || lengthen > MINIMUM_LENGTH) {
                                                                        Toast.makeText(custDash, "MPESA has 10 characters", Toast.LENGTH_LONG).show();

                                                                    } else if (!upperCase.matcher(mpesaDK).find() && !digitCase.matcher(mpesaDK).find()) {
                                                                        Toast.makeText(custDash, "MPESA code is wrong", Toast.LENGTH_LONG).show();
                                                                    } else {
                                                                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                                                                        requestQueue.add(new StringRequest(Request.Method.POST, TeaRoom.my_order,
                                                                                response1 -> {
                                                                                    try {
                                                                                        JSONObject jsonObject1 = new JSONObject(response1);
                                                                                        int statu = jsonObject1.getInt("success");
                                                                                        String meso = jsonObject1.getString("message");
                                                                                        Toast.makeText(custDash, meso, Toast.LENGTH_SHORT).show();
                                                                                        if (statu == 1) {
                                                                                            startActivity(new Intent(getApplicationContext(), CustDash.class));
                                                                                            finish();
                                                                                        }
                                                                                    } catch (Exception e) {
                                                                                        e.printStackTrace();
                                                                                        Toast.makeText(custDash, "An error occurred", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }, error -> {
                                                                            Toast.makeText(custDash, error.toString(), Toast.LENGTH_LONG).show();
                                                                        }) {
                                                                            @Nullable
                                                                            @Override
                                                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                                                Map<String, String> params = new HashMap<>();
                                                                                params.put("mpesa", mpesaDK);
                                                                                params.put("amount", myCost);
                                                                                params.put("ship", "0");
                                                                                params.put("cost", myCost);
                                                                                params.put("cust_id", staffMod.getCust_id());
                                                                                params.put("name", staffMod.getFname() + " " + staffMod.getLname());
                                                                                params.put("phone", staffMod.getContact());
                                                                                params.put("location", "Direct");
                                                                                params.put("category", myCate);
                                                                                params.put("type", gateType);
                                                                                params.put("material", myMaterial);
                                                                                params.put("coating", myCoating);
                                                                                params.put("dimension", "Height: " + hei + " ft By Length: " + len + " ft");
                                                                                params.put("quantity", qty);
                                                                                params.put("set_date", myDate);
                                                                                params.put("mode", "Not Shipped");
                                                                                return params;
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                                mydialog1.setNegativeButton("exit", (dialogInterface1, i1) -> dialogInterface1.cancel());
                                                                final AlertDialog alertDialog = mydialog1.create();
                                                                alertDialog.setCanceledOnTouchOutside(false);
                                                                alertDialog.show();
                                                            });
                                                            alerting.setNegativeButton(Html.fromHtml("<font color='#E81D0E'>cancel</font>"), (dialog, id) -> dialog.cancel());
                                                            AlertDialog alertDialog = alerting.create();
                                                            alertDialog.show();
                                                            alertDialog.setCanceledOnTouchOutside(false);
                                                            alertDialog.setCancelable(false);
                                                        });
                                                        alex.setNegativeButton("close", (uk, kl) -> uk.cancel());
                                                        alex.setNeutralButton("yes", (uk, kl) -> {
                                                            AlertDialog.Builder bobo = new AlertDialog.Builder(this);
                                                            bobo.setTitle("Add Image");
                                                            Rect rct = new Rect();
                                                            Window wind = this.getWindow();
                                                            wind.getDecorView().getWindowVisibleDisplayFrame(rct);
                                                            LayoutInflater lala = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                            View vodo = lala.inflate(R.layout.yess, null);
                                                            plusno = vodo.findViewById(R.id.btnClose);
                                                            plusyes = vodo.findViewById(R.id.btnSub);
                                                            imageView = vodo.findViewById(R.id.textImager);
                                                            bobo.setView(vodo);   imageView.setOnClickListener(vir -> {
                                                                ActivityCompat.requestPermissions(
                                                                        CustDash.this,
                                                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                                        000
                                                                );
                                                            });
                                                            plusyes.setOnClickListener(view -> {
                                                                Drawable drawable = imageView.getDrawable();
                                                                if (drawable == null) {
                                                                    Toast.makeText(custDash, "Click the colored box to add your image", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    AlertDialog.Builder alerting = new AlertDialog.Builder(this);
                                                                    alerting.setTitle("Payment Option");
                                                                    alerting.setMessage("Order Cost: KES" + pesa + "\nDo you require Shipping?");
                                                                    alerting.setPositiveButton(Html.fromHtml("<font color='#000000'><i><b>Ship</b</i></font>"), (dialog, id) -> {
                                                                        AlertDialog.Builder men = new AlertDialog.Builder(custDash);
                                                                        men.setTitle("Shipping");
                                                                        men.setMessage("Shipping cost is KES 500" + "\nShipping within 48hrs from purchase time\nDo you want to proceed?");
                                                                        men.setPositiveButton(Html.fromHtml("<font color='#15911A'><i>yes</i></font>"), (dialog12, which) -> {
                                                                            AlertDialog.Builder mydialog1 = new AlertDialog.Builder(custDash);
                                                                            final Spinner spinning = new Spinner(custDash);
                                                                            ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(this, R.array.Location, android.R.layout.simple_spinner_item);
                                                                            adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                            spinning.setAdapter(adapt);
                                                                            mydialog1.setView(spinning);
                                                                            mydialog1.setTitle("Select your Current Location Below");
                                                                            doubled = summed + 500;
                                                                            stress = String.valueOf(doubled);
                                                                            mydialog1.setMessage("Order Cost KES " + pesa + "\nShipping Cost KES 500\nTotal Cost KES " + stress);
                                                                            mydialog1.setPositiveButton("proceed", (dialogInterface12, i12) -> {
                                                                                Location = spinning.getSelectedItem().toString();
                                                                                if (Location.equals("Current Location")) {
                                                                                    Toast.makeText(custDash, "Current Location required", Toast.LENGTH_LONG).show();
                                                                                } else {
                                                                                    AlertDialog.Builder mydial = new AlertDialog.Builder(custDash);
                                                                                    mydial.setTitle("Enter a known town");
                                                                                    final EditText namer = new EditText(custDash);
                                                                                    namer.setInputType(InputType.TYPE_CLASS_TEXT);
                                                                                    namer.setHint(Html.fromHtml("<font><i>known town<i></font>"));
                                                                                    mydial.setView(namer);
                                                                                    mydial.setPositiveButton("send", (dialogInterface17, i17) -> {
                                                                                        address = namer.getText().toString();

                                                                                        if (address.isEmpty()) {
                                                                                            Toast.makeText(custDash, "Town required", Toast.LENGTH_LONG).show();
                                                                                        } else {
                                                                                            AlertDialog.Builder dailing = new AlertDialog.Builder(custDash);
                                                                                            dailing.setTitle("Add Known Landmark near Your Location");
                                                                                            final EditText named = new EditText(custDash);
                                                                                            named.setInputType(InputType.TYPE_CLASS_TEXT);
                                                                                            named.setHint(Html.fromHtml("<font><i>some landmark to your location<i></font>"));
                                                                                            dailing.setView(named);
                                                                                            dailing.setPositiveButton("next", (fork, jembe) -> {
                                                                                                additional = named.getText().toString();
                                                                                                if (additional.isEmpty()) {
                                                                                                    Toast.makeText(custDash, "some additional information required", Toast.LENGTH_LONG).show();
                                                                                                } else {
                                                                                                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(custDash);
                                                                                                    Rect rectx = new Rect();
                                                                                                    Window windowx = custDash.getWindow();
                                                                                                    windowx.getDecorView().getWindowVisibleDisplayFrame(rectx);
                                                                                                    LayoutInflater layoutInflaterx = (LayoutInflater) custDash.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                                                    View layout = layoutInflaterx.inflate(R.layout.testing, null);
                                                                                                    layout.setMinimumWidth((int) (rectx.width() * 0.9f));
                                                                                                    layout.setMinimumHeight((int) (rectx.height() * 0.15f));
                                                                                                    MPesa = layout.findViewById(R.id.mpesa);
                                                                                                    dialog1.setTitle("Payment");
                                                                                                    dialog1.setView(layout);
                                                                                                    dialog1.setPositiveButton("send", (dialogInterface19, i19) -> {
                                                                                                        mpesa = MPesa.getText().toString();
                                                                                                        int lengthen = mpesa.length();
                                                                                                        Pattern upperCase = Pattern.compile(getString(R.string.upper));
                                                                                                        Pattern digitCase = Pattern.compile(getString(R.string.numbers));

                                                                                                        if (mpesa.isEmpty()) {
                                                                                                            Toast.makeText(custDash, "MPESA Required", Toast.LENGTH_LONG).show();
                                                                                                        } else if (lengthen < MINIMUM_LENGTH) {
                                                                                                            Toast.makeText(custDash, "MPESA has 10 characters", Toast.LENGTH_LONG).show();

                                                                                                        } else {
                                                                                                            requestQueue = Volley.newRequestQueue(getApplicationContext());
                                                                                                            requestQueue.add(new StringRequest(Request.Method.POST, TeaRoom.wazimu,
                                                                                                                    response1 -> {
                                                                                                                        try {
                                                                                                                            JSONObject jsonObject1 = new JSONObject(response1);
                                                                                                                            int statu = jsonObject1.getInt("success");
                                                                                                                            String meso = jsonObject1.getString("message");
                                                                                                                            Toast.makeText(custDash, meso, Toast.LENGTH_SHORT).show();
                                                                                                                            if (statu == 1) {
                                                                                                                                startActivity(new Intent(getApplicationContext(), CustDash.class));
                                                                                                                                finish();
                                                                                                                            }
                                                                                                                        } catch (Exception e) {
                                                                                                                            e.printStackTrace();
                                                                                                                            Toast.makeText(custDash, "An error occurred", Toast.LENGTH_SHORT).show();
                                                                                                                        }
                                                                                                                    }, error -> {
                                                                                                                Toast.makeText(custDash, error.toString(), Toast.LENGTH_LONG).show();
                                                                                                            }) {
                                                                                                                @Nullable
                                                                                                                @Override
                                                                                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                                                                                    Map<String, String> params = new HashMap<>();
                                                                                                                    params.put("mpesa", mpesa);
                                                                                                                    params.put("amount", stress);
                                                                                                                    params.put("ship", "500");
                                                                                                                    params.put("cost", myCost);
                                                                                                                    params.put("cust_id", staffMod.getCust_id());
                                                                                                                    params.put("name", staffMod.getFname() + " " + staffMod.getLname());
                                                                                                                    params.put("phone", staffMod.getContact());
                                                                                                                    params.put("location", Location + "-" + address + "-" + additional);
                                                                                                                    params.put("category", myCate);
                                                                                                                    params.put("type", gateType);
                                                                                                                    params.put("material", myMaterial);
                                                                                                                    params.put("coating", myCoating);
                                                                                                                    params.put("dimension", "Height: " + hei + " ft By Length: " + len + " ft");
                                                                                                                    params.put("quantity", qty);
                                                                                                                    params.put("set_date", myDate);
                                                                                                                    params.put("mode", "Shipped");
                                                                                                                    params.put("image", encodedimageString);
                                                                                                                    return params;
                                                                                                                }
                                                                                                            });
                                                                                                        }
                                                                                                    });//mpesa,amount,ship,cost,cust_id,name,phone,location,category,type,material,coating,dimension,quantity,set_date,mode
                                                                                                    dialog1.setNegativeButton("exit", (dialogInterface19, i19) -> dialogInterface19.cancel());
                                                                                                    final AlertDialog alertDialog = dialog1.create();
                                                                                                    alertDialog.setCanceledOnTouchOutside(false);
                                                                                                    alertDialog.setCancelable(false);
                                                                                                    alertDialog.show();

                                                                                                }
                                                                                            });
                                                                                            dailing.setNegativeButton("exit", (fork, jembe) -> fork.cancel());
                                                                                            final AlertDialog alertx = dailing.create();
                                                                                            alertx.setCanceledOnTouchOutside(false);
                                                                                            alertx.setCancelable(false);
                                                                                            alertx.show();
                                                                                        }

                                                                                    });
                                                                                    mydial.setNegativeButton("exit", (dialogInterface17, i17) -> dialogInterface17.cancel());
                                                                                    final AlertDialog alertDialog = mydial.create();
                                                                                    alertDialog.setCanceledOnTouchOutside(false);
                                                                                    alertDialog.setCancelable(false);
                                                                                    alertDialog.show();

                                                                                }
                                                                            });
                                                                            mydialog1.setNegativeButton("exit", (dialogInterface17, i17) -> dialogInterface17.cancel());
                                                                            final AlertDialog alertDialog = mydialog1.create();
                                                                            alertDialog.setCanceledOnTouchOutside(false);
                                                                            alertDialog.setCancelable(false);
                                                                            alertDialog.show();
                                                                        });
                                                                        men.setNegativeButton(Html.fromHtml("<font color='#DD190B'><i>cancel</i></font>"), (dialog13, which) -> dialog13.cancel());
                                                                        Dialog dialog1 = men.create();
                                                                        dialog1.setCancelable(false);
                                                                        dialog1.setCanceledOnTouchOutside(false);
                                                                        dialog1.show();

                                                                    });
                                                                    alerting.setNeutralButton(Html.fromHtml("<font color='#000000'><i><b>No Ship</b</i></font>"), (dialog, id) -> {
                                                                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(custDash);
                                                                        Rect rect0 = new Rect();
                                                                        Window window0 = custDash.getWindow();
                                                                        window0.getDecorView().getWindowVisibleDisplayFrame(rect0);
                                                                        LayoutInflater layoutInflatero = (LayoutInflater) custDash.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        View layout = layoutInflatero.inflate(R.layout.testing, null);
                                                                        layout.setMinimumWidth((int) (rect0.width() * 0.9f));
                                                                        layout.setMinimumHeight((int) (rect0.height() * 0.15f));
                                                                        MPesa = layout.findViewById(R.id.mpesa);
                                                                        mydialog1.setTitle("Payment");
                                                                        mydialog1.setView(layout);
                                                                        mydialog1.setPositiveButton("send", (dialogInterface12, i12) -> {
                                                                            mpesaDK = MPesa.getText().toString();
                                                                            int lengthen = mpesaDK.length();
                                                                            Pattern upperCase = Pattern.compile(getString(R.string.upper));
                                                                            Pattern digitCase = Pattern.compile(getString(R.string.numbers));

                                                                            if (mpesaDK.isEmpty()) {
                                                                                Toast.makeText(custDash, "MPESA Required", Toast.LENGTH_LONG).show();
                                                                            } else if (lengthen < MINIMUM_LENGTH || lengthen > MINIMUM_LENGTH) {
                                                                                Toast.makeText(custDash, "MPESA has 10 characters", Toast.LENGTH_LONG).show();

                                                                            } else if (!upperCase.matcher(mpesaDK).find() && !digitCase.matcher(mpesaDK).find()) {
                                                                                Toast.makeText(custDash, "MPESA code is wrong", Toast.LENGTH_LONG).show();
                                                                            } else {
                                                                                requestQueue = Volley.newRequestQueue(getApplicationContext());
                                                                                requestQueue.add(new StringRequest(Request.Method.POST, TeaRoom.wazimu,
                                                                                        response1 -> {
                                                                                            try {
                                                                                                JSONObject jsonObject1 = new JSONObject(response1);
                                                                                                int statu = jsonObject1.getInt("success");
                                                                                                String meso = jsonObject1.getString("message");
                                                                                                Toast.makeText(custDash, meso, Toast.LENGTH_SHORT).show();
                                                                                                if (statu == 1) {
                                                                                                    startActivity(new Intent(getApplicationContext(), CustDash.class));
                                                                                                    finish();
                                                                                                }
                                                                                            } catch (Exception e) {
                                                                                                e.printStackTrace();
                                                                                                Toast.makeText(custDash, "An error occurred", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        }, error -> {
                                                                                    Toast.makeText(custDash, error.toString(), Toast.LENGTH_LONG).show();
                                                                                }) {
                                                                                    @Nullable
                                                                                    @Override
                                                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                                                        Map<String, String> params = new HashMap<>();
                                                                                        params.put("mpesa", mpesaDK);
                                                                                        params.put("amount", myCost);
                                                                                        params.put("ship", "0");
                                                                                        params.put("cost", myCost);
                                                                                        params.put("cust_id", staffMod.getCust_id());
                                                                                        params.put("name", staffMod.getFname() + " " + staffMod.getLname());
                                                                                        params.put("phone", staffMod.getContact());
                                                                                        params.put("location", "Direct");
                                                                                        params.put("category", myCate);
                                                                                        params.put("type", gateType);
                                                                                        params.put("material", myMaterial);
                                                                                        params.put("coating", myCoating);
                                                                                        params.put("dimension", "Height: " + hei + " ft By Length: " + len + " ft");
                                                                                        params.put("quantity", qty);
                                                                                        params.put("set_date", myDate);
                                                                                        params.put("mode", "Not Shipped");
                                                                                        params.put("image", encodedimageString);
                                                                                        return params;
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                        mydialog1.setNegativeButton("exit", (dialogInterface1, i1) -> dialogInterface1.cancel());
                                                                        final AlertDialog alertDialog = mydialog1.create();
                                                                        alertDialog.setCanceledOnTouchOutside(false);
                                                                        alertDialog.show();
                                                                    });
                                                                    alerting.setNegativeButton(Html.fromHtml("<font color='#E81D0E'>cancel</font>"), (dialog, id) -> dialog.cancel());
                                                                    AlertDialog alertDialog = alerting.create();
                                                                    alertDialog.show();
                                                                    alertDialog.setCanceledOnTouchOutside(false);
                                                                    alertDialog.setCancelable(false);
                                                                }
                                                            });
                                                            AlertDialog dede = bobo.create();
                                                            plusno.setOnClickListener(view -> {
                                                                dede.cancel();
                                                            });
                                                            dede.setCancelable(false);
                                                            dede.setCanceledOnTouchOutside(false);
                                                            dede.show();
                                                        });
                                                        AlertDialog dada = alex.create();
                                                        dada.setCanceledOnTouchOutside(false);
                                                        dada.setCancelable(false);
                                                        dada.show();

                                                    });
                                                } else if (stat == 0) {
                                                    Sub.setVisibility(View.GONE);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Toast.makeText(custDash, "An error occurred", Toast.LENGTH_SHORT).show();
                                            }
                                        }, error -> {
                                    Toast.makeText(custDash, "Check your net connection", Toast.LENGTH_SHORT).show();
                                }) {
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("timer", myDate);
                                        return params;
                                    }
                                });
                            }

                        });
                    }
                }
            }

        });
        alert.setTitle("Personal Specification");
        alert.setView(layout);
        AlertDialog alertDialog = alert.create();
        close.setOnClickListener(v -> {
            alertDialog.cancel();
        });
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
    }

  /*  private void getCart() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.countCart,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String counter = jsonObject.getString("counter");
                                MyCat.setText("My Basket " + counter);
                                MyCat.setOnClickListener(v -> {
                                    startActivity(new Intent(getApplicationContext(), Cart.class));
                                });
                            }
                        } else if (success == 0) {
                            MyCat.setOnClickListener(v -> {
                                Toast.makeText(this, "Add Some Products. You Cart is empty", Toast.LENGTH_SHORT).show();
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        MyCat.setVisibility(View.GONE);
                    }

                }, error -> {
            MyCat.setVisibility(View.GONE);
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cust_id", staffMod.getCust_id());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }*/
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      if (requestCode == 000) {
          if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              Intent intent = new Intent(Intent.ACTION_PICK);
              intent.setType("image/*");
              startActivityForResult(intent, 000);
          } else {
              Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
          }
          return;
      }
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 000 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                encodedBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodedBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimages = byteArrayOutputStream.toByteArray();
        encodedimageString = Base64.encodeToString(bytesofimages, Base64.DEFAULT);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cust_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.prof:
                AlertDialog.Builder builder = new AlertDialog.Builder(CustDash.this);
                builder.setTitle("My Profile");
                builder.setMessage("Registry: " + staffMod.getCust_id() + "\nFname: " + staffMod.getFname() + "\nLname: " + staffMod.getLname() + "\nUsername: " + staffMod.getUsername() + "\nEmail: " + staffMod.getEmail() + "\nContact: " + staffMod.getContact() + "\nLocation: " + staffMod.getLocation());
                builder.show();
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setGravity(Gravity.TOP);
                break;
            case R.id.logger:
                blacSess.logoutCust();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            case R.id.myPayee:
                startActivity(new Intent(getApplicationContext(), HistoryPay.class));
                break;
            case R.id.mySpeci:
                startActivity(new Intent(getApplicationContext(), Customed.class));
                break;
            case R.id.myCart:
                startActivity(new Intent(getApplicationContext(), Cart.class));
                break;
            case R.id.myReceipt:
                startActivity(new Intent(getApplicationContext(), Receipt.class));
                break;
            case R.id.myDelivery:
                startActivity(new Intent(getApplicationContext(), Delivery.class));
                break;
            case R.id.myFeed:
                startActivity(new Intent(getApplicationContext(), FeedBack.class));
                break;
            case R.id.myRate:
                startActivity(new Intent(getApplicationContext(), Message.class));
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        Dat.setText(currentDateString);

    }
}