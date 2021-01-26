package com.example.hassanproject.Adapter;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hassanproject.Model.Sensor;
import com.example.hassanproject.R;

import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;
import static com.example.hassanproject.Adapter.App.CHANNEL_1_ID;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.ViewHolder> {
    private List<Sensor> sensorList;

    public SensorAdapter(List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    @NonNull
    @Override
    public SensorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sesnor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorAdapter.ViewHolder holder, int position) {
        String sensorName = sensorList.get(position).getSensorName();
        int status = sensorList.get(position).getStatus();
        int image = sensorList.get(position).getSensorIcon();
        String readings = sensorList.get(position).getSensorReadings();
        String[] list = sensorList.get(position).getIssuesList();
        if (list == null || list[0].equals("")) {
            list = new String[1];
            list[0] = "issues missing";
        }

        holder.setData(status, sensorName, list, image, readings);
    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sensorImage;
        TextView sensorName;
        TextView view;
        TextView readings;
        ImageView yellow_iv;
        ImageView red_iv;
        ImageView green_iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sensorImage = itemView.findViewById(R.id.image_sensor);
            sensorName = itemView.findViewById(R.id.sensor_name);
            view = itemView.findViewById(R.id.view_issues);
            readings = itemView.findViewById(R.id.readings_sensor);
            yellow_iv = itemView.findViewById(R.id.yellow_icon);
            green_iv = itemView.findViewById(R.id.green_icon);
            red_iv = itemView.findViewById(R.id.red_icon);
        }

        @SuppressLint("ResourceAsColor")
        public void setData(int status, String sensorName, final String[] list, int sensorIcon, String readingsTxt) {
            this.sensorName.setText(sensorName);
            this.sensorImage.setImageResource(sensorIcon);
            this.readings.setText(readingsTxt);

            if (list != null) {
                final StringBuilder sb = new StringBuilder(60);

                for (int i = 0; i < list.length; i++) {
                    if (i == list.length - 1) {
                        sb.append(list[i]);
                    } else {
                        sb.append(list[i] + "\n");
                    }
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                        builder.setTitle("Issues May Occur");
                        builder.setMessage(sb.toString());
                        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        builder.show();
                    }
                });
            }

            if (status == 1 || status == 2 || status == 3) {
                if (status == 1) {
                    yellow_iv.setImageResource(R.drawable.light_yellow_dot);
                    red_iv.setImageResource(R.drawable.red_dot);
                    green_iv.setImageResource(R.drawable.light_green_dot);
                } else if (status == 2) {
                    yellow_iv.setImageResource(R.drawable.yellow_dot);
                    red_iv.setImageResource(R.drawable.light_red_dot);
                    green_iv.setImageResource(R.drawable.light_green_dot);
                } else if (status == 3) {
                    yellow_iv.setImageResource(R.drawable.light_yellow_dot);
                    red_iv.setImageResource(R.drawable.light_red_dot);
                    green_iv.setImageResource(R.drawable.green_dot);
                }
            } else {
                yellow_iv.setImageResource(R.drawable.light_yellow_dot);
                red_iv.setImageResource(R.drawable.light_red_dot);
                green_iv.setImageResource(R.drawable.light_green_dot);
            }
        }
    }
}
