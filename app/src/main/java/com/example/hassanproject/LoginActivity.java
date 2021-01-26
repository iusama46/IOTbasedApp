package com.example.hassanproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hassanproject.Helpers.UserInfo;
import com.example.hassanproject.Model.Device;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    final String LOGIN_URl = "https://mirzashasan95.000webhostapp.com/Webapi/checkLogin.php";
    List<Device> deviceList;
    EditText password, email;
    ImageButton showPassword;
    boolean isDeviceFound = false;
    int c = 1;

    //String nameReg = "^[\\p{L} .'-]+$";
    //String emailReg = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextView signup_btn, login_btn;

    private boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        showPassword = findViewById(R.id.show_password);
        login_btn = findViewById(R.id.login_tv);
        deviceList = new ArrayList<>();
        signup_btn = findViewById(R.id.sup);
        String intentEmail = getIntent().getStringExtra("email");
        if (intentEmail != null && isValidEmail(intentEmail)) {
            email.setText(intentEmail);
            email.setSelection(email.getText().length());
        }

        Paper.init(this);
        checkConnection();
        isDeviceExist();


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidEmail(email.getText().toString())) {
                    email.setError("Invalid Email");
                    login_btn.setEnabled(true);
                } else if (password.getText().toString().equals("")) {
                    password.setError("Password Required");
                    login_btn.setEnabled(true);
                } else {
                    login_btn.setEnabled(false);
                    checkUserLogin(email.getText().toString(), password.getText().toString());
                }

            }
        });
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, UserRegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().isEmpty()) {
                    password.setError("Please Enter Password");
                } else {
                    if (c == 1) {
                        password.setTransformationMethod(null);
                        password.setSelection(password.getText().length());
                        c = 0;
                    } else {
                        c = 1;
                        password.setTransformationMethod(new PasswordTransformationMethod());
                        password.setSelection(password.getText().length());
                    }
                }
            }
        });
    }

    public void checkUserLogin(final String email, final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("clima", response);
                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("loginAuth");

                            if (sucess.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String role = object.getString("role");
                                    String device_id = object.getString("device_id");
                                    if (role != null && !role.equals("null")) {
                                        if (device_id != null && !device_id.equals("null")) {


                                            boolean isFound = false;

                                            for (Device device : deviceList) {
                                                if (device.getDeviceId().equals(device_id)) {
                                                    isFound = true;
                                                    break;
                                                }
                                            }
                                            ////////////
                                            if (role.equals("1") && isFound) {

                                                List<UserInfo> userInfo = new ArrayList<>();
                                                userInfo.add(new UserInfo(email,password,"admin",device_id));
                                                Paper.book().write("UserInfo", userInfo);
                                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, MonitorDeviceActivity.class);
                                                intent.putExtra("email", email.toString());
                                                intent.putExtra("deviceId", device_id);
                                                intent.putExtra("role", "admin");
                                                startActivity(intent);
                                            } else if (role.equals("2") && isFound) {
                                                List<UserInfo> userInfo = new ArrayList<>();
                                                userInfo.add(new UserInfo(email,password,"user",device_id));
                                                Paper.book().write("UserInfo", userInfo);
                                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, MonitorDeviceActivity.class);
                                                intent.putExtra("email", email.toString());
                                                intent.putExtra("deviceId", device_id);
                                                intent.putExtra("role", "user");
                                                startActivity(intent);
                                            } else {
                                                login_btn.setEnabled(true);
                                                deviceMsg();
                                            }

                                            /////////
                                        }


                                    } else {
                                        login_btn.setEnabled(true);
                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }


                            } else {
                                login_btn.setEnabled(true);
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                map.put("getEmail", email);
                map.put("getPassword", password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override // exit the app on click
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quit Gen Monitor?");
        builder.setMessage("Are you sure you want to Quit?");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
                finish();
            }
        });
        builder.show();
    }

    public void checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileData = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected() || mobileData.isConnected()) {

        } else {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Internet is not Connected", Snackbar.LENGTH_SHORT)
                    .setAction("Try Again", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkConnection();
                        }
                    })
                    .show();
        }
    }

    public void isDeviceExist() {
        deviceList.clear();
        final String DEVICE_URl = "https://mirzashasan95.000webhostapp.com/Webapi/getDevicesList.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DEVICE_URl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("Devices");

                            if (sucess.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String device_id = object.getString("device_id");
                                    String device_name = object.getString("device_name");
                                    deviceList.add(new Device(device_id, device_name));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                // map.put("device_id", deviceId);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    void deviceMsg() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Device ID issue");
        builder.setMessage("Your linked device with this email is not found/exist. Please contact administration");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
