package com.smartfit.smartfitapi.model;

import lombok.Data;

@Data
public class StaffSignUp {
    private String email;
    private String password;
    private String name;
    private String address;
    private String role;
}
