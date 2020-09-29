package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

import java.util.Date;

@Data
public class UserMealDTO {
    private Long id;
    private Integer totalCalories;
    private Date eatenDate;
    private MealDTO meal;
}
