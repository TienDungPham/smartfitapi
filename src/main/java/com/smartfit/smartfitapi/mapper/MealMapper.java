package com.smartfit.smartfitapi.mapper;

import com.smartfit.smartfitapi.entity.Meal;
import com.smartfit.smartfitapi.model.transfer.MealDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MealMapper {
    MealMapper INSTANCE = Mappers.getMapper(MealMapper.class);

    List<MealDTO> mealsToDTO(List<Meal> meals);
}
