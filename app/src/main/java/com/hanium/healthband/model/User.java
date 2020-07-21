package com.hanium.healthband.model;

public class User {
    public String username;
    public String name;
    public String phone_number;
    public String user_type;
    //public String userArduinoCode;


    public User(String username, String name, String phone_number, String user_type) {
        this.username = username;
        this.name = name;
        this.phone_number = phone_number;
        this.user_type = user_type;
    }
}
