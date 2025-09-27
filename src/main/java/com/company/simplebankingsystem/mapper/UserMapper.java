package com.company.simplebankingsystem.mapper;


import com.company.simplebankingsystem.dao.entity.UserEntity;
import com.company.simplebankingsystem.dto.request.CreateUserRequest;
import com.company.simplebankingsystem.dto.response.UpdateUserResponse;
import com.company.simplebankingsystem.dto.response.UserResponse;

import java.time.LocalDateTime;

import static com.company.simplebankingsystem.enums.UserStatus.ACTIVE;

public class UserMapper {

    public static UserResponse entityToResponse(UserEntity entity) {
        if (entity == null) return null;
        return UserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .fin(entity.getFin())
                .age(entity.getAge())
                .userStatus(entity.getUserStatus())
                .createAt(entity.getCreateAt())
                .build();
    }



    public static UserEntity requestToEntity(CreateUserRequest request) {
        if (request == null) return null;
        return UserEntity.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .fin(request.getFin())
                .age(request.getAge())
                .userStatus(ACTIVE)
                .createAt(LocalDateTime.now())
                .build();
    }

    public static UpdateUserResponse entityToUpdateResponse(UserEntity entity) {
        if (entity == null) return null;
        return UpdateUserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .fin(entity.getFin())
                .age(entity.getAge())
                .userStatus(entity.getUserStatus())
                .updateAt(entity.getUpdateAt())
                .build();
    }
}