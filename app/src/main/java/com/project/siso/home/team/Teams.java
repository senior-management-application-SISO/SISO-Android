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

    public String getTeamName() {
        return teamName;
    }

    public String getTeamAddress() {
        return teamAddress;
    }
}
