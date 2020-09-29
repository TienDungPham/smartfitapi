package com.smartfit.smartfitapi.controller;

import com.smartfit.smartfitapi.model.UpdateUserCourse;
import com.smartfit.smartfitapi.model.UpdateUserMeal;
import com.smartfit.smartfitapi.model.UpdateUserProfile;
import com.smartfit.smartfitapi.model.UpdateUserSession;
import com.smartfit.smartfitapi.model.transfer.*;
import com.smartfit.smartfitapi.service.UserInfoService;
import com.smartfit.smartfitapi.service.shared.AccessAuthService;
import com.smartfit.smartfitapi.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/user-info")
public class UserInfoController {
    @Autowired
    private AccessAuthService accessAuthService;
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping(path = "/profile")
    public ResponseEntity<UserProfileDTO> findUserProfile(@RequestParam String accessToken) {
        if (!accessAuthService.isUserAccessTokenValid(accessToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ServiceResult<UserProfileDTO> result = userInfoService.findUserProfile(accessToken);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/profile")
    public ResponseEntity<Boolean> updateUserProfile(@RequestParam String accessToken,
                                                     @RequestBody UpdateUserProfile newUpdate) {
        if (!accessAuthService.isUserAccessTokenValid(accessToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ServiceResult<Boolean> result = userInfoService.updateUserProfile(accessToken, newUpdate);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/order")
    public ResponseEntity<UserOrderDTO> findUserOrder(@RequestParam String accessToken) {
        if (!accessAuthService.isUserAccessTokenValid(accessToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ServiceResult<UserOrderDTO> result = userInfoService.findUserOrder(accessToken);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/user-course")
    public ResponseEntity<List<UserCourseDTO>> findUserCourses(@RequestParam String accessToken) {
        if (!accessAuthService.isUserAccessTokenValid(accessToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ServiceResult<List<UserCourseDTO>> result = userInfoService.findUserCourses(accessToken);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/update-user-course")
    public ResponseEntity<Boolean> updateUserCourse(@RequestParam String accessToken,
                                                    @RequestBody UpdateUserCourse newUpdate) {
        if (!accessAuthService.isUserAccessTokenValid(accessToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ServiceResult<Boolean> result = userInfoService.updateUserCourse(accessToken, newUpdate);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/update-user-session")
    public ResponseEntity<Boolean> updateUserSession(@RequestParam String accessToken,
                                                     @RequestBody UpdateUserSession incomeUpdate) {
        if (!accessAuthService.isUserAccessTokenValid(accessToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ServiceResult<Boolean> result = userInfoService.updateUserSession(accessToken, incomeUpdate);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/user-meal")
    public ResponseEntity<List<UserMealDTO>> findUserMeals(@RequestParam String accessToken) {
        if (!accessAuthService.isUserAccessTokenValid(accessToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ServiceResult<List<UserMealDTO>> result = userInfoService.findUserMeals(accessToken);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/update-user-meal")
    public ResponseEntity<Boolean> updateUserMeal(@RequestParam String accessToken,
                                                  @RequestBody UpdateUserMeal incomeUpdate) {
        if (!accessAuthService.isUserAccessTokenValid(accessToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ServiceResult<Boolean> result = userInfoService.updateUserMeal(accessToken, incomeUpdate);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/user-progress")
    public ResponseEntity<UserProgressDTO> calculateUserProgress(@RequestParam String accessToken) {
        if (!accessAuthService.isUserAccessTokenValid(accessToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ServiceResult<UserProgressDTO> result = userInfoService.calculateUserProgress(accessToken);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
