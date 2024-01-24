package com.freshermanage.model.dto;

import com.freshermanage.model.Roles;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDTO {

    private Long id;
    private String fullName;
    private String userName;
    private String email;
    private boolean userStatus;
    private List<Roles> listRole;

    public UserDTO(Long id, String fullName, String userName, String email, boolean userStatus, List<Roles> listRole) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.userStatus = userStatus;
        this.listRole = listRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public List<Roles> getListRole() {
        return listRole;
    }

    public void setListRole(List<Roles> listRole) {
        this.listRole = listRole;
    }
}

