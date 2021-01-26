package com.example.hassanproject.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hassanproject.Adapter.CShowProgress;
import com.example.hassanproject.Adapter.DevicesAdapter;
import com.example.hassanproject.Model.Device;
import com.example.hassanproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevicesListActivity extends AppCompatActivity {

    RecyclerView devicesRV;
    List<Device> deviceList;
    DevicesAdapter devicesAdapter;
    CShowProgress cShowProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        devicesRV = findViewById(R.id.devices_list);
        devicesRV.setLayoutManager(new LinearLayoutManager(this));
        deviceList = new ArrayList<>();
        devicesAdapter = new DevicesAdapter(deviceList);
        devicesRV.setAdapter(devicesAdapter);

        cShowProgress = CShowProgress.getInstance();
        cShowProgress.showProgress(DevicesListActivity.this);
        new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                } finally {
                    getData();
                }
            }
        }.start();

    }

    public void getData() {
        deviceList.clear();
        final String DEVICE_URl = "https://mirzashasan95.000webhostapp.com/Webapi/getDevicesList.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DEVICE_URl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cShowProgress.hideProgress();
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
                                    deviceList.add(new Device("Device ID#: " + device_id, device_name));
                                    devicesAdapter.notifyDataSetChanged();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
