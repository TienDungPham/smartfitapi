package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

import java.util.List;

@Data
public class AnalyticDTO {
    private List<String> labels;
    private List<DatasetDTO> datasets;
}
