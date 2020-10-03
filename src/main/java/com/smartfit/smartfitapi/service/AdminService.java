package com.smartfit.smartfitapi.service;

import com.smartfit.smartfitapi.entity.*;
import com.smartfit.smartfitapi.mapper.CourseMapper;
import com.smartfit.smartfitapi.mapper.MealMapper;
import com.smartfit.smartfitapi.mapper.StaffMapper;
import com.smartfit.smartfitapi.mapper.UserMapper;
import com.smartfit.smartfitapi.model.transfer.*;
import com.smartfit.smartfitapi.repository.*;
import com.smartfit.smartfitapi.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseStepRepository courseStepRepository;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private StaffProfileRepository staffProfileRepository;

    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final CourseMapper courseMapper = CourseMapper.INSTANCE;
    private final MealMapper mealMapper = MealMapper.INSTANCE;
    private final StaffMapper staffMapper = StaffMapper.INSTANCE;

    public ServiceResult<List<UserProfileDTO>> findAllUsers() {
        ServiceResult<List<UserProfileDTO>> result = new ServiceResult<>();
        try {
            List<UserProfile> userProfiles = userProfileRepository.findAll();
            List<UserProfileDTO> resultData = userMapper.userProfilesToDTO(userProfiles);

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<List<CourseDTO>> findAllCourses() {
        ServiceResult<List<CourseDTO>> result = new ServiceResult<>();
        try {
            List<Course> courses = courseRepository.findAll();
            List<CourseDTO> resultData = courseMapper.coursesToDTO(courses);

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<Boolean> updateCourse(CourseDetailDTO updateCourse) {
        ServiceResult<Boolean> result = new ServiceResult<>();
        try {
            if (updateCourse.getId() == null) {
                createNewCourse(updateCourse);
            } else {
                updateOldCourse(updateCourse);
            }

            result.setData(true);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<List<MealDTO>> findAllMeal() {
        ServiceResult<List<MealDTO>> result = new ServiceResult<>();
        try {
            List<Meal> meals = mealRepository.findAll();
            List<MealDTO> resultData = mealMapper.mealsToDTO(meals);

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<List<StaffProfileDTO>> findAllStaffProfile() {
        ServiceResult<List<StaffProfileDTO>> result = new ServiceResult<>();
        try {
            List<StaffProfile> staffProfiles = staffProfileRepository.findAll();
            List<StaffProfileDTO> resultData = staffMapper.staffProfilesToDTO(staffProfiles);

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    private void createNewCourse(CourseDetailDTO updateCourse) {
        Course course = Course.builder()
                .name(updateCourse.getName())
                .imageUrl(updateCourse.getImageUrl())
                .description(updateCourse.getDescription())
                .courseType(updateCourse.getCourseType())
                .level(updateCourse.getLevel())
                .estimatedTime(updateCourse.getEstimatedTime())
                .orderType(updateCourse.getOrderType())
                .build();
        course = courseRepository.save(course);

        for (CourseStepDTO cs : updateCourse.getCourseSteps()) {
            CourseStep courseStep = CourseStep.builder()
                    .name(cs.getName())
                    .type(cs.getType())
                    .description(cs.getDescription())
                    .videoUrl(cs.getVideoUrl())
                    .pose(cs.getPose())
                    .course(course)
                    .build();
            courseStepRepository.save(courseStep);
        }
    }

    private void updateOldCourse(CourseDetailDTO updateCourse) {
        Optional<Course> course = courseRepository.findById(updateCourse.getId());
        if (course.isPresent()) {
            Course c = course.get();
            if (updateCourse.getName() != null) c.setName(updateCourse.getName());
            if (updateCourse.getImageUrl() != null) c.setImageUrl(updateCourse.getImageUrl());
            if (updateCourse.getDescription() != null) c.setDescription(updateCourse.getDescription());
            if (updateCourse.getCourseType() != null) c.setCourseType(updateCourse.getCourseType());
            if (updateCourse.getLevel() != null) c.setLevel(updateCourse.getLevel());
            if (updateCourse.getEstimatedTime() != null) c.setEstimatedTime(updateCourse.getEstimatedTime());
            if (updateCourse.getOrderType() != null) c.setOrderType(updateCourse.getOrderType());
            courseRepository.save(c);

            for (CourseStepDTO cs : updateCourse.getCourseSteps()) {
                if (cs.getId() != null) {
                    Optional<CourseStep> courseStep = courseStepRepository.findById(cs.getId());
                    if (courseStep.isPresent()) {
                        CourseStep newUpdate = courseStep.get();
                        if (cs.getName() != null) newUpdate.setName(cs.getName());
                        if (cs.getType() != null) newUpdate.setType(cs.getType());
                        if (cs.getDescription() != null) newUpdate.setDescription(cs.getDescription());
                        if (cs.getVideoUrl() != null) newUpdate.setVideoUrl(cs.getVideoUrl());
                        if (cs.getPose() != null) newUpdate.setPose(cs.getPose());
                        courseStepRepository.save(newUpdate);
                    }
                }
            }
        }
    }
}
