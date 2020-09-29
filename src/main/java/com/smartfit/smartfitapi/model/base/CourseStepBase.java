package com.smartfit.smartfitapi.model.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Data
@SuperBuilder
@MappedSuperclass
public class CourseStepBase {
    private String name;
    private String type;
    private String description;
    private String videoUrl;
    private String pose;

    public CourseStepBase() {
    }
}
