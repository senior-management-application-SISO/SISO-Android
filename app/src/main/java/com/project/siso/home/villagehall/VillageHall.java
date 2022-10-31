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

    public String getHallName() {
        return hallName;
    }

    public String getAddress() {
        return address;
    }
}
