package com.smartfit.smartfitapi.model.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Data
@SuperBuilder
@MappedSuperclass
public class StaffAccountBase {
    private String email;
    private String password;
    private Boolean isVerified;

    public StaffAccountBase() {
    }
}

