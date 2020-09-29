package com.smartfit.smartfitapi.model.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Data
@SuperBuilder
@MappedSuperclass
public class UserPaymentBase {
    private String type;
    private String status;
    private String description;
    private Integer totalPrice;

    public UserPaymentBase() {
    }
}
