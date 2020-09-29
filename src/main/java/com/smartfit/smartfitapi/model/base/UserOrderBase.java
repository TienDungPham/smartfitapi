package com.smartfit.smartfitapi.model.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@SuperBuilder
@MappedSuperclass
public class UserOrderBase {
    private String type;
    private String status;
    private Date expiryTime;

    public UserOrderBase() {
    }
}
