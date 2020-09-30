package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

@Data
public class UserProgressDTO {
    private Integer workouts;
    private String minutes;
    private Integer calories;
    private Integer goal;
}
