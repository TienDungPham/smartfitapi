package com.smartfit.smartfitapi.repository;

import com.smartfit.smartfitapi.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    @Query(value = "SELECT us FROM UserSession us WHERE us.userProfile.id = :userId AND us.course.id = :courseId ORDER BY us.id DESC ")
    List<UserSession> findByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);
}
