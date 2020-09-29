package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserOrderDTO {
    private String type;
    private String status;
    private Date expiryTime;
    private List<UserPaymentDTO> userPayments;
}
