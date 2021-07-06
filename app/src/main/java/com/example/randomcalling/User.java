package com.example.randomcalling;

import java.util.ArrayList;

public class User {
    String userName, PhoneNo, isActive, userKey, userPassword, userEmail;
    ArrayList<String>exp;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public User(String userKey, String userName, String phoneNo, String userEmail, String userPassword, String isActive, ArrayList<String>exp) {
        this.userName = userName;
        this.PhoneNo = phoneNo;
        this.isActive = isActive;
        this.userKey = userKey;
        this.exp = exp;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public User(String userName, String phoneNo) {
        this.userName = userName;
        this.PhoneNo = phoneNo;
    }
    public User(){}

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", PhoneNo='" + PhoneNo + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }

    public ArrayList<String> getExp() {
        return exp;
    }

    public void setExp(ArrayList<String> exp) {
        this.exp = exp;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
