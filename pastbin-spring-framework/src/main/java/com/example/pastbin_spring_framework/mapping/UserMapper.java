package com.example.pastbin_spring_framework.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.pastbin_spring_framework.dto.UserDto;
import com.example.pastbin_spring_framework.entity.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto tDto(User user);

    @Mapping(target = "password", ignore = true)
    User tEntity(UserDto userDto);
}
