package com.smartfit.smartfitapi.mapper;

import com.smartfit.smartfitapi.entity.Course;
import com.smartfit.smartfitapi.entity.UserCourse;
import com.smartfit.smartfitapi.model.transfer.CourseDTO;
import com.smartfit.smartfitapi.model.transfer.CourseDetailDTO;
import com.smartfit.smartfitapi.model.transfer.UserCourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseDTO courseToDTO(Course course);

    CourseDetailDTO courseToDetailDTO(Course course);

    UserCourseDTO userCourseToDTO(UserCourse userCourse);

    List<CourseDTO> coursesToDTO(List<Course> courses);
}
