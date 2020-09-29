package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

@Data
public class UserProfileDTO {
    private String name;
    private String imageUrl;
    private Integer weight;
    private Integer height;
    private Integer age;
    private Boolean gender;
    private String goal;
}
