package com.project.siso.home.admin;

public class AdminCountyOffice {

    //admin
    private Long id;

    private String adminName;

    private String adminId;

    private String adminPassword;
    private String adminPhoneNumber;

    private Long countyOfficeId;


    //CountyOffice
    private String officeName;

    public AdminCountyOffice() {
    }

    public AdminCountyOffice(Long id, String adminName, String adminId, String adminPassword, String adminPhoneNumber, Long countyOfficeId, String officeName) {
        this.id = id;
        this.adminName = adminName;
        this.adminId = adminId;
        this.adminPassword = adminPassword;
        this.adminPhoneNumber = adminPhoneNumber;
        this.countyOfficeId = countyOfficeId;
        this.officeName = officeName;
    }

    public AdminCountyOffice(Long id, String adminName, String adminId, String adminPhoneNumber, Long countyOfficeId, String officeName) {
        this.id = id;
        this.adminName = adminName;
        this.adminId = adminId;
        this.adminPhoneNumber = adminPhoneNumber;
        this.countyOfficeId = countyOfficeId;
        this.officeName = officeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminPhoneNumber() {
        return adminPhoneNumber;
    }

    public void setAdminPhoneNumber(String adminPhoneNumber) {
        this.adminPhoneNumber = adminPhoneNumber;
    }

    public Long getCountyOfficeId() {
        return countyOfficeId;
    }

    public void setCountyOfficeId(Long countyOfficeId) {
        this.countyOfficeId = countyOfficeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
}
