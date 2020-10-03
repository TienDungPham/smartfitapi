package com.smartfit.smartfitapi.service;

import com.smartfit.smartfitapi.entity.StaffAccess;
import com.smartfit.smartfitapi.entity.StaffAccount;
import com.smartfit.smartfitapi.entity.StaffProfile;
import com.smartfit.smartfitapi.mapper.StaffMapper;
import com.smartfit.smartfitapi.model.StaffSignIn;
import com.smartfit.smartfitapi.model.StaffSignUp;
import com.smartfit.smartfitapi.model.transfer.StaffAccessDTO;
import com.smartfit.smartfitapi.repository.StaffAccessRepository;
import com.smartfit.smartfitapi.repository.StaffAccountRepository;
import com.smartfit.smartfitapi.repository.StaffProfileRepository;
import com.smartfit.smartfitapi.utils.ServiceResult;
import com.smartfit.smartfitapi.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StaffAuthService {
    @Autowired
    private StaffAccountRepository staffAccountRepository;
    @Autowired
    private StaffProfileRepository staffProfileRepository;
    @Autowired
    private StaffAccessRepository staffAccessRepository;

    private final StaffMapper staffMapper = StaffMapper.INSTANCE;

    public ServiceResult<StaffAccessDTO> signIn(StaffSignIn si) {
        ServiceResult<StaffAccessDTO> result = new ServiceResult<>();
        try {
            StaffAccount staffAccount = staffAccountRepository.signIn(si.getEmail(), si.getPassword());
            if (staffAccount != null) {
                StaffAccess staffAccess = generateStaffAccess(staffAccount.getStaffProfile());
                StaffAccessDTO resultData = staffMapper.staffAccessToDTO(staffAccess);

                result.setData(resultData);
                result.setCode(ServiceResult.ResultCode.AUTHENTICATED);
            } else {
                result.setCode(ServiceResult.ResultCode.NOTFOUND);
            }
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<StaffAccessDTO> signUp(StaffSignUp su) {
        ServiceResult<StaffAccessDTO> result = new ServiceResult<>();
        try {
            if (isAccountExit(su.getEmail(), su.getPassword())) {
                result.setCode(ServiceResult.ResultCode.CONFLICT);
            } else {
                StaffProfile staffProfile = createStaffProfile(su);
                StaffAccount staffAccount = createStaffAccount(su, staffProfile);

                StaffAccess staffAccess = generateStaffAccess(staffProfile);
                StaffAccessDTO resultData = staffMapper.staffAccessToDTO(staffAccess);

                result.setData(resultData);
                result.setCode(ServiceResult.ResultCode.SUCCESS);
            }
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<Boolean> signOut(String accessToken) {
        ServiceResult<Boolean> result = new ServiceResult<>();
        StaffAccess staffAccess = staffAccessRepository.findByAccessToken(accessToken);
        if (staffAccess != null) {
            staffAccessRepository.delete(staffAccess);
        }

        result.setData(true);
        result.setCode(ServiceResult.ResultCode.SUCCESS);
        return result;
    }

    public ServiceResult<StaffAccessDTO> refreshToken(String refreshToken) {
        ServiceResult<StaffAccessDTO> result = new ServiceResult<>();
        StaffAccess staffAccess = staffAccessRepository.findByRefreshToken(refreshToken);
        if (staffAccess != null) {
            staffAccess = refreshStaffAccess(staffAccess);
            StaffAccessDTO resultData = staffMapper.staffAccessToDTO(staffAccess);

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } else {
            result.setCode(ServiceResult.ResultCode.FAIL);
        }
        return result;
    }

    private Boolean isAccountExit(String email, String password) {
        return staffAccountRepository.signIn(email, password) != null;
    }

    private StaffAccess generateStaffAccess(StaffProfile staffProfile) {
        StaffAccess staffAccess = StaffAccess.builder()
                .accessToken(UUID.randomUUID().toString())
                .refreshToken(UUID.randomUUID().toString())
                .expiryTime(Utils.plusDayToCurrentDate(2))
                .staffProfile(staffProfile)
                .build();
        return staffAccessRepository.save(staffAccess);
    }

    private StaffAccess refreshStaffAccess(StaffAccess staffAccess) {
        staffAccess.setAccessToken(UUID.randomUUID().toString());
        staffAccess.setRefreshToken(UUID.randomUUID().toString());
        staffAccess.setExpiryTime(Utils.plusDayToCurrentDate(2));
        return staffAccessRepository.save(staffAccess);
    }

    private StaffProfile createStaffProfile(StaffSignUp su) {
        StaffProfile staffProfile = StaffProfile.builder()
                .name(su.getName())
                .imageUrl("https://res.cloudinary.com/dtasyh91f/image/upload/v1601405117/public/public_1_gxulfz.png")
                .address(su.getAddress())
                .role(su.getRole())
                .build();
        return staffProfileRepository.save(staffProfile);
    }

    private StaffAccount createStaffAccount(StaffSignUp su, StaffProfile staffProfile) {
        StaffAccount staffAccount = StaffAccount.builder()
                .email(su.getEmail())
                .password(su.getPassword())
                .isVerified(true)
                .staffProfile(staffProfile)
                .build();
        return staffAccountRepository.save(staffAccount);
    }
}
