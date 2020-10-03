package com.smartfit.smartfitapi.model.transfer;

import lombok.Data;

@Data
public class AdminAnalyticDTO {
    private AnalyticDTO monthlyIncome;
    private AnalyticDTO userWorkoutTrend;
}
