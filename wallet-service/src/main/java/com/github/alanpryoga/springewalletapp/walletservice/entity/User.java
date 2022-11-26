package com.github.alanpryoga.springewalletapp.walletservice.entity;

public class User {

    private int id;

    private String name;

    private String phone;

    private UserType userType;

    public User(int id, String name, String phone, UserType userType) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

}
