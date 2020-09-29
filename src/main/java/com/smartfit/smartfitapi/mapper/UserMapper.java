package com.smartfit.smartfitapi.mapper;

import com.smartfit.smartfitapi.entity.*;
import com.smartfit.smartfitapi.model.transfer.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserAccessDTO userAccessToDTO(UserAccess userAccess);

    UserProfileDTO userProfileToDTO(UserProfile userProfile);

    UserOrderDTO userOrderToDTO(UserOrder userOrder);

    List<UserCourseDTO> userCoursesToDTO(List<UserCourse> userCourses);

    List<UserMealDTO> userMealsToDTO(List<UserMeal> userMeals);
}
