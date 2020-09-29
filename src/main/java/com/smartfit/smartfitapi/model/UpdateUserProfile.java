package com.smartfit.smartfitapi.model;

import lombok.Data;

@Data
public class UpdateUserProfile {
    private String name;
    private String imageUrl;
    private Integer weight;
    private Integer height;
    private Integer age;
    private Boolean gender;
    private String goal;
}
