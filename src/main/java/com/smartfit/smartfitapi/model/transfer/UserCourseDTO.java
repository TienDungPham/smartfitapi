package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

@Data
public class UserCourseDTO {
    private Long id;
    private Integer courseStep;
    private Integer courseProgress;
    private CourseDTO course;
}
