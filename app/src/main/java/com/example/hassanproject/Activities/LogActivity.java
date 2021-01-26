package com.example.hassanproject.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
import com.example.hassanproject.Adapter.UsageAdapter;
import com.example.hassanproject.Helpers.UsageHistory;
import com.example.hassanproject.Helpers.UsageHistoryDetails;
import com.example.hassanproject.Model.Usage;
import com.example.hassanproject.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class LogActivity extends AppCompatActivity {

    String deviceId;
    RecyclerView logRV;
    UsageAdapter usageAdapter;
    List<Usage> usageList;
    long duration = 0;
    TextView noUsageTv;
    CShowProgress cShowProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("History");
        noUsageTv = findViewById(R.id.no_usage);
        cShowProgress = CShowProgress.getInstance();
        cShowProgress.showProgress(LogActivity.this);

        deviceId = getIntent().getStringExtra("device_id");
        if (deviceId != null && !deviceId.equals("")) {
            getData(deviceId);
            //updateUsage(deviceId);
            //getTotalUsage(deviceId);
        } else {
            Toast.makeText(this, "List Empty", Toast.LENGTH_SHORT).show();
        }

        logRV = findViewById(R.id.device_usage);

     /*  logRV.setLayoutManager( new LinearLayoutManager(LogActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });*/
        logRV.setLayoutManager(new LinearLayoutManager(this));
        logRV.setNestedScrollingEnabled(false);

        usageList = new ArrayList<>();

    }

    public void getTotalUsage(final String deviceID) {

        final String DEVICE_URl = "https://mirzashasan95.000webhostapp.com/Webapi/getUsageByID.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DEVICE_URl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("clima usage", response);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        UsageHistory usageHistory = gson.fromJson(response, UsageHistory.class);

                        for (UsageHistoryDetails usageObj : usageHistory.getUsageHistory()) {
                            usageList.add(new Usage(2, usageObj.getStartTime(),
                                    usageObj.getEndTime(),
                                    usageObj.getStartVibration(),
                                    usageObj.getEndVibration(),
                                    usageObj.getStartCurrent(),
                                    usageObj.getEndCurrent(),
                                    usageObj.getStartFlow(),
                                    usageObj.getEndFlow(),
                                    usageObj.getStartExhaust(),
                                    usageObj.getEndExhaust(),
                                    usageObj.getStartTemperature(),
                                    usageObj.getEndTemperature(),
                                    usageObj.getStartVoltage(),
                                    usageObj.getEndVoltage(),
                                    usageObj.getDuration()));

                            String durationString = usageObj.getDuration();
                            if (durationString != null && !durationString.equals("null")) {
                                duration = duration + Long.parseLong(durationString);
                            }
                        }

                        if (duration != 0) {
                            usageList.get(0).setUsage(ConvertSecondToHHMMString(duration));
                        } else {
                            usageList.get(0).setUsage("00:00:00");
                        }

                        if(usageList.size()==1){
                            noUsageTv.setVisibility(View.VISIBLE);
                            noUsageTv.setText("NO USAGE FOUND");
                        }
                        usageAdapter = new UsageAdapter(usageList);
                        logRV.setAdapter(usageAdapter);
                        usageAdapter.notifyDataSetChanged();

                        cShowProgress.hideProgress();
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
                Log.e("clima", deviceID);
                map.put("device_id", deviceID);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getData(final String deviceID) {
        final String DEVICE_URl = "https://mirzashasan95.000webhostapp.com/Webapi/getUser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DEVICE_URl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("climalog", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("App_Users");

                            if (sucess.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    String phone = object.getString("phone");
                                    String city = object.getString("city");

                                    if (usageList.size() == 0) {
                                        usageList.add(new Usage(1, phone, name, email, deviceID, city, ""));
                                        getTotalUsage(deviceId);
                                    }
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
                Log.e("clima", deviceID);
                map.put("device_id", deviceID);
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

    private String ConvertSecondToHHMMString(long secondtTime) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);
        String time = df.format(new Date(secondtTime * 1000L));
        return time;
    }
}
