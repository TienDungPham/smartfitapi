package com.smartfit.smartfitapi.controller;

import com.smartfit.smartfitapi.model.transfer.MealDTO;
import com.smartfit.smartfitapi.service.MealService;
import com.smartfit.smartfitapi.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/meal")
public class MealController {
    @Autowired
    private MealService mealService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<MealDTO>> findAllCourse() {
        ServiceResult<List<MealDTO>> result = mealService.findAllMeals();
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
