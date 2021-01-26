package com.example.hassanproject;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hassanproject.Helpers.UserInfo;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        ImageView imageView = findViewById(R.id.img);
      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        },3000)*/
        ;


        //Animation myanimation = AnimationUtils.loadAnimation(this, R.anim.mytranstion);
        //imageView.startAnimation(myanimation);
        final Intent i = new Intent(this, LoginActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                } finally {
                    List<UserInfo> userInfo = Paper.book().read("UserInfo");
                    if (userInfo != null && isConnected()) {
                        Intent intent = new Intent(MainActivity.this, MonitorDeviceActivity.class);
                        intent.putExtra("email", userInfo.get(0).getEmail().toString());
                        intent.putExtra("deviceId", userInfo.get(0).getDeviceId().toString());
                        intent.putExtra("role", userInfo.get(0).getRole().toString());
                        startActivity(intent);
                    } else if (isConnected()) {
                        startActivity(i);
                        finish();
                    } else {
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "Internet is not Connected", Snackbar.LENGTH_LONG).show();
                    }

                }
            }
        };
        timer.start();
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileData = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isConnected() || mobileData.isConnected();
    }
}
