package com.smartfit.smartfitapi.repository;

import com.smartfit.smartfitapi.entity.UserMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UseMealRepository extends JpaRepository<UserMeal, Long> {
}
