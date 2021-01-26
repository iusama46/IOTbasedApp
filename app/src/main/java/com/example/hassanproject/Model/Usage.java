package com.example.hassanproject.Model;

public class Usage {
    public static final int INFO = 1;
    public static final int DETAILS = 2;
    int type;


    String phone, name, email, deviceId, city, usage;

    public Usage(int type, String phone, String name, String email, String deviceId, String city, String usage) {
        this.type = type;
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.deviceId = deviceId;
        this.city = city;
        this.usage = usage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Usage(int type, String startTime, String endTime, String startVibration, String endVibration, String startCurrent, String endCurrent, String startFlow, String endFlow, String startExhaust, String endExhaust, String startTemperature, String endTemperature, String startVoltage, String endVoltage, String duration) {
        this.type = type;
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

    private String sessionId;

    private String startTime;

    private String endTime;
    private String duration;

    private String startVibration;

    private String endVibration;

    private String startCurrent;

    private String endCurrent;

    private String startFlow;

    private String endFlow;

    private String startExhaust;

    private String endExhaust;

    private String startTemperature;

    private String endTemperature;
    private String startVoltage;

    private String endVoltage;

    public Usage(String sessionId, String startTime, String endTime, String duration, String startVibration, String endVibration, String startCurrent, String endCurrent, String startFlow, String endFlow, String startExhaust, String endExhaust, String startTemperature, String endTemperature, String startVoltage, String endVoltage) {
        this.sessionId = sessionId;
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

    public Usage(String startTime, String endTime, String duration, String startVibration, String endVibration, String startCurrent, String endCurrent, String startFlow, String endFlow, String startExhaust, String endExhaust, String startTemperature, String endTemperature, String startVoltage, String endVoltage) {
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

    public Usage() {
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
