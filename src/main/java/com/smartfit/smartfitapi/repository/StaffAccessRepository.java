package com.smartfit.smartfitapi.repository;

import com.smartfit.smartfitapi.entity.StaffAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface StaffAccessRepository extends JpaRepository<StaffAccess, Long> {
    @Query("SELECT sa FROM StaffAccess sa WHERE sa.accessToken = :accessToken")
    StaffAccess findByAccessToken(@Param("accessToken") String accessToken);

    @Query("SELECT sa FROM StaffAccess sa WHERE sa.refreshToken = :refreshToken")
    StaffAccess findByRefreshToken(@Param("refreshToken") String refreshToken);
}
