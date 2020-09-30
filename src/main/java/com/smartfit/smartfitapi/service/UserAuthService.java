package com.smartfit.smartfitapi.service;

import com.smartfit.smartfitapi.entity.UserAccess;
import com.smartfit.smartfitapi.entity.UserAccount;
import com.smartfit.smartfitapi.entity.UserOrder;
import com.smartfit.smartfitapi.entity.UserProfile;
import com.smartfit.smartfitapi.mapper.UserMapper;
import com.smartfit.smartfitapi.model.UserSignIn;
import com.smartfit.smartfitapi.model.UserSignUp;
import com.smartfit.smartfitapi.model.base.AppEnumBase;
import com.smartfit.smartfitapi.model.transfer.UserAccessDTO;
import com.smartfit.smartfitapi.repository.UserAccessRepository;
import com.smartfit.smartfitapi.repository.UserAccountRepository;
import com.smartfit.smartfitapi.repository.UserOrderRepository;
import com.smartfit.smartfitapi.repository.UserProfileRepository;
import com.smartfit.smartfitapi.utils.ServiceResult;
import com.smartfit.smartfitapi.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserAuthService {
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserAccessRepository userAccessRepository;
    @Autowired
    private UserOrderRepository userOrderRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public ServiceResult<UserAccessDTO> signIn(UserSignIn si) {
        ServiceResult<UserAccessDTO> result = new ServiceResult<>();
        try {
            UserAccount userAccount = userAccountRepository.signIn(si.getPhoneNumber(), si.getPhoneIdentity());
            if (userAccount != null) {
                UserAccess userAccess = generateUserAccess(userAccount.getUserProfile());
                UserAccessDTO resultData = userMapper.userAccessToDTO(userAccess);

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

    public ServiceResult<UserAccessDTO> signUp(UserSignUp su) {
        ServiceResult<UserAccessDTO> result = new ServiceResult<>();
        try {
            if (isAccountExit(su.getPhoneNumber(), su.getPhoneIdentity())) {
                result.setCode(ServiceResult.ResultCode.CONFLICT);
            } else {
                UserProfile userProfile = createUserProfile(su);
                UserOrder userOrder = createUserOrder(userProfile);
                UserAccount userAccount = createUserAccount(su, userProfile);

                UserAccess userAccess = generateUserAccess(userProfile);
                UserAccessDTO resultData = userMapper.userAccessToDTO(userAccess);

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
        UserAccess userAccess = userAccessRepository.findByAccessToken(accessToken);
        if (userAccess != null) {
            userAccessRepository.delete(userAccess);
        }

        result.setData(true);
        result.setCode(ServiceResult.ResultCode.SUCCESS);
        return result;
    }

    public ServiceResult<UserAccessDTO> refreshToken(String refreshToken) {
        ServiceResult<UserAccessDTO> result = new ServiceResult<>();
        UserAccess userAccess = userAccessRepository.findByRefreshToken(refreshToken);
        if (userAccess != null) {
            userAccess = refreshUserAccess(userAccess);
            UserAccessDTO resultData = userMapper.userAccessToDTO(userAccess);

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } else {
            result.setCode(ServiceResult.ResultCode.FAIL);
        }
        return result;
    }

    private Boolean isAccountExit(String phoneNumber, String phoneIdentity) {
        return userAccountRepository.signIn(phoneNumber, phoneIdentity) != null;
    }

    private UserAccess generateUserAccess(UserProfile userProfile) {
        UserAccess userAccess = UserAccess.builder()
                .accessToken(UUID.randomUUID().toString())
                .refreshToken(UUID.randomUUID().toString())
                .expiryTime(Utils.plusDayToCurrentDate(2))
                .userProfile(userProfile)
                .build();
        return userAccessRepository.save(userAccess);
    }

    private UserAccess refreshUserAccess(UserAccess userAccess) {
        userAccess.setAccessToken(UUID.randomUUID().toString());
        userAccess.setRefreshToken(UUID.randomUUID().toString());
        userAccess.setExpiryTime(Utils.plusDayToCurrentDate(2));
        return userAccessRepository.save(userAccess);
    }

    private UserProfile createUserProfile(UserSignUp su) {
        UserProfile userProfile = UserProfile.builder()
                .name(su.getName())
                .imageUrl("https://res.cloudinary.com/dtasyh91f/image/upload/v1601405117/public/public_1_gxulfz.png")
                .weight(su.getWeight())
                .height(su.getHeight())
                .age(su.getWeight())
                .gender(su.getGender())
                .goal(su.getGoal())
                .build();
        return userProfileRepository.save(userProfile);
    }

    private UserAccount createUserAccount(UserSignUp su, UserProfile userProfile) {
        UserAccount userAccount = UserAccount.builder()
                .phoneNumber(su.getPhoneNumber())
                .phoneIdentify(su.getPhoneIdentity())
                .userProfile(userProfile)
                .build();
        return userAccountRepository.save(userAccount);
    }

    private UserOrder createUserOrder(UserProfile userProfile) {
        UserOrder userOrder = UserOrder.builder()
                .type(AppEnumBase.OrderType.BASIC.getString())
                .status(AppEnumBase.ActivityStatus.DONE.getString())
                .expiryTime(Utils.plusDayToCurrentDate(365))
                .userProfile(userProfile)
                .build();
        return userOrderRepository.save(userOrder);
    }
}
