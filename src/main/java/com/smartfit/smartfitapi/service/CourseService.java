package com.smartfit.smartfitapi.service;

import com.smartfit.smartfitapi.entity.Course;
import com.smartfit.smartfitapi.entity.UserOrder;
import com.smartfit.smartfitapi.entity.UserProfile;
import com.smartfit.smartfitapi.mapper.CourseMapper;
import com.smartfit.smartfitapi.model.transfer.CourseDTO;
import com.smartfit.smartfitapi.model.transfer.CourseDetailDTO;
import com.smartfit.smartfitapi.repository.CourseRepository;
import com.smartfit.smartfitapi.repository.UserAccessRepository;
import com.smartfit.smartfitapi.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private UserAccessRepository userAccessRepository;
    @Autowired
    private CourseRepository courseRepository;
    private final CourseMapper courseMapper = CourseMapper.INSTANCE;

    public ServiceResult<List<CourseDTO>> findAllCourse() {
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

    public ServiceResult<CourseDetailDTO> findCourseDetail(Long id) {
        ServiceResult<CourseDetailDTO> result = new ServiceResult<>();
        try {
            Optional<Course> course = courseRepository.findById(id);
            if (course.isPresent()) {
                CourseDetailDTO resultData = courseMapper.courseToDetailDTO(course.get());

                result.setData(resultData);
                result.setCode(ServiceResult.ResultCode.SUCCESS);
            } else {
                result.setCode(ServiceResult.ResultCode.NOTFOUND);
            }
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<Boolean> checkUserAccess(Long id, String accessToken) {
        ServiceResult<Boolean> result = new ServiceResult<>();
        try {
            Optional<Course> course = courseRepository.findById(id);
            if (course.isPresent()) {
                UserProfile userProfile = userAccessRepository.findByAccessToken(accessToken).getUserProfile();
                if (isAllowAccess(course.get().getOrderType(), userProfile.getUserOrder())) {
                    result.setData(true);
                    result.setCode(ServiceResult.ResultCode.SUCCESS);
                } else {
                    result.setCode(ServiceResult.ResultCode.UNAUTHENTICATED);
                }
            } else {
                result.setCode(ServiceResult.ResultCode.NOTFOUND);
            }
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    private Boolean isAllowAccess(String courseOrderType, UserOrder userOrder) {
        if (!userOrder.getType().equals("Basic") && userOrder.getExpiryTime().after(new Date())) {
            return true;
        }
        return courseOrderType.equals("Basic");
    }
}
