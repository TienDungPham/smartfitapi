package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

import java.util.Date;

@Data
public class StaffAccessDTO {
    private String accessToken;
    private String refreshToken;
    private Date expiryTime;
}
