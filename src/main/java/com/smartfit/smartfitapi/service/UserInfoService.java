package com.smartfit.smartfitapi.service;

import com.smartfit.smartfitapi.entity.*;
import com.smartfit.smartfitapi.mapper.UserMapper;
import com.smartfit.smartfitapi.model.UpdateUserCourse;
import com.smartfit.smartfitapi.model.UpdateUserMeal;
import com.smartfit.smartfitapi.model.UpdateUserProfile;
import com.smartfit.smartfitapi.model.UpdateUserSession;
import com.smartfit.smartfitapi.model.transfer.*;
import com.smartfit.smartfitapi.repository.*;
import com.smartfit.smartfitapi.utils.ServiceResult;
import com.smartfit.smartfitapi.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserInfoService {
    @Autowired
    private UserAccessRepository userAccessRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserCourseRepository userCourseRepository;
    @Autowired
    private UserSessionRepository userSessionRepository;
    @Autowired
    private UserMealRepository userMealRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private MealRepository mealRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public ServiceResult<UserProfileDTO> findUserProfile(String accessToken) {
        ServiceResult<UserProfileDTO> result = new ServiceResult<>();
        try {
            UserAccess userAccess = userAccessRepository.findByAccessToken(accessToken);
            UserProfileDTO resultData = userMapper.userProfileToDTO(userAccess.getUserProfile());

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<Boolean> updateUserProfile(String accessToken, UpdateUserProfile newUpdate) {
        ServiceResult<Boolean> result = new ServiceResult<>();
        try {
            UserProfile userProfile = userAccessRepository
                    .findByAccessToken(accessToken)
                    .getUserProfile();
            if (newUpdate.getName() != null) userProfile.setName(newUpdate.getName());
            if (newUpdate.getImageUrl() != null) userProfile.setImageUrl(newUpdate.getImageUrl());
            if (newUpdate.getWeight() != null) userProfile.setWeight(newUpdate.getWeight());
            if (newUpdate.getHeight() != null) userProfile.setHeight(newUpdate.getHeight());
            if (newUpdate.getAge() != null) userProfile.setAge(newUpdate.getAge());
            if (newUpdate.getGender() != null) userProfile.setGender(newUpdate.getGender());
            if (newUpdate.getGoal() != null) userProfile.setGoal(newUpdate.getGoal());
            userProfileRepository.save(userProfile);

            result.setData(true);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<UserOrderDTO> findUserOrder(String accessToken) {
        ServiceResult<UserOrderDTO> result = new ServiceResult<>();
        try {
            UserAccess userAccess = userAccessRepository.findByAccessToken(accessToken);
            UserOrderDTO resultData = userMapper
                    .userOrderToDTO(userAccess.getUserProfile().getUserOrder());

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<List<UserCourseDTO>> findUserCourses(String accessToken) {
        ServiceResult<List<UserCourseDTO>> result = new ServiceResult<>();
        try {
            UserAccess userAccess = userAccessRepository.findByAccessToken(accessToken);
            List<UserCourseDTO> resultData = userMapper
                    .userCoursesToDTO(userAccess.getUserProfile().getUserCourses());

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<Boolean> updateUserCourse(String accessToken, UpdateUserCourse newUpdate) {
        ServiceResult<Boolean> result = new ServiceResult<>();
        try {
            UserProfile userProfile = userAccessRepository
                    .findByAccessToken(accessToken)
                    .getUserProfile();
            Optional<Course> course = courseRepository
                    .findById(newUpdate.getCourseId());
            if (userProfile == null || !course.isPresent()) throw new NullPointerException();

            UserCourse userCourse = userCourseRepository
                    .findByUserIdAndCourseId(userProfile.getId(), newUpdate.getCourseId());

            if (userCourse == null) {
                userCourse = UserCourse.builder()
                        .course(course.get())
                        .userProfile(userProfile)
                        .build();
            }

            userCourse.setCourseStep(newUpdate.getCourseStep());
            if (course.get().getCourseSteps().size() < 1) {
                userCourse.setCourseProgress(100);
            } else {
                float courseProgress = (float) newUpdate.getCourseStep() / course.get().getCourseSteps().size();
                if (courseProgress >= 1) userCourse.setCourseProgress(100);
                else userCourse.setCourseProgress(Math.round(courseProgress * 100));
            }
            userCourseRepository.save(userCourse);

            result.setData(true);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<Boolean> updateUserSession(String accessToken, UpdateUserSession newUpdate) {
        ServiceResult<Boolean> result = new ServiceResult<>();
        try {
            UserProfile userProfile = userAccessRepository
                    .findByAccessToken(accessToken)
                    .getUserProfile();
            Optional<Course> course = courseRepository
                    .findById(newUpdate.getCourseId());
            if (userProfile == null || !course.isPresent()) throw new NullPointerException();

            List<UserSession> userSessions = userSessionRepository
                    .findByUserIdAndCourseId(userProfile.getId(), newUpdate.getCourseId());
            UserSession userSession = userSessions.size() > 0 ? userSessions.get(0) : null;

            if (userSession == null || userSession.getAccessTime().compareTo(userSession.getLeaveTime()) < 0) {
                userSession = UserSession.builder()
                        .course(course.get())
                        .userProfile(userProfile)
                        .accessTime(new Date())
                        .leaveTime(new Date())
                        .totalCalories(0)
                        .build();
            } else {
                userSession.setLeaveTime(new Date());
                long dateDiffInSeconds = Utils
                        .getDateDiffInSeconds(userSession.getAccessTime(), userSession.getLeaveTime());
                float dateDiffInHours = (float) (dateDiffInSeconds / 60) / 60;
                userSession.setTotalCalories(
                        Utils.caloriesBurnedCalculate(
                                1.55F,
                                userProfile.getWeight(),
                                dateDiffInHours
                        )
                );
            }
            userSessionRepository.save(userSession);

            result.setData(true);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<List<UserMealDTO>> findUserMeals(String accessToken) {
        ServiceResult<List<UserMealDTO>> result = new ServiceResult<>();
        try {
            UserAccess userAccess = userAccessRepository.findByAccessToken(accessToken);
            List<UserMealDTO> resultData = userMapper
                    .userMealsToDTO(userAccess.getUserProfile().getUserMeals());

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<Boolean> updateUserMeal(String accessToken, UpdateUserMeal newUpdate) {
        ServiceResult<Boolean> result = new ServiceResult<>();
        try {
            UserProfile userProfile = userAccessRepository
                    .findByAccessToken(accessToken)
                    .getUserProfile();
            Optional<Meal> meal = mealRepository.findById(newUpdate.getMealId());
            if (userProfile == null || !meal.isPresent()) throw new NullPointerException();

            UserMeal userMeal = UserMeal.builder()
                    .userProfile(userProfile)
                    .meal(meal.get())
                    .eatenDate(new Date())
                    .servingSize(newUpdate.getServingSize())
                    .totalCalories(meal.get().getCalories() * newUpdate.getServingSize())
                    .build();
            userMealRepository.save(userMeal);

            result.setData(true);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<UserProgressDTO> calculateUserProgress(String accessToken) {
        ServiceResult<UserProgressDTO> result = new ServiceResult<>();
        try {
            UserAccess userAccess = userAccessRepository.findByAccessToken(accessToken);
            List<UserSession> userSessions = userAccess
                    .getUserProfile()
                    .getUserSessions()
                    .stream()
                    .filter(us -> Utils.isSameDay(us.getAccessTime(), new Date()))
                    .collect(Collectors.toList());
            List<UserMeal> userMeals = userAccess
                    .getUserProfile()
                    .getUserMeals()
                    .stream()
                    .filter(um -> Utils.isSameDay(um.getEatenDate(), new Date()))
                    .collect(Collectors.toList());

            Integer todayCalories = 0;
            long dateDiffInSeconds = 0L;
            for (UserSession us : userSessions) {
                todayCalories += us.getTotalCalories();
                dateDiffInSeconds += Utils.getDateDiffInSeconds(us.getAccessTime(), us.getLeaveTime());
            }

            Integer goalCalories = calculateGoal(userAccess.getUserProfile());
            for (UserMeal um : userMeals) {
                goalCalories += um.getTotalCalories();
            }

            UserProgressDTO resultData = new UserProgressDTO();
            resultData.setCalories(todayCalories);
            resultData.setMinutes(Long.toString(dateDiffInSeconds / 60));
            resultData.setWorkouts(userSessions.size());
            resultData.setGoal(goalCalories);

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    private Integer calculateGoal(UserProfile up) {
        double bmr = 0D;
        if (!up.getGender()) {
            bmr = 66 + (13.7 * up.getWeight()) + (5 * up.getHeight()) - (6.8 * up.getAge());

        } else {
            bmr = 655 + (9.6 * up.getWeight()) + (1.8 * up.getHeight()) - (4.7 * up.getAge());
        }
        double dailyCalories = bmr * 1.465;
        double goalCalories = 0D;
        switch (up.getGoal()) {
            case "Mile Weight Loss":
                goalCalories = dailyCalories - dailyCalories * 86 / 100;
                break;
            case "Weight Loss":
                goalCalories = dailyCalories - dailyCalories * 73 / 100;
                break;
            case "Extreme Weight Loss":
                goalCalories = dailyCalories - dailyCalories * 46 / 100;
                break;
            default:
                goalCalories = dailyCalories - dailyCalories;
                break;
        }
        return Math.round((float) goalCalories);
    }
}
