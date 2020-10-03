package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

@Data
public class CountDTO {
    private Integer totalUser;
    private Integer totalActiveUser;
    private Integer totalCourse;
    private Integer totalStaff;
}
