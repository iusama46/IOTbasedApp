package com.example.hassanproject.Helpers;

public class UserInfo {
    String email, password, role, deviceId;

    public UserInfo(String email, String password, String role, String deviceId) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.deviceId = deviceId;
    }

    public UserInfo() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
