package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

@Data
public class UserPaymentDTO {
    private Long id;
    private String type;
    private String status;
    private String description;
    private Integer totalPrice;
}
