package com.smartfit.smartfitapi.repository;

import com.smartfit.smartfitapi.entity.StaffAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface StaffAccountRepository extends JpaRepository<StaffAccount, Long> {
    @Query("SELECT sa FROM StaffAccount sa WHERE sa.email = :email AND sa.password = :password")
    StaffAccount signIn(@Param("email") String email, @Param("password") String password);
}
