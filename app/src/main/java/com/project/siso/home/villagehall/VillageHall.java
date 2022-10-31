package com.project.siso.home.villagehall;

public class VillageHall {

    private Long id;
    private String hallName;
    private Integer lat;
    private Integer lon;
    private String address;
    private Long adminId;

    public VillageHall(String hallName, String address) {
        this.hallName = hallName;
        this.address = address;
    }

    public VillageHall(Long id, String hallName, Integer lat, Integer lon, String address, Long adminId) {
        this.id = id;
        this.hallName = hallName;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.adminId = adminId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Integer getLon() {
        return lon;
    }

    public void setLon(Integer lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
