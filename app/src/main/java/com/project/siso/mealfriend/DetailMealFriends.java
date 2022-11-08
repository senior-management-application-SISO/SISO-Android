package com.project.siso.mealfriend;

public class DetailMealFriends {

    private Long id;
    private Integer memNumber;
    private Integer currentNumber;

    private String time;
    private String address;
    private String name;
    private String phoneNumber;
    private String memo;
    private Boolean state;

    private String userName;

    public DetailMealFriends() {
    }

    public DetailMealFriends(Long id, Integer memNumber, Integer currentNumber, String time, String address, String name, String phoneNumber, String memo, Boolean state, String userName) {
        this.id = id;
        this.memNumber = memNumber;
        this.currentNumber = currentNumber;
        this.time = time;
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.memo = memo;
        this.state = state;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMemNumber() {
        return memNumber;
    }

    public void setMemNumber(Integer memNumber) {
        this.memNumber = memNumber;
    }

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
