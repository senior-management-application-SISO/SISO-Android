package com.project.siso.home.team;

public class Teams {
    private Long id;
    private String teamName;
    private String teamAddress;
    private Long adminId;

    public Teams(Long id, String teamName, String teamAddress, Long adminId) {
        this.id = id;
        this.teamName = teamName;
        this.teamAddress = teamAddress;
        this.adminId = adminId;
    }

    public Teams(String teamName, String teamAddress) {
        this.teamName = teamName;
        this.teamAddress = teamAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamAddress() {
        return teamAddress;
    }

    public void setTeamAddress(String teamAddress) {
        this.teamAddress = teamAddress;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
