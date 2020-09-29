package com.smartfit.smartfitapi.model.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Data
@SuperBuilder
@MappedSuperclass
public class CourseBase {
    private String name;
    private String imageUrl;
    private String description;
    private String courseType;
    private String level;
    private Integer estimatedTime;
    private String orderType;

    public CourseBase() {
    }
}
