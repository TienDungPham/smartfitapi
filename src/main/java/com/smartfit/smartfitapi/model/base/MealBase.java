package com.smartfit.smartfitapi.model.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Data
@SuperBuilder
@MappedSuperclass
public class MealBase {
    private String name;
    private String imageUrl;
    private String type;
    private Integer servingSize;
    private Integer calories;

    public MealBase() {
    }
}
