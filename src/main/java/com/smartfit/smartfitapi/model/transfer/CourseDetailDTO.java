package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

import java.util.List;

@Data
public class CourseDetailDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private String courseType;
    private String level;
    private Integer estimatedTime;
    private String orderType;
    private List<CourseStepDTO> courseSteps;
}
