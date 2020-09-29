package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

@Data
public class CourseStepDTO {
    private Long id;
    private String name;
    private String type;
    private String description;
    private String videoUrl;
    private String pose;
}
