package com.smartfit.smartfitapi.repository;

import com.smartfit.smartfitapi.entity.UserAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserAccessRepository extends JpaRepository<UserAccess, Long> {
    @Query("SELECT ua FROM UserAccess ua WHERE ua.accessToken = :accessToken")
    UserAccess findByAccessToken(@Param("accessToken") String accessToken);

    @Query("SELECT ua FROM UserAccess ua WHERE ua.refreshToken = :refreshToken")
    UserAccess findByRefreshToken(@Param("refreshToken") String refreshToken);
}
