package com.project.siso.villagehall;

import java.time.LocalDateTime;

public class Users {
    private String userName;
    private String dateOfBirth;
    private String phoneNumber;

    private String hallName;
    private String address;

    public Users(String userName, String dateOfBirth, String phoneNumber, String hallName, String address) {
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.hallName = hallName;
        this.address = address;
    }

    public Users(String userName, String phoneNumber) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
