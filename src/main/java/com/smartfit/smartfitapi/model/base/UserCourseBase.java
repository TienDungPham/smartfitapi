package com.smartfit.smartfitapi.model.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Data
@SuperBuilder
@MappedSuperclass
public class UserCourseBase {
    private Integer courseStep;
    private Integer courseProgress;

    public UserCourseBase() {
    }
}
