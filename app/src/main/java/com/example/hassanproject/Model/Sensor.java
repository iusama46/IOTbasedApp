package com.example.hassanproject.Model;

public class Sensor {


    int status;
    String sensorName;
    int sensorIcon;
    String sensorReadings;
    String[] issuesList;

    public Sensor(int status, String sensorName, int sensorIcon, String sensorReadings, String[] issuesList) {
        this.status = status;
        this.sensorName = sensorName;
        this.sensorIcon = sensorIcon;
        this.sensorReadings = sensorReadings;
        this.issuesList = issuesList;
    }

    public Sensor(int status, String sensorName, String sensorReadings, String[] issuesList) {
        this.status = status;
        this.sensorName = sensorName;
        this.sensorReadings = sensorReadings;
        this.issuesList = issuesList;
    }

    public int getSensorIcon() {
        return sensorIcon;
    }

    public void setSensorIcon(int sensorIcon) {
        this.sensorIcon = sensorIcon;
    }

    public String getSensorReadings() {
        return sensorReadings;
    }

    public void setSensorReadings(String sensorReadings) {
        this.sensorReadings = sensorReadings;
    }

    public Sensor(int status, String sensorName, String[] issuesList) {
        this.status = status;
        this.sensorName = sensorName;
        this.issuesList = issuesList;
    }

    public void setIssuesList(String[] issuesList) {
        this.issuesList = issuesList;
    }

    public Sensor(int status, String sensorName) {
        this.status = status;
        this.sensorName = sensorName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String[] getIssuesList() {
        return issuesList;
    }
}
