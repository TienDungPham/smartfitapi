package com.smartfit.smartfitapi.model.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@SuperBuilder
@MappedSuperclass
public class UserAccessBase {
    private String accessToken;
    private String refreshToken;
    private Date expiryTime;

    public UserAccessBase() {
    }
}
