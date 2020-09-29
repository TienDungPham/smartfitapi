package com.smartfit.smartfitapi.repository;

import com.smartfit.smartfitapi.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    @Query("SELECT ua FROM UserAccount ua WHERE ua.phoneNumber = :phoneNumber AND ua.phoneIdentify = :phoneIdentify")
    UserAccount signIn(@Param("phoneNumber") String phoneNumber, @Param("phoneIdentify") String phoneIdentify);
}
