package com.example.hassanproject.Helpers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsageHistoryDetails {
    @SerializedName("session_id")
    @Expose
    private String sessionId;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("start_vibration")
    @Expose
    private String startVibration;
    @SerializedName("end_vibration")
    @Expose
    private String endVibration;
    @SerializedName("start_current")
    @Expose
    private String startCurrent;
    @SerializedName("end_current")
    @Expose
    private String endCurrent;
    @SerializedName("start_flow")
    @Expose
    private String startFlow;
    @SerializedName("end_flow")
    @Expose
    private String endFlow;
    @SerializedName("start_exhaust")
    @Expose
    private String startExhaust;
    @SerializedName("end_exhaust")
    @Expose
    private String endExhaust;
    @SerializedName("start_temperature")
    @Expose
    private String startTemperature;
    @SerializedName("end_temperature")
    @Expose
    private String endTemperature;
    @SerializedName("start_voltage")
    @Expose
    private String startVoltage;
    @SerializedName("end_voltage")
    @Expose
    private String endVoltage;
    public UsageHistoryDetails(String startTime, String endTime, String duration, String startVibration, String endVibration, String startCurrent, String endCurrent, String startFlow, String endFlow, String startExhaust, String endExhaust, String startTemperature, String endTemperature, String startVoltage, String endVoltage) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.startVibration = startVibration;
        this.endVibration = endVibration;
        this.startCurrent = startCurrent;
        this.endCurrent = endCurrent;
        this.startFlow = startFlow;
        this.endFlow = endFlow;
        this.startExhaust = startExhaust;
        this.endExhaust = endExhaust;
        this.startTemperature = startTemperature;
        this.endTemperature = endTemperature;
        this.startVoltage = startVoltage;
        this.endVoltage = endVoltage;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartVibration() {
        return startVibration;
    }

    public void setStartVibration(String startVibration) {
        this.startVibration = startVibration;
    }

    public String getEndVibration() {
        return endVibration;
    }

    public void setEndVibration(String endVibration) {
        this.endVibration = endVibration;
    }

    public String getStartCurrent() {
        return startCurrent;
    }

    public void setStartCurrent(String startCurrent) {
        this.startCurrent = startCurrent;
    }

    public String getEndCurrent() {
        return endCurrent;
    }

    public void setEndCurrent(String endCurrent) {
        this.endCurrent = endCurrent;
    }

    public String getStartFlow() {
        return startFlow;
    }

    public void setStartFlow(String startFlow) {
        this.startFlow = startFlow;
    }

    public String getEndFlow() {
        return endFlow;
    }

    public void setEndFlow(String endFlow) {
        this.endFlow = endFlow;
    }

    public String getStartExhaust() {
        return startExhaust;
    }

    public void setStartExhaust(String startExhaust) {
        this.startExhaust = startExhaust;
    }

    public String getEndExhaust() {
        return endExhaust;
    }

    public void setEndExhaust(String endExhaust) {
        this.endExhaust = endExhaust;
    }

    public String getStartTemperature() {
        return startTemperature;
    }

    public void setStartTemperature(String startTemperature) {
        this.startTemperature = startTemperature;
    }

    public String getEndTemperature() {
        return endTemperature;
    }

    public void setEndTemperature(String endTemperature) {
        this.endTemperature = endTemperature;
    }

    public String getStartVoltage() {
        return startVoltage;
    }

    public void setStartVoltage(String startVoltage) {
        this.startVoltage = startVoltage;
    }

    public String getEndVoltage() {
        return endVoltage;
    }

    public void setEndVoltage(String endVoltage) {
        this.endVoltage = endVoltage;
    }

}
