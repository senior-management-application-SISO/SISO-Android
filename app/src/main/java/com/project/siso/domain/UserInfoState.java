package com.project.siso.domain;


import java.sql.Date;
import java.time.LocalDateTime;

public class UserInfoState {
    private Long id;
    private String userName;
    private String dateOfBirth;
    private String address;
    private String phoneNumber;
    private Boolean alone;
    private Long teamId;
    private Long adminId;
    private Long usersLocationId;
    private Long villageHallId;
    private String date;
    private Boolean attendanceState;
    private Boolean hallState;

    public UserInfoState() {
    }

    public UserInfoState(Long id, String userName, String dateOfBirth, String address, String phoneNumber, Boolean alone, Long teamId, Long adminId, Long usersLocationId, Long villageHallId, String date, Boolean attendanceState, Boolean hallState) {
        this.id = id;
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.alone = alone;
        this.teamId = teamId;
        this.adminId = adminId;
        this.usersLocationId = usersLocationId;
        this.villageHallId = villageHallId;
        this.date = date;
        this.attendanceState = attendanceState;
        this.hallState = hallState;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
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

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getUsersLocationId() {
        return usersLocationId;
    }

    public void setUsersLocationId(Long usersLocationId) {
        this.usersLocationId = usersLocationId;
    }

    public Long getVillageHallId() {
        return villageHallId;
    }

    public void setVillageHallId(Long villageHallId) {
        this.villageHallId = villageHallId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getAttendanceState() {
        return attendanceState;
    }

    public void setAttendanceState(Boolean attendanceState) {
        this.attendanceState = attendanceState;
    }

    public Boolean getHallState() {
        return hallState;
    }

    public void setHallState(Boolean hallState) {
        this.hallState = hallState;
    }
}
