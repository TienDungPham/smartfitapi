package com.smartfit.smartfitapi.model.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@SuperBuilder
@MappedSuperclass
public class UserSessionBase {
    private Date accessTime;
    private Date leaveTime;
    private Integer totalCalories;

    public UserSessionBase() {
    }
}
