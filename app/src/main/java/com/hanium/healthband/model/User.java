package com.hanium.healthband.model;

public class User {
    private String username;
    private String name;
    private String phone_number;
    private String user_type;
    //public String userArduinoCode;


    public User(String username, String name, String phone_number, String user_type) {
        this.username = username;
        this.name = name;
        this.phone_number = phone_number;
        this.user_type = user_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
