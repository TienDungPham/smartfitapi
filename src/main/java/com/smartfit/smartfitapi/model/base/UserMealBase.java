package com.smartfit.smartfitapi.model.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@SuperBuilder
@MappedSuperclass
public class UserMealBase {
    private Integer totalCalories;
    private Date eatenDate;
    private Integer servingSize;

    public UserMealBase() {
    }
}
