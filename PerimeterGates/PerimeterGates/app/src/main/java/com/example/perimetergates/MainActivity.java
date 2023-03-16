package com.example.perimetergates;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.BlackSmith.BlackDash;
import com.example.perimetergates.BlackSmith.BlackLog;
import com.example.perimetergates.Customer.CustDash;
import com.example.perimetergates.Customer.CustLog;
import com.example.perimetergates.Driver.DriveDash;
import com.example.perimetergates.Driver.DriveLog;
import com.example.perimetergates.Finance.FinaDash;
import com.example.perimetergates.Finance.FinaLog;
import com.example.perimetergates.Intel.BlacSess;
import com.example.perimetergates.Intel.CustMod;
import com.example.perimetergates.Intel.CustSess;
import com.example.perimetergates.Intel.DriMod;
import com.example.perimetergates.Intel.DriSess;
import com.example.perimetergates.Intel.FinaSess;
import com.example.perimetergates.Intel.InveSess;
import com.example.perimetergates.Intel.StaffMod;
import com.example.perimetergates.Intel.SupMod;
import com.example.perimetergates.Intel.SupSess;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.Inventory.DispatcherModule;
import com.example.perimetergates.Inventory.InvenLog;
import com.example.perimetergates.Inventory.InventDash;
import com.example.perimetergates.Suplier.SuppDash;
import com.example.perimetergates.Suplier.SuppLog;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button button;
    View layer;
    Vibrator vibrator;
    SliderView sliderView;
    int[] images = {R.drawable.gat,
            R.drawable.gater,
            R.drawable.gatepass};
    CardView customer, driver, supplier, finance, inventory, blacksmith;
    EditText Fname, Lname, Email;
    Button btnMover;
    CustSess custSess;
    CustMod custMod;
    DriMod driMod;
    DriSess driSess;
    StaffMod staffMod;
    SupMod supMod;
    SupSess supSess;
    FinaSess finaSess;
    InveSess inveSess;
    BlacSess blacSess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        sliderView = findViewById(R.id.slider);
        slidesadapter slidesadapter = new slidesadapter(images);
        sliderView.setSliderAdapter(slidesadapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
        customer = findViewById(R.id.Customer);
        driver = findViewById(R.id.Driver);
        supplier = findViewById(R.id.Supplier);
        finance = findViewById(R.id.Finance);
        inventory = findViewById(R.id.Inventory);
        blacksmith = findViewById(R.id.Blacksmith);
        blacSess = new BlacSess(getApplicationContext());
        staffMod = blacSess.getBlackDetails();
        driSess = new DriSess(getApplicationContext());
        driMod = driSess.getDriDetails();
        supSess = new SupSess(getApplicationContext());
        supMod = supSess.getSupDetails();
        finaSess = new FinaSess(getApplicationContext());
        staffMod = finaSess.getFinaDetails();
        inveSess = new InveSess(getApplicationContext());
        staffMod = inveSess.getInventDetails();
        custSess = new CustSess(getApplicationContext());
        custMod = custSess.getCustDetails();
        customer.setOnClickListener(v -> {
           if (custSess.loggedCust()) {
                startActivity(new Intent(getApplicationContext(), CustDash.class));
            } else {
                startActivity(new Intent(getApplicationContext(), CustLog.class));
           }

        });
        driver.setOnClickListener(v -> {
           if (driSess.loggedDri()) {
                startActivity(new Intent(getApplicationContext(), DriveDash.class));
            } else {
                startActivity(new Intent(getApplicationContext(), DriveLog.class));
            }
        });
        supplier.setOnClickListener(v -> {
            if (supSess.loggedSup()) {
                startActivity(new Intent(getApplicationContext(), SuppDash.class));
            } else {
                startActivity(new Intent(getApplicationContext(), SuppLog.class));
            }
        });
        finance.setOnClickListener(v -> {
            if (finaSess.loggedFina()) {
                startActivity(new Intent(getApplicationContext(), FinaDash.class));
            } else {
                startActivity(new Intent(getApplicationContext(), FinaLog.class));
            }
        });
        inventory.setOnClickListener(v -> {
            CharSequence[] items = {"SHIPMENT & DELIVERY", "INVENTORY MANAGEMENT"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("SELECT OPTION");
            dialog.setItems(items, (dialog1, item) -> {
                if (item == 0) {
                    AlertDialog.Builder yess = new AlertDialog.Builder(MainActivity.this);
                    yess.setTitle("Enter Username");
                    final EditText editText = new EditText(MainActivity.this);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    editText.setHint("Enter dispatcher/Inventory username");
                    yess.setView(editText);
                    yess.setPositiveButton("login", (diolo, idf) -> {
                        final String myReson = editText.getText().toString().trim();
                        if (myReson.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Username required", Toast.LENGTH_SHORT).show();
                        } else {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.dispatcher,
                                    response -> {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String msg = jsonObject.getString("message");
                                            int status = jsonObject.getInt("success");
                                            if (status == 1) {
                                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), DispatcherModule.class));
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
                                    params.put("username", myReson);
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(stringRequest);
                        }
                    });
                    yess.setNegativeButton("close", (diolo, idf) -> diolo.cancel());
                    AlertDialog alertDialog = yess.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                } else {
                   if (inveSess.loggedInvent()) {
                        startActivity(new Intent(getApplicationContext(), InventDash.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), InvenLog.class));
                    }
                }
            });
            dialog.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
            final AlertDialog alertDialog = dialog.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            alertDialog.show();
        });
        blacksmith.setOnClickListener(v -> {
           if (blacSess.loggedBlack()) {
                startActivity(new Intent(getApplicationContext(), BlackDash.class));
            } else {
                startActivity(new Intent(getApplicationContext(), BlackLog.class));
           }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About Us Information");
                builder.setMessage(getString(R.string.Abt));
                builder.setNegativeButton("close", (oo, o) -> oo.cancel());
                Dialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;
            case R.id.help:
                AlertDialog.Builder builderd = new AlertDialog.Builder(this);
                builderd.setTitle("Help");
                builderd.setMessage(getString(R.string.Faq));
                builderd.setNegativeButton("close", (oo, o) -> oo.cancel());
                Dialog dia = builderd.create();
                dia.setCancelable(false);
                dia.setCanceledOnTouchOutside(false);
                dia.show();
                break;
            case R.id.logout:
                finishAffinity();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}