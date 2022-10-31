package com.project.siso.home.admin;

public class Admin {

    private Long id;
    private String adminName;
    private String adminId;
    private String adminPassword;
    private String adminPhoneNumber;
    private Long countyOfficeId;

    public Admin(String adminName, String adminPhoneNumber) {
        this.adminName = adminName;
        this.adminPhoneNumber = adminPhoneNumber;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAdminPhoneNumber() {
        return adminPhoneNumber;
    }

    public Long getId() {
        return id;
    }
}
