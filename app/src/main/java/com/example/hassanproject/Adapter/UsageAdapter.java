package com.example.hassanproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hassanproject.Model.Usage;
import com.example.hassanproject.R;

import java.util.List;

public class UsageAdapter extends RecyclerView.Adapter {

    List<Usage> usageList;

    public UsageAdapter(List<Usage> usageList) {
        this.usageList = usageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case Usage.INFO:
                View viewInfo = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_info_item, parent, false);
                return new UserInfoViewHolder(viewInfo);
            case Usage.DETAILS:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usage_item, parent, false);
                return new DetailsViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (usageList.get(position).getType() == Usage.INFO) {
            if (usageList.get(position).getCity() != null && !usageList.get(position).getCity().equals("null")) {
                ((UserInfoViewHolder) holder).cityTV.setText(usageList.get(position).getCity());
            }
            if (usageList.get(position).getUsage() != null && !usageList.get(position).getUsage().equals("null")) {
                ((UserInfoViewHolder) holder).usageTV.setText(usageList.get(position).getUsage());
            }
            if (usageList.get(position).getName() != null && !usageList.get(position).getName().equals("null")) {
                ((UserInfoViewHolder) holder).nameTV.setText(usageList.get(position).getName());
            }
            if (usageList.get(position).getEmail() != null && !usageList.get(position).getEmail().equals("null")) {
                ((UserInfoViewHolder) holder).emailTV.setText(usageList.get(position).getEmail());
            }
            if (usageList.get(position).getPhone() != null && !usageList.get(position).getPhone().equals("null")) {
                ((UserInfoViewHolder) holder).phoneTV.setText(usageList.get(position).getPhone());
            }
            if (usageList.get(position).getDeviceId() != null && !usageList.get(position).getDeviceId().equals("null")) {
                ((UserInfoViewHolder) holder).deviceIdTV.setText(usageList.get(position).getDeviceId());
            }

        } else if (usageList.get(position).getType() == Usage.DETAILS) {
            if (usageList.get(position).getEndTime() != null && !usageList.get(position).getEndTime().equals("null")) {
                String time[] = usageList.get(position).getEndTime().split("T");
                ((DetailsViewHolder) holder).eDate.setText(time[0] + " " + time[1]);
            }
            if (usageList.get(position).getStartTime() != null && !usageList.get(position).getStartTime().equals("null")) {
                String time[] = usageList.get(position).getStartTime().split("T");
                ((DetailsViewHolder) holder).sDate.setText(time[0] + " " + time[1]);
            }

            if (usageList.get(position).getEndTemperature() != null && !usageList.get(position).getEndTemperature().equals("null")) {

                ((DetailsViewHolder) holder).etemp.setText(usageList.get(position).getEndTemperature());
            }
            if (usageList.get(position).getStartTemperature() != null && !usageList.get(position).getStartTemperature().equals("null")) {
                ((DetailsViewHolder) holder).stemp.setText(usageList.get(position).getStartTemperature());
            }

            if (usageList.get(position).getEndVoltage() != null && !usageList.get(position).getEndVoltage().equals("null")) {
                ((DetailsViewHolder) holder).evoltage.setText(usageList.get(position).getEndVoltage());
            }

            if (usageList.get(position).getStartVoltage() != null && !usageList.get(position).getStartVoltage().equals("null")) {
                ((DetailsViewHolder) holder).svoltage.setText(usageList.get(position).getStartVoltage());
            }

            if (usageList.get(position).getStartVibration() != null && !usageList.get(position).getStartVibration().equals("null")) {
                ((DetailsViewHolder) holder).svibration.setText(usageList.get(position).getStartVibration());
            }

            if (usageList.get(position).getEndVibration() != null && !usageList.get(position).getEndVibration().equals("null")) {
                ((DetailsViewHolder) holder).evibration.setText(usageList.get(position).getEndVibration());
            }
            if (usageList.get(position).getEndFlow() != null && !usageList.get(position).getEndFlow().equals("null")) {
                ((DetailsViewHolder) holder).eflow.setText(usageList.get(position).getEndFlow());
            }
            if (usageList.get(position).getStartFlow() != null && !usageList.get(position).getStartFlow().equals("null")) {
                ((DetailsViewHolder) holder).sflow.setText(usageList.get(position).getStartFlow());
            }
            if (usageList.get(position).getEndExhaust() != null && !usageList.get(position).getEndExhaust().equals("null")) {
                ((DetailsViewHolder) holder).eexhaust.setText(usageList.get(position).getEndExhaust());
            }
            if (usageList.get(position).getStartExhaust() != null && !usageList.get(position).getStartExhaust().equals("null")) {
                ((DetailsViewHolder) holder).sexhaust.setText(usageList.get(position).getStartExhaust());
            }
            if (usageList.get(position).getStartCurrent() != null && !usageList.get(position).getStartCurrent().equals("null")) {
                ((DetailsViewHolder) holder).scurrent.setText(usageList.get(position).getStartCurrent());
            }
            if (usageList.get(position).getEndCurrent() != null && !usageList.get(position).getEndCurrent().equals("null")) {
                ((DetailsViewHolder) holder).ecurrent.setText(usageList.get(position).getEndCurrent());
            }

        }

    }


    @Override
    public int getItemViewType(int position) {
        switch (usageList.get(position).getType()) {
            case 1:
                return Usage.INFO;
            case 2:
                return Usage.DETAILS;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return usageList.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView stemp, svoltage, sexhaust, sflow, svibration, scurrent;
        TextView etemp, evoltage, eexhaust, eflow, evibration, ecurrent;
        TextView sDate, eDate;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            stemp = itemView.findViewById(R.id.start_temperature);
            svibration = itemView.findViewById(R.id.startVibration);
            scurrent = itemView.findViewById(R.id.startCurrent);
            svoltage = itemView.findViewById(R.id.startVoltage);
            sflow = itemView.findViewById(R.id.startFlow);
            sexhaust = itemView.findViewById(R.id.startExhaust);
            etemp = itemView.findViewById(R.id.end_temperature);
            evibration = itemView.findViewById(R.id.endVibration);
            ecurrent = itemView.findViewById(R.id.endCurrent);
            eexhaust = itemView.findViewById(R.id.endExhaust);
            eflow = itemView.findViewById(R.id.end_flow);
            evoltage = itemView.findViewById(R.id.endVoltage);
            sDate = itemView.findViewById(R.id.start_date);
            eDate = itemView.findViewById(R.id.end_date);
        }
    }

    public class UserInfoViewHolder extends RecyclerView.ViewHolder {
        TextView phoneTV, nameTV, emailTV, deviceIdTV, cityTV, usageTV;

        public UserInfoViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneTV = itemView.findViewById(R.id.phone_no);
            deviceIdTV = itemView.findViewById(R.id.device_id);
            nameTV = itemView.findViewById(R.id.name);
            cityTV = itemView.findViewById(R.id.city);
            usageTV = itemView.findViewById(R.id.usage);
            emailTV = itemView.findViewById(R.id.email);
        }
    }

}
