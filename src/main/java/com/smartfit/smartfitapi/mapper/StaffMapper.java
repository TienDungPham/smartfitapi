package com.smartfit.smartfitapi.mapper;

import com.smartfit.smartfitapi.entity.StaffAccess;
import com.smartfit.smartfitapi.entity.StaffProfile;
import com.smartfit.smartfitapi.model.transfer.StaffAccessDTO;
import com.smartfit.smartfitapi.model.transfer.StaffProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StaffMapper {
    StaffMapper INSTANCE = Mappers.getMapper(StaffMapper.class);

    List<StaffProfileDTO> staffProfilesToDTO(List<StaffProfile> staffProfiles);

    StaffAccessDTO staffAccessToDTO(StaffAccess staffAccess);
}
