package com.smartfit.smartfitapi.controller;

import com.smartfit.smartfitapi.model.transfer.CourseDTO;
import com.smartfit.smartfitapi.model.transfer.CourseDetailDTO;
import com.smartfit.smartfitapi.service.CourseService;
import com.smartfit.smartfitapi.service.shared.AccessAuthService;
import com.smartfit.smartfitapi.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/course")
public class CourseController {
    @Autowired
    private AccessAuthService accessAuthService;
    @Autowired
    private CourseService courseService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<CourseDTO>> findAllCourse() {
        ServiceResult<List<CourseDTO>> result = courseService.findAllCourse();
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/detail")
    public ResponseEntity<CourseDetailDTO> findCourseDetail(@RequestParam Long id) {
        ServiceResult<CourseDetailDTO> result = courseService.findCourseDetail(id);
        switch (result.getCode()) {
            case SUCCESS:
                return new ResponseEntity<>(result.getData(), HttpStatus.OK);
            case NOTFOUND:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/access-check")
    public ResponseEntity<Boolean> checkUserAccess(@RequestParam Long id,
                                                   @RequestParam String accessToken) {
        if (!accessAuthService.isUserAccessTokenValid(accessToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ServiceResult<Boolean> result = courseService.checkUserAccess(id, accessToken);
        switch (result.getCode()) {
            case SUCCESS:
                return new ResponseEntity<>(result.getData(), HttpStatus.OK);
            case NOTFOUND:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            case UNAUTHENTICATED:
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            default:
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
