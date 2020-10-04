package com.smartfit.smartfitapi.controller;

import com.smartfit.smartfitapi.model.transfer.AdminAnalyticDTO;
import com.smartfit.smartfitapi.model.transfer.CountDTO;
import com.smartfit.smartfitapi.service.AdminAnalyticService;
import com.smartfit.smartfitapi.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@Controller
@RequestMapping(path = "/api/v2")
public class AdminAnalyticController {
    @Autowired
    private AdminAnalyticService adminAnalyticService;

    @GetMapping(path = "/analytic")
    public ResponseEntity<AdminAnalyticDTO> analytic() {
        ServiceResult<AdminAnalyticDTO> result = adminAnalyticService.analytic();
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/count-info")
    public ResponseEntity<CountDTO> countInfo() {
        ServiceResult<CountDTO> result = adminAnalyticService.countInfo();
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
