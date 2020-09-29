package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

@Data
public class MealDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private String type;
    private Integer servingSize;
    private Integer calories;
}
