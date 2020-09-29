package com.smartfit.smartfitapi.model.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Data
@SuperBuilder
@MappedSuperclass
public class UserAccountBase {
    private String phoneNumber;
    private String phoneIdentify;

    public UserAccountBase() {
    }
}
