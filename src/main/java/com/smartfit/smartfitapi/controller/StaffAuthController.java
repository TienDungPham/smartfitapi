package com.smartfit.smartfitapi.controller;

import com.smartfit.smartfitapi.model.StaffSignIn;
import com.smartfit.smartfitapi.model.StaffSignUp;
import com.smartfit.smartfitapi.model.transfer.StaffAccessDTO;
import com.smartfit.smartfitapi.service.StaffAuthService;
import com.smartfit.smartfitapi.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping(path = "/api/v2/staff-auth")
public class StaffAuthController {
    @Autowired
    private StaffAuthService staffAuthService;

    @PostMapping(path = "/sign-in")
    public ResponseEntity<StaffAccessDTO> signIn(@RequestBody StaffSignIn si) {
        ServiceResult<StaffAccessDTO> result = staffAuthService.signIn(si);
        if (result.getCode() == ServiceResult.ResultCode.AUTHENTICATED) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<StaffAccessDTO> signUp(@RequestBody StaffSignUp su) {
        ServiceResult<StaffAccessDTO> result = staffAuthService.signUp(su);
        switch (result.getCode()) {
            case SUCCESS:
                return new ResponseEntity<>(result.getData(), HttpStatus.CREATED);
            case CONFLICT:
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            default:
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/sign-out")
    public ResponseEntity<Boolean> signOut(@RequestParam String accessToken) {
        ServiceResult<Boolean> result = staffAuthService.signOut(accessToken);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping(path = "/refresh-token")
    public ResponseEntity<StaffAccessDTO> refreshToken(@RequestParam String refreshToken) {
        ServiceResult<StaffAccessDTO> result = staffAuthService.refreshToken(refreshToken);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
