package com.smartfit.smartfitapi.repository;

import com.smartfit.smartfitapi.entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    @Query("SELECT uc FROM UserCourse uc WHERE uc.userProfile.id = :userId AND uc.course.id = :courseId")
    UserCourse findByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);
}
