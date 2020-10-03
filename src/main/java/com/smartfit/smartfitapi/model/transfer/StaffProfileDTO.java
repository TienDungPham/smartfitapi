package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

@Data
public class StaffProfileDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private String address;
    private String role;
}
