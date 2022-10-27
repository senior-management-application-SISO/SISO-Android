package com.project.siso.domain;


import java.util.Date;

public class Users {

    private Long id;

    private String userName;
    private String userId;
    private String password;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private Boolean alone;

    //외래키
    private Long teamId;
    private Long usersLocationId;
    private Long adminId;
    private Long villageHallId;

    public Users(Long id, String userName, String userId, String password, Date dateOfBirth, String address, String phoneNumber, Boolean alone, Long teamId, Long usersLocationId, Long adminId, Long villageHallId) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.alone = alone;
        this.teamId = teamId;
        this.usersLocationId = usersLocationId;
        this.adminId = adminId;
        this.villageHallId = villageHallId;
    }

    public Users() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getAlone() {
        return alone;
    }

    public void setAlone(Boolean alone) {
        this.alone = alone;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getUsersLocationId() {
        return usersLocationId;
    }

    public void setUsersLocationId(Long usersLocationId) {
        this.usersLocationId = usersLocationId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getVillageHallId() {
        return villageHallId;
    }

    public void setVillageHallId(Long villageHallId) {
        this.villageHallId = villageHallId;
    }
}
