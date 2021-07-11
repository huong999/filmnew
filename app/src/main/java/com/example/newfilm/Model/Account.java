package com.example.newfilm.Model;

public class Account {

    private int id;
    private String phone;
    private String password;
    private String fullName;
    private String address;

    public Account( String phone, String password, String fullName, String address) {
        this.phone = phone;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
    }

    public Account(int id, String phone, String password, String fullName, String address) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
