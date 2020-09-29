package com.smartfit.smartfitapi.model.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Data
@SuperBuilder
@MappedSuperclass
public class UserProfileBase {
    private String name;
    private String imageUrl;
    private Integer weight;
    private Integer height;
    private Integer age;
    private Boolean gender;
    private String goal;

    public UserProfileBase() {
    }
}
