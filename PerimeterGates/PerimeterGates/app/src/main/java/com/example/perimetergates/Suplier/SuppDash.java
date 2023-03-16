package com.example.perimetergates.Suplier;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perimetergates.Intel.PurAda;
import com.example.perimetergates.Intel.Purmode;
import com.example.perimetergates.Intel.SupMod;
import com.example.perimetergates.Intel.SupSess;
import com.example.perimetergates.Intel.TeaRoom;
import com.example.perimetergates.MainActivity;
import com.example.perimetergates.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SuppDash extends AppCompatActivity {
    SupSess blacSess;
    SupMod staffMod;
    Button button, but;
    ListView listView;
    SearchView searchView;
    Purmode gateMode;
    ArrayList<Purmode> SubjectList = new ArrayList<>();
    PurAda gateAda;
    EditText Quantity, Price, Description;
    Button Send;
    ImageView imageView;
    Bitmap bitmap;
    String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blacSess = new SupSess(getApplicationContext());
        staffMod = blacSess.getSupDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + staffMod.getFname());
        setContentView(R.layout.activity_supp_dash);
        but = findViewById(R.id.myHisty);
        button = findViewById(R.id.inMake);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.listedOne);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            gateMode = (Purmode) parent.getItemAtPosition(position);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            Rect rect = new Rect();
            Window window = SuppDash.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            LayoutInflater layoutInflater = (LayoutInflater) SuppDash.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.wakuu, null);
            layout.setMinimumWidth((int) (rect.width() * 0.9f));
            layout.setMinimumHeight((int) (rect.height() * 0.04f));
            Quantity = layout.findViewById(R.id.quantityAmount);
            Send = layout.findViewById(R.id.btnPost);
            Price = layout.findViewById(R.id.price);
            Description = layout.findViewById(R.id.description);
            imageView = layout.findViewById(R.id.circleView);
            imageView.setOnClickListener(vir -> {
                ActivityCompat.requestPermissions(
                        SuppDash.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        000
                );
            });
            Send.setOnClickListener(vie -> {
                final String myQuant = Quantity.getText().toString().trim();
                final String myPrice = Price.getText().toString().trim();
                final String myDesc = Description.getText().toString().trim();
                Drawable myImage = imageView.getDrawable();

                if (myQuant.isEmpty() || myDesc.isEmpty() || myPrice.isEmpty()) {
                    Toast.makeText(this, "Oops. You have an empty field", Toast.LENGTH_SHORT).show();
                } else if (myImage == null) {
                    Toast.makeText(SuppDash.this, "Click the box to add some resume", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.moverBill,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int Status = jsonObject.getInt("success");
                                    String mess = jsonObject.getString("message");
                                    if (Status == 1) {
                                        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), SuppDash.class));
                                        finish();
                                    } else if (Status == 0) {
                                        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }, error -> {
                        Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show();
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("quantity", myQuant);
                            params.put("qty", gateMode.getQuantity());
                            params.put("category", gateMode.getCategory());
                            params.put("type", gateMode.getType());
                            params.put("pur_id", gateMode.getPur_id());
                            params.put("price", myPrice);
                            params.put("description", myDesc);
                            params.put("image", encodedImage);
                            params.put("supplier", staffMod.getSupp_id());
                            return params;
                        }
                    };   //category,type,qty,pur_id,quantity,price,description,image,supplier
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            });
            alert.setTitle("Upload Product");
            alert.setView(layout);
            alert.setMessage("MaximumQty " + gateMode.getQuantity() + " units");
            alert.setNegativeButton("close", (dialog, idx) -> dialog.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        });
        practical();
        button.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ExpectedPay.class));
        });
        but.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SuppHistory.class));
        });
    }

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
        encodedImage = Base64.encodeToString(bytesofimages, Base64.DEFAULT);
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.newLife,
                response -> {
                    try {
                        Purmode subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String pur_id = jsonObject.getString("pur_id");
                                String category = jsonObject.getString("category");
                                String type = jsonObject.getString("type");
                                String quantity = jsonObject.getString("quantity");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new Purmode(pur_id, category, type, quantity, reg_date);
                                SubjectList.add(subject);
                            }
                            gateAda = new PurAda(SuppDash.this, R.layout.sup_upload, SubjectList);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.supply_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.prof:
                AlertDialog.Builder builder = new AlertDialog.Builder(SuppDash.this);
                builder.setTitle("My Profile");
                builder.setMessage("REG_ID: " + staffMod.getSupp_id() + "\nFname: " + staffMod.getFname() + "\nLname: " + staffMod.getLname() + "\nUsername: " + staffMod.getUsername() + "\nEmail: " + staffMod.getEmail() + "\nContact: " + staffMod.getContact());
                builder.show();
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setGravity(Gravity.TOP);
                break;
            case R.id.logger:
                blacSess.logoutSup();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}