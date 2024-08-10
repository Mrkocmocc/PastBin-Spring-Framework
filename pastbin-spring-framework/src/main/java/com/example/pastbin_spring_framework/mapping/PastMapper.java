package com.example.pastbin_spring_framework.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.pastbin_spring_framework.dto.PastDto;
import com.example.pastbin_spring_framework.entity.Past;

@Mapper(uses = { UserMapper.class })
public interface PastMapper {
    PastMapper INSTANCE = Mappers.getMapper(PastMapper.class);

    PastDto toDto(Past past);

    Past toEntity(PastDto pastDto);
}
