package com.example.perimetergates.Inventory;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.perimetergates.Intel.GateMode;
import com.example.perimetergates.Intel.InveSess;
import com.example.perimetergates.Intel.ModifyGate;
import com.example.perimetergates.Intel.StaffMod;
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

public class InventDash extends AppCompatActivity {
    InveSess blacSess;
    StaffMod staffMod;
    Button button, Supp, Send, Feed;
    GateMode gateMode;
    ArrayList<GateMode> SubjectList = new ArrayList<>();
    ModifyGate gateAda;
    GridView listView;
    SearchView searchView;
    Spinner Category, Typed;
    ImageView imageView;
    EditText Quantity, Description, Price, EditPri;
    TextView EditQty;
    String encodedimageString, myTyped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blacSess = new InveSess(getApplicationContext());
        staffMod = blacSess.getInventDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + staffMod.getFname());
        setContentView(R.layout.activity_invent_dash);
        button = findViewById(R.id.storage);
        Feed = findViewById(R.id.myFeed);
        Feed.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Feedback.class));
        });
        Supp = findViewById(R.id.supplied);
        Supp.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Supply.class));
        });
        button.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Stock.class));
        });
        listView = findViewById(R.id.availableGrid);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            gateMode = (GateMode) parent.getItemAtPosition(position);
            showCategory(InventDash.this, position);
        });
        practical();
       /* butl.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            Rect rect = new Rect();
            Window window = InventDash.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            LayoutInflater layoutInflater = (LayoutInflater) InventDash.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.prod_up, null);
            layout.setMinimumWidth((int) (rect.width() * 0.9f));
            layout.setMinimumHeight((int) (rect.height() * 0.04f));
            Quantity = layout.findViewById(R.id.quantityAmount);
            Category = layout.findViewById(R.id.spnCate);
            Send = layout.findViewById(R.id.btnPost);
            Price = layout.findViewById(R.id.price);
            Description = layout.findViewById(R.id.description);
            Typed = layout.findViewById(R.id.spnType);
            imageView = layout.findViewById(R.id.circleView);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Product, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Category.setAdapter(adapter);
            Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String mSpinner = Category.getSelectedItem().toString();
                    validateRegSpinner(mSpinner);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            imageView.setOnClickListener(vir -> {
                ActivityCompat.requestPermissions(
                        InventDash.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        000
                );
            });

            Send.setOnClickListener(view -> {
                final String myCate = Category.getSelectedItem().toString().trim();
                if (!myCate.equals("Select Category")) {
                    myTyped = Typed.getSelectedItem().toString().trim();
                }
                final String myQuant = Quantity.getText().toString().trim();
                final String myDesc = Description.getText().toString().trim();
                final String myPrice = Price.getText().toString().trim();
                Drawable myImage = imageView.getDrawable();

                if (myCate.equals("Select Category")) {
                    Toast.makeText(this, "Please select product Category", Toast.LENGTH_SHORT).show();
                } else if (myTyped.equals("Select Type")) {
                    Toast.makeText(this, "No type was Selected", Toast.LENGTH_SHORT).show();
                } else if (myQuant.isEmpty() || myDesc.isEmpty() || myPrice.isEmpty()) {
                    Toast.makeText(this, "Opps. You have an empty field", Toast.LENGTH_SHORT).show();
                } else if (myImage == null) {
                    Toast.makeText(InventDash.this, "Click the box to add some resume", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.addProduct,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String mess = jsonObject.getString("message");
                                    int Status = jsonObject.getInt("success");
                                    if (Status == 1) {
                                        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), InventDash.class));
                                        finish();
                                    } else if (Status == 0) {
                                        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                                }
                            }, error -> {
                        Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show();
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("category", myCate);
                            params.put("type", myTyped);
                            params.put("quantity", myQuant);
                            params.put("description", myDesc);
                            params.put("price", myPrice);
                            params.put("image", encodedimageString);
                            return params;
                        }
                    };   //category,description,quantity,price,image
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            });
            alert.setTitle("Upload Product");
            alert.setView(layout);
            alert.setNegativeButton("close", (dialog, id) -> dialog.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        });*/
    }

    private void showCategory(InventDash inventDash, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(inventDash);
        GateMode product = SubjectList.get(position);
        builder.setTitle(product.getCategory());
        builder.setMessage("Type: " + product.getType() + "\nDescription: " + product.getDescription() + "\nDate: " + product.getReg_date());
        Rect rect = new Rect();
        Window window = inventDash.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        LayoutInflater layoutInflater = (LayoutInflater) inventDash.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.view_mem, null);
        layout.setMinimumWidth((int) (rect.width() * 0.9f));
        layout.setMinimumHeight((int) (rect.height() * 0.02f));
        imageView = layout.findViewById(R.id.myImage);
        EditPri = layout.findViewById(R.id.enterPric);
        EditQty = layout.findViewById(R.id.enterQty);
        Glide.with(inventDash).load(product.getImage()).into(imageView);
        EditQty.setText(product.getQuantity());
        EditPri.setText(product.getPrice());
        builder.setPositiveButton("update", (dialog, i2) -> {
            final String myPrice = EditPri.getText().toString().trim();
            final String myQty = EditQty.getText().toString().trim();
            if (myPrice.isEmpty() || myQty.isEmpty()) {
                Toast.makeText(inventDash, "You empty fields", Toast.LENGTH_SHORT).show();
            } else if (myPrice.equals(product.getPrice()) & myQty.equals(product.getQuantity())) {
                Toast.makeText(inventDash, "No entry was edited", Toast.LENGTH_SHORT).show();
            } else {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.changePrice,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String mess = jsonObject.getString("message");
                                int Status = jsonObject.getInt("success");
                                if (Status == 1) {
                                    Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), InventDash.class));
                                    finish();
                                } else if (Status == 0) {
                                    Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }, error -> {
                    Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show();
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", product.getId());
                        params.put("price", myPrice);
                        params.put("quantity", myQty);
                        ;
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
        builder.setNeutralButton("delete", (dialog, i2) -> {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.dropProd,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String mess = jsonObject.getString("message");
                            int Status = jsonObject.getInt("success");
                            if (Status == 1) {
                                Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), InventDash.class));
                                finish();
                            } else if (Status == 0) {
                                Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }, error -> {
                Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show();
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", product.getId());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        });
        builder.setView(layout);
        builder.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    private void validateRegSpinner(String mSpinner) {
        if (mSpinner.equals("Gates") || mSpinner.equals("Doors") || mSpinner.equals("Windows")) {
            Typed.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Majors, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Typed.setAdapter(adapter2);
        } else if (mSpinner.equals("Grills")) {
            Typed.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Grills, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Typed.setAdapter(adapter1);
        } else if (mSpinner.equals("Shoe Rack")) {
            Typed.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Shoe, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Typed.setAdapter(adapter1);
        } else {
            Typed.setVisibility(View.GONE);
        }
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TeaRoom.allProduct,
                response -> {
                    try {
                        GateMode subject;
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
                                String description = jsonObject.getString("description");
                                String image = jsonObject.getString("image");
                                String imagery = TeaRoom.img + image;
                                String qty = jsonObject.getString("qty");
                                String quantity = jsonObject.getString("quantity");
                                String price = jsonObject.getString("price");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new GateMode(id, category, type, description, imagery, qty, quantity, price, reg_date);
                                SubjectList.add(subject);
                            }
                            gateAda = new ModifyGate(InventDash.this, R.layout.modify, SubjectList);
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
        inflater.inflate(R.menu.invent_prof, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.prof:
                AlertDialog.Builder builder = new AlertDialog.Builder(InventDash.this);
                builder.setTitle("My Profile");
                builder.setMessage("Serial: " + staffMod.getStaff_id() + "\nFname: " + staffMod.getFname() + "\nLname: " + staffMod.getLname() + "\nUsername: " + staffMod.getUsername() + "\nEmail: " + staffMod.getEmail() + "\nContact: " + staffMod.getContact() + "\nRole: " + staffMod.getRole());
                builder.show();
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setGravity(Gravity.TOP);
                break;
            case R.id.logger:
                blacSess.logoutInvent();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            case R.id.exit:
                finishAffinity();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}