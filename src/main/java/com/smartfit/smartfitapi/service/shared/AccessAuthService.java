package com.smartfit.smartfitapi.service.shared;

import com.smartfit.smartfitapi.entity.StaffAccess;
import com.smartfit.smartfitapi.entity.UserAccess;
import com.smartfit.smartfitapi.repository.StaffAccessRepository;
import com.smartfit.smartfitapi.repository.UserAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccessAuthService {
    @Autowired
    private UserAccessRepository userAccessRepository;
    @Autowired
    private StaffAccessRepository staffAccessRepository;

    public boolean isUserAccessTokenValid(String accessToken) {
        UserAccess userAccess = userAccessRepository.findByAccessToken(accessToken);
        return userAccess != null && userAccess.getUserProfile() != null && userAccess.getExpiryTime().after(new Date());
    }

    public boolean isStaffAccessTokenValid(String accessToken) {
        StaffAccess staffAccess = staffAccessRepository.findByAccessToken(accessToken);
        return staffAccess != null && staffAccess.getStaffProfile() != null && staffAccess.getExpiryTime().after(new Date());
    }
}
