package com.smartfit.smartfitapi.repository;

import com.smartfit.smartfitapi.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
}
