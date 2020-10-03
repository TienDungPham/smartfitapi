package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

import java.util.List;

@Data
public class DatasetDTO {
    private String label;
    private List<Integer> data;
}
