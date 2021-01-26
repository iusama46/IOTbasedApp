package com.example.hassanproject.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hassanproject.Activities.LogActivity;
import com.example.hassanproject.Model.Device;
import com.example.hassanproject.R;

import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.ViewHolder> {
    List<Device> deviceList;

    public DevicesAdapter(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new DevicesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.deviceName.setText(deviceList.get(position).getDeviceName());
        holder.deviceId.setText(deviceList.get(position).getDeviceId());

        String device_id = deviceList.get(position).getDeviceId();
       final String[] id = device_id.split("Device ID#: ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), LogActivity.class).
                        putExtra("device_id",id[1]));
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName, deviceId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            deviceId = itemView.findViewById(R.id.device_id);
            deviceName = itemView.findViewById(R.id.device_name);
        }
    }
}
