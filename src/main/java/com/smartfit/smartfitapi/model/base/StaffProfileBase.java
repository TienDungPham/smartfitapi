package com.smartfit.smartfitapi.model.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Data
@SuperBuilder
@MappedSuperclass
public class StaffProfileBase {
    private String name;
    private String imageUrl;
    private String address;
    private String role;

    public StaffProfileBase() {
    }
}
