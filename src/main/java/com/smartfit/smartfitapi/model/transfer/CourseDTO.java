package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private String courseType;
    private String level;
    private Integer estimatedTime;
    private String orderType;
}
