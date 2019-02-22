package com.example.vyspsrivyavasayiadmin;

public class UserClass {
    String name,mobile,email,area,area_code,status,timestamp,registered,address;

    public UserClass(String name, String mobile, String email, String address,String area,
                     String area_code, String status, String timestamp, String registered) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.area = area;
        this.address = address;
        this.area_code = area_code;
        this.status = status;
        this.timestamp = timestamp;
        this.registered = registered;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getArea() {
        return area;
    }

    public String getArea_code() {
        return area_code;
    }

    public String getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getRegistered() {
        return registered;
    }
}