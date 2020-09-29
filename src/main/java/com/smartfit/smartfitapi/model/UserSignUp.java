package com.smartfit.smartfitapi.model;

import lombok.Data;

@Data
public class UserSignUp {
    private String phoneNumber;
    private String phoneIdentity;
    private String name;
    private Integer weight;
    private Integer height;
    private Integer age;
    private Boolean gender;
    private String goal;
}
