package com.freshermanage.payload.response;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String userName;
    private String email;
    private boolean userStatus;
    private List<String> listRoles;

    public JwtResponse() {
    }

    public JwtResponse(String token, String userName, String email, List<String> listRoles) {
        this.token = token;
        this.userName = userName;
        this.email = email;
        this.listRoles = listRoles;
    }

    public JwtResponse(String token, String userName, String email, boolean userStatus, List<String> listRoles) {
        this.token = token;
        this.userName = userName;
        this.email = email;
        this.userStatus = userStatus;
        this.listRoles = listRoles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public List<String> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<String> listRoles) {
        this.listRoles = listRoles;
    }
}
