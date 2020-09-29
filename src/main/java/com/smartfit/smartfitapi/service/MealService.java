package com.smartfit.smartfitapi.service;

import com.smartfit.smartfitapi.entity.Meal;
import com.smartfit.smartfitapi.mapper.MealMapper;
import com.smartfit.smartfitapi.model.transfer.MealDTO;
import com.smartfit.smartfitapi.repository.MealRepository;
import com.smartfit.smartfitapi.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {
    @Autowired
    private MealRepository mealRepository;
    private final MealMapper mealMapper = MealMapper.INSTANCE;

    public ServiceResult<List<MealDTO>> findAllMeals() {
        ServiceResult<List<MealDTO>> result = new ServiceResult<>();
        try {
            List<Meal> meals = mealRepository.findAll();
            List<MealDTO> resultData = mealMapper.mealsToDTO(meals);

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }
}
