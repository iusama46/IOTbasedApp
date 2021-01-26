package com.example.hassanproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.hassanproject.R.color.colorPrimary;

public class UserRegisterActivity extends AppCompatActivity {
    final String Signup_url = "https://mirzashasan95.000webhostapp.com/Webapi/info.php";
    MaterialSpinner spinnerCities;
    EditText name, password, email, phonenumber, device_id;
    ImageButton show_password;
    int counter = 1;
    String cityName = "";
    TextView login_btn, signup_btn;

    public static boolean isStringOnlyAlphabet(String str) {
        return ((!str.equals(""))
                && (str != null)
                && (str.matches("^[a-zA-Z]*$")));
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        getSupportActionBar().hide();
        name = findViewById(R.id.name);
        show_password = findViewById(R.id.show_password);
        signup_btn = findViewById(R.id.sup);
        login_btn = findViewById(R.id.lin);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        phonenumber = findViewById(R.id.mobphone);
        device_id = findViewById(R.id.device_id);
        spinnerCities = findViewById(R.id.spinnerID);
        spinnerCities.setTextColor(getResources().getColor(colorPrimary));
        /////////////////////////////////////////

        final List<String> list = new ArrayList<>();
        list.add("Choose City");
        list.add("Lahore");
        list.add("Islamabad");
        list.add("Multan");
        list.add("Pishawar");
        list.add("Quetta");
        list.add("Karachi");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCities.setDropdownMaxHeight(400);
        spinnerCities.setDropdownHeight(400);
        spinnerCities.setAdapter(dataAdapter);

        spinnerCities.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                //String selectedItem = view.getItemAtPosition(position).toString();
                cityName = item.toString();

            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserRegisterActivity.this, LoginActivity.class));
                finish();
            }
        });


        show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().isEmpty()) {
                    password.setError("Please Enter Pass word");
                } else {
                    if (counter == 1) {
                        password.setTransformationMethod(null);
                        password.setSelection(password.getText().length());
                        counter = 0;
                    } else {
                        counter = 1;
                        password.setTransformationMethod(new PasswordTransformationMethod());
                        password.setSelection(password.getText().length());
                    }
                }
            }
        });
        //////////////////////////////////////////


        /////////////////////////////////////

        signup_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signup_btn.setEnabled(false);

                if ((name.length() != 0) || (password.length() != 0) || (phonenumber.length() != 0)
                        || (email.length() != 0) || (!cityName.equals(""))) {

                    if (isValidEmail(email.getText().toString())) {
                        if (password.getText().toString().length() > 5) {
                            if (isStringOnlyAlphabet(name.getText().toString())) {
                                if (phonenumber.getText().toString().length() > 7) {
                                    if (!cityName.equals("") && !cityName.equals(list.get(0))) {
                                        if (!device_id.equals("") && device_id.getText().length() > 2) {
                                            String getname = name.getText().toString();
                                            String getpassword = password.getText().toString();
                                            String getemailaddress = email.getText().toString();
                                            String getphonenumber = phonenumber.getText().toString();
                                            String deviceID = device_id.getText().toString();

                                            signUpUser(getname, getpassword, getemailaddress,
                                                    cityName, getphonenumber, deviceID);
                                            signup_btn.setEnabled(true);
                                        } else {
                                            signup_btn.setEnabled(true);
                                            device_id.setError("Device ID is Invalid");
                                        }
                                    } else {
                                        signup_btn.setEnabled(true);
                                        Toast.makeText(UserRegisterActivity.this, "Choose City", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    signup_btn.setEnabled(true);
                                    phonenumber.setError("Phone number is invalid");
                                }
                            } else {
                                signup_btn.setEnabled(true);
                                name.setError("Name is Invalid");
                            }
                        } else {
                            signup_btn.setEnabled(true);
                            password.setError("Password is short, Password must have more than 6-12 ");
                        }
                    } else {
                        signup_btn.setEnabled(true);
                        email.setError("Invalid Email");
                    }

                } else {
                    signup_btn.setEnabled(true);
                    name.setError("name can't be empty");
                    password.setError("password can't be empty");
                    phonenumber.setError("phone number can't be empty");
                    email.setError("email can't be empty");
                }
            }
        });
    }

    public void signUpUser(final String username, final String password, final String mail, final String city, final String phonenumber, final String deviceID) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Account Creating In Process");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Signup_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                if (response.trim().equals("done")) {
                    Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(UserRegisterActivity.this, LoginActivity.class);
                    i.putExtra("email", mail);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("GetName", username);
                map.put("GetPassword", password);
                map.put("GetEmail", mail);
                map.put("GetPhone", String.valueOf(phonenumber));
                map.put("GetCity", city);
                map.put("GetDeviceID", deviceID);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
