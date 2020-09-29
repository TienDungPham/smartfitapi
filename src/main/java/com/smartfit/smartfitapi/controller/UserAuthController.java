package com.smartfit.smartfitapi.controller;

import com.smartfit.smartfitapi.model.UserSignIn;
import com.smartfit.smartfitapi.model.UserSignUp;
import com.smartfit.smartfitapi.model.transfer.UserAccessDTO;
import com.smartfit.smartfitapi.service.UserAuthService;
import com.smartfit.smartfitapi.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/api/v1/user-auth")
public class UserAuthController {
    @Autowired
    private UserAuthService userAuthService;

    @PostMapping(path = "/sign-in")
    public ResponseEntity<UserAccessDTO> signIn(@RequestBody UserSignIn si) {
        ServiceResult<UserAccessDTO> result = userAuthService.signIn(si);
        if (result.getCode() == ServiceResult.ResultCode.AUTHENTICATED) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<UserAccessDTO> signUp(@RequestBody UserSignUp su) {
        ServiceResult<UserAccessDTO> result = userAuthService.signUp(su);
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
        ServiceResult<Boolean> result = userAuthService.signOut(accessToken);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping(path = "/refresh-token")
    public ResponseEntity<UserAccessDTO> refreshToken(@RequestParam String refreshToken) {
        ServiceResult<UserAccessDTO> result = userAuthService.refreshToken(refreshToken);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
