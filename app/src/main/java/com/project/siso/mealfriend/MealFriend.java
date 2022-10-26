package com.project.siso.mealfriend;

import java.time.LocalTime;

public class MealFriend {
    private Integer memNumber;
    private LocalTime time;
    private String address;
    private String name;
    private String phoneNumber;
    private String members;
    private String memo;
    private Boolean state;

    public MealFriend() {
    }

    public MealFriend(Integer memNumber, String address, String name) {
        this.memNumber = memNumber;
        this.address = address;
        this.name = name;
    }

    public MealFriend(Integer memNumber, LocalTime time, String address, String name, String phoneNumber, String members, String memo, Boolean state) {
        this.memNumber = memNumber;
        this.time = time;
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.members = members;
        this.memo = memo;
        this.state = state;
    }

    public Integer getMemNumber() {
        return memNumber;
    }

    public void setMemNumber(Integer memNumber) {
        this.memNumber = memNumber;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
