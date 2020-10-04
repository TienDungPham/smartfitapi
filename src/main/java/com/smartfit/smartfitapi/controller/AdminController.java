package com.smartfit.smartfitapi.controller;

import com.smartfit.smartfitapi.model.transfer.*;
import com.smartfit.smartfitapi.service.AdminService;
import com.smartfit.smartfitapi.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(path = "/api/v2")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping(path = "/all-user")
    public ResponseEntity<List<UserProfileDTO>> findAllUserProfile() {
        ServiceResult<List<UserProfileDTO>> result = adminService.findAllUsers();
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/all-course")
    public ResponseEntity<List<CourseDTO>> findAllCourse() {
        ServiceResult<List<CourseDTO>> result = adminService.findAllCourses();
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/update-course")
    public ResponseEntity<Boolean> updateCourse(@RequestBody CourseDetailDTO updateCourse) {
        ServiceResult<Boolean> result = adminService.updateCourse(updateCourse);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/all-meal")
    public ResponseEntity<List<MealDTO>> findAllMeal() {
        ServiceResult<List<MealDTO>> result = adminService.findAllMeal();
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/all-staff-profile")
    public ResponseEntity<List<StaffProfileDTO>> findAllStaffProfile() {
        ServiceResult<List<StaffProfileDTO>> result = adminService.findAllStaffProfile();
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
