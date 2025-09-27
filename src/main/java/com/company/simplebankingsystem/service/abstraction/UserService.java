package com.company.simplebankingsystem.service.abstraction;


import com.company.simplebankingsystem.dto.request.CreateUserRequest;
import com.company.simplebankingsystem.dto.request.UpdateUserRequest;
import com.company.simplebankingsystem.dto.response.UpdateUserResponse;
import com.company.simplebankingsystem.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    UpdateUserResponse updateUserById(Long id, UpdateUserRequest request);
    void deleteUserById(Long id);
    UserResponse findById(Long id);
}
