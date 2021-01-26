package com.example.hassanproject;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hassanproject.Activities.DevicesListActivity;
import com.example.hassanproject.Adapter.CShowProgress;
import com.example.hassanproject.Adapter.SensorAdapter;
import com.example.hassanproject.MQTT.MqttConnection;
import com.example.hassanproject.Model.Sensor;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import io.paperdb.Paper;

import static com.example.hassanproject.Adapter.App.CHANNEL_1_ID;

public class MonitorDeviceActivity extends AppCompatActivity {

    public static final int RED = 1;
    public static final int YELLOW = 2;
    public static final int GREEN = 3;
    String deviceID;
    RecyclerView sensorsRV;
    SensorAdapter sensorAdapter;
    TextView email_tv, role_tv;
    TextView current_time;
    TextView runningHours;
    MqttConnection connection;
    long duration = 0;
    private NotificationManagerCompat notificationManager;

    public String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_device);
        Paper.init(this);
        notificationManager = NotificationManagerCompat.from(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sensorsRV = findViewById(R.id.sesnor_rv);
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        sensorsRV.setLayoutManager(new GridLayoutManager(this, 2));

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        current_time = findViewById(R.id.current_time);
        runningHours = findViewById(R.id.duration);
        current_time.setText(getCurrentDate());
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        androidx.appcompat.app.ActionBarDrawerToggle toggle = new androidx.appcompat.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.nav_help) {
                    startActivity(new Intent(MonitorDeviceActivity.this, PDFActivity.class)
                            .putExtra("intent", "help"));
                } else if (id == R.id.nav_sensors_info) {
                    startActivity(new Intent(MonitorDeviceActivity.this, PDFActivity.class)
                            .putExtra("intent", "sensor"));
                } else if (id == R.id.nav_log_out) {
                    logoutUser();
                } else if (id == R.id.nav_home) {
                    Toast.makeText(MonitorDeviceActivity.this, "To be Implemented", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawers();
                } else if (id == R.id.nav_contact_us) {
                    contactUs();
                } else if (id == R.id.nav_log_file) {
                    startActivity(new Intent(MonitorDeviceActivity.this, DevicesListActivity.class));
                }
                return false;
            }
        });

        //View hView = navigationView.inflateHeaderView(R.layout.nav_header_monitor_device);

        View headerView = navigationView.getHeaderView(0);
        role_tv = headerView.findViewById(R.id.role_tv);
        email_tv = headerView.findViewById(R.id.email_tv);
        String intentEmail = getIntent().getStringExtra("email");

        if (intentEmail != null) {
            email_tv.setText(intentEmail);
        }
        String intentRole = getIntent().getStringExtra("role");
        if (intentRole != null) {
            role_tv.setText("Logged in as " + intentRole);
        }
        if (intentRole != null && intentRole.equals("admin")) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_home).setVisible(false);
            nav_Menu.findItem(R.id.nav_log_file).setVisible(true);
        } else {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_home).setVisible(false);
            nav_Menu.findItem(R.id.nav_log_file).setVisible(false);
        }

        connection = new MqttConnection(this, new exchange_values() {
            @Override
            public void update_values() {
                update();
            }
        });
        connection.setConnection();


        ////////////////////////////////

        List<Sensor> sensorList = new ArrayList<>();

        //TODO: Data Input
        // 1 = red, 2=yellow. 3= green
        sensorList.add(new Sensor(2, "Voltage", R.drawable.lightning, " 220.9 V", new String[]{"issue1", "issue2"}));
        sensorList.add(new Sensor(3, "Current", R.drawable.current, " 12.0 A", new String[]{"issues1,isssue2"}));
        sensorList.add(new Sensor(2, "Vibration", R.drawable.vibration, " 1123 MM/S", new String[]{""}));
        sensorList.add(new Sensor(1, "Temperature", R.drawable.temperature, " 80.3 C", new String[]{""}));
        sensorList.add(new Sensor(3, " Flow  ", R.drawable.flow_w, " 19.0 L/Hr", new String[]{""}));
        sensorList.add(new Sensor(2, "Exhaust", R.drawable.pump, " 46.0 V", new String[]{""}));
        sensorAdapter = new SensorAdapter(sensorList);
        sensorsRV.setAdapter(sensorAdapter);
        sensorAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();

        deviceID = getIntent().getStringExtra("deviceId");
        if (deviceID != null) {
            updateUsage(deviceID);
        }
    }

    private void updateUsage(final String deviceID) {
        runningHours.setText("00:00:00");
        final String DEVICE_URl = "https://mirzashasan95.000webhostapp.com/Webapi/getDuration.php";
        duration = 0;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DEVICE_URl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("clima duration", response);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("Usage_History");

                            if (sucess.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String durationString = object.getString("duration");
                                    if (durationString != null && !durationString.equals("null")) {
                                        duration = duration + Long.parseLong(durationString);
                                    }
                                }

                                if (duration != 0)
                                    runningHours.setText(ConvertSecondToHHMMString(duration));
                                else
                                    runningHours.setText("00:00:00");

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
                map.put("device_id", deviceID);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void contactUs() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Contact Us");
        builder.setMessage(" Phone #: +933242227355 \n Phone #: +93211234567 \n Phone #: +93211234567 \n Email: Hasanbaig@hotmail.com");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();


    }


    private void logoutUser() {
        final CShowProgress cShowProgress = CShowProgress.getInstance();
        cShowProgress.showProgress(MonitorDeviceActivity.this);
        new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                } finally {
                    cShowProgress.hideProgress();
                    Paper.book().destroy();
                    startActivity(new Intent(MonitorDeviceActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.monitor_device, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_help) {
            startActivity(new Intent(MonitorDeviceActivity.this, PDFActivity.class)
                    .putExtra("intent", "help"));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        /*NavController navController = Navigation.findNavController(this);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();*/
        return true;
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

    public void update() {
        List<Sensor> sensorList = new ArrayList<>();

        //TODO: Data Input
        // 1 = red, 2=yellow. 3= green
        int red = 1, yellow = 2, green = 3;

        int volt_status = 0;
        int current_status = 0;
        int ex_status = 0;
        int vibration_status = 0;
        int temperature_status = 0;
        int flow_status = 0;

        float volt = 0;
        float current = 0;
        float ex = 0;
        float vibration = 0;
        float temperature = 0;
        float flow = 0;


        try {
            volt = Float.parseFloat(connection.voltage);
            if (volt > 230) {
                volt_status = red;
            } else if (volt > 222) {
                volt_status = yellow;
            }
            if (volt < 220) {
                volt_status = green;
            }
        } catch (Exception e) {
            volt_status = 0;
            e.printStackTrace();
        }

        try {
            current = Float.parseFloat(connection.current);
            if (current > 0) {
                current_status = red;
            } else if (current > 1) {
                current_status = yellow;
            }
            if (current < 2) {
                current_status = green;
            }
        } catch (Exception e) {
            current_status = 0;
            e.printStackTrace();
        }
        try {
            ex = Float.parseFloat(connection.ex);
            if (ex > 0) {
                ex_status = red;
            } else if (ex > 1) {
                ex_status = yellow;
            }
            if (ex < 2) {
                ex_status = green;
            }
        } catch (Exception e) {
            ex_status = 0;
            e.printStackTrace();
        }
        try {
            vibration = Float.parseFloat(connection.vibration);
            if (vibration > 0) {
                vibration_status = red;
            } else if (vibration > 1) {
                vibration_status = yellow;
            }
            if (vibration < 2) {
                vibration_status = green;
            }
        } catch (Exception e) {
            vibration_status = 0;
            e.printStackTrace();
        }
        try {
            temperature = Float.parseFloat(connection.temperature);
            if (temperature > 0) {
                temperature_status = red;
            } else if (temperature > 1) {
                temperature_status = yellow;
            }
            if (temperature < 2) {
                temperature_status = green;
            }
        } catch (Exception e) {
            temperature_status = 0;
            e.printStackTrace();
        }
        try {
            flow = Float.parseFloat(connection.flow);
            if (flow > 0) {
                flow_status = red;
            } else if (flow > 1) {
                flow_status = yellow;
            }
            if (flow < 2) {
                flow_status = green;
            }
        } catch (Exception e) {
            flow_status = 0;
            e.printStackTrace();
        }


        sensorList.add(new Sensor(volt_status, "Voltage", R.drawable.lightning, volt + " V", new String[]{"Check1", "Check2", "Check3"}));
        sensorList.add(new Sensor(current_status, "Current", R.drawable.current, current + " A", new String[]{"issues1", "isssue2"}));
        sensorList.add(new Sensor(vibration_status, "Vibration", R.drawable.vibration, vibration + " MM/S", new String[]{"add yout issues"}));
        sensorList.add(new Sensor(temperature_status, "Temperature", R.drawable.temperature, temperature + " C", new String[]{""}));
        sensorList.add(new Sensor(flow_status, " Flow  ", R.drawable.flow_w, flow + " L/hr", new String[]{""}));
        sensorList.add(new Sensor(ex_status, "Exhaust", R.drawable.pump, ex + " V", new String[]{""}));
        sensorAdapter = new SensorAdapter(sensorList);
        sensorsRV.setAdapter(sensorAdapter);
        sensorAdapter.notifyDataSetChanged();


        for (Sensor sensor : sensorList) {
            if (sensor.getStatus() == RED) {

                if (sensor.getSensorName().equals(sensorList.get(0).getSensorName())) {
                    showNotification(sensor.getSensorName() + " Sensor ", "Need help " + volt, 1);
                } else if (sensor.getSensorName().equals(sensorList.get(1).getSensorName())) {
                    showNotification(sensor.getSensorName() + " Sensor ", "Your message " + current, 2);
                } else if (sensor.getSensorName().equals(sensorList.get(2).getSensorName())) {
                    showNotification(sensor.getSensorName() + " Sensor ", "Your message " + vibration, 3);
                } else if (sensor.getSensorName().equals(sensorList.get(3).getSensorName())) {
                    showNotification(sensor.getSensorName() + " Sensor ", "Your message " + temperature, 4);
                } else if (sensor.getSensorName().equals(sensorList.get(4).getSensorName())) {
                    showNotification(sensor.getSensorName() + " Sensor", "Your message " + flow, 5);
                } else if (sensor.getSensorName().equals(sensorList.get(5).getSensorName())) {
                    showNotification(sensor.getSensorName() + " Sensor", "Your message " + ex, 6);
                }
            }
        }

    }

    private void showNotification(String sensorName, String alertMessage, int id) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_warning)
                .setContentTitle(sensorName + " Sensor")
                .setContentText(alertMessage)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(id, notification);
    }

    private String ConvertSecondToHHMMString(long secondtTime) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);
        String time = df.format(new Date(secondtTime * 1000L));
        return time;
    }


}
